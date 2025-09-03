package org.jammor9.mappointeditor;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.control.spreadsheet.Grid;
import org.jammor9.mappointeditor.models.CalendarModel;
import org.jammor9.mappointeditor.models.MarkerModel;

import java.util.ArrayList;

public class CalendarDialog extends Dialog<CalendarModel> {

    private static final String ENTER_MONTH_NAME= "Enter Month Name";
    private static final String ENTER_MONTH_DURATION = "Enter Month Duration";

    private CalendarModel calendarModel;
    private GridPane inputPane;
    private ArrayList<TextField[]> monthFields;
    private TextField calendarName;
    private TextField calendarSuffixAnte;
    private TextField calendarSuffixPost;

    public CalendarDialog(CalendarModel calendarModel) {
        super();
        setTitle("New Calendar");
        this.calendarModel = calendarModel;
        this.inputPane = new GridPane();
        this.monthFields = new ArrayList<>();
        this.calendarName = new TextField();
        this.calendarName.setPromptText("Enter Calendar Name Here");
        this.calendarSuffixPost = new TextField();
        this.calendarSuffixPost.setPromptText("Abbreviation Post Year 1");
        this.calendarSuffixAnte = new TextField();
        this.calendarSuffixAnte.setPromptText("Abbreviation Before Year 1");
        createWindow();
        resultsConverter();
    }

    private void resultsConverter() {
        Callback<ButtonType, CalendarModel> calendarModelCallback = buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (!validateDialog()) return null;
                generateCalendar();
                return this.calendarModel;
            }
            return null;
        };
        setResultConverter(calendarModelCallback);
    }

    private boolean validateDialog() {

        if (calendarName.getText().isEmpty() || calendarSuffixAnte.getText().isEmpty() || calendarSuffixPost.getText().isEmpty()) return false;

        for (TextField[] newMonth: monthFields) {
            TextField monthName = newMonth[0];
            TextField monthDuration = newMonth[1];
            TextField leapYearDays = newMonth[2];
            TextField leapYearGap = newMonth[3];

            if (monthName.getText().isEmpty()) return false;
            try {
                if (monthDuration.getText().isEmpty() || leapYearDays.getText().isEmpty() || leapYearGap.getText().isEmpty()) return false;
                Integer.parseInt(monthDuration.getText());
                Integer.parseInt(leapYearDays.getText());
                Integer.parseInt(leapYearGap.getText());
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    private void generateCalendar() {
        calendarModel.setName(calendarName.getText());
        calendarModel.setCalendarSuffixAnte(calendarSuffixAnte.getText());
        calendarModel.setCalendarSuffixPost(calendarSuffixPost.getText());

        for (TextField[] newMonth : monthFields) {
            String monthName = newMonth[0].getText();
            int monthDuration = Integer.parseInt(newMonth[1].getText());
            int leapYearDays = Integer.parseInt(newMonth[2].getText());
            int leapYearGap = Integer.parseInt(newMonth[3].getText());

            if (leapYearDays == 0 || leapYearGap == 0) calendarModel.addMonth(monthName, monthDuration);
            else calendarModel.addMonth(monthName, monthDuration, leapYearDays, leapYearGap);
        }
    }

    private void createWindow() {
        VBox vBox = new VBox();

        VBox topBox = new VBox();
        topBox.getChildren().addAll(calendarName, calendarSuffixAnte, calendarSuffixPost);

        Label monthNameLabel = new Label("Month Name");
        Label monthDurationLabel = new Label("Month Duration (Days)");
        Label leapYearLengthLabel = new Label("Number of Leap Days");
        Label leapYearGapLabel = new Label("Gap between Leap Years (Years)");
        inputPane.add(monthNameLabel, 0, 0);
        inputPane.add(monthDurationLabel, 1, 0);
        inputPane.add(leapYearLengthLabel, 2, 0);
        inputPane.add(leapYearGapLabel, 3, 0);

        addNewMonthField(inputPane);

        Button addMonthButton = new Button("Add Month");

        addMonthButton.setOnAction(e -> addNewMonthField(inputPane));

        vBox.getChildren().addAll(topBox, inputPane, addMonthButton);

        getDialogPane().setContent(vBox);
        getDialogPane().getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
        getDialogPane().getStyleClass().add("dialogBox");
        getDialogPane().setMaxHeight(3000);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    private void addNewMonthField(GridPane gridPane) {
        int y = gridPane.getRowCount();
        TextField newMonthNameField = new TextField();
        newMonthNameField.setPromptText(ENTER_MONTH_NAME);
        newMonthNameField.setStyle("-fx-background-color: #242424;");
        TextField newMonthDurationField = new TextField();
        newMonthDurationField.setPromptText(ENTER_MONTH_DURATION);
        newMonthDurationField.setStyle("-fx-background-color: #6e6e6e;");
        TextField leapYearLengthField = new TextField("0");
        leapYearLengthField.setStyle("-fx-background-color: #242424;");
        TextField leapYearGapField = new TextField("0");
        leapYearGapField.setStyle("-fx-background-color: #6e6e6e;");

        TextField[] newMonth = {newMonthNameField, newMonthDurationField, leapYearLengthField, leapYearGapField};
        this.monthFields.add(newMonth);

        gridPane.add(newMonthNameField, 0, y);
        gridPane.add(newMonthDurationField, 1, y);
        gridPane.add(leapYearLengthField, 2, y);
        gridPane.add(leapYearGapField, 3, y);
        getDialogPane().getScene().getWindow().sizeToScene();
    }

}
