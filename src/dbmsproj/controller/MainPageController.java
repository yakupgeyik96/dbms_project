package dbmsproj.controller;

import dbmsproj.service.SectionDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MainPageController implements Initializable {

    ArrayList<LocalDate> takenDays;

    @FXML
    private ComboBox<String> comboBoxSelectSection;

    @FXML
    private DatePicker datePickerSelectSection;

    @FXML
    private Button buttonSelectSection;

    @FXML
    private TextField textFieldTC;

    @FXML
    void onSelectSectionClick(ActionEvent event) {

        LocalDate[] localDates = new LocalDate[takenDays.size()];

        for (int i = 0; i < takenDays.size(); i++) {
            localDates[i] = takenDays.get(i);
        }

        String section = comboBoxSelectSection.getValue();

        System.out.println("Section: " + section);
        System.out.println("Dates: " + Arrays.toString(localDates));
    }

    @FXML
    void onDateAction(ActionEvent event) {
        LocalDate localDate = datePickerSelectSection.getValue();
        takenDays.add(localDate);
    }

    @FXML
    void onQueryTCNumber(ActionEvent event) {
        String tcNumber = textFieldTC.getText();
        if(tcNumber.equals("21835902314")) {
            setReservationComponents(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        takenDays = new ArrayList<>();
        setReservationComponents(true);


        getSections(); // get sections and set combobox values
    }

    private void setReservationComponents(boolean status) {
        comboBoxSelectSection.setDisable(status);
        datePickerSelectSection.setDisable(status);
        buttonSelectSection.setDisable(status);
    }

    private void getSections() {
        SectionDao sectionDao = new SectionDao();
        List<String> names = sectionDao.getSectionNames();
        ObservableList<String> sections = FXCollections.observableArrayList(names);
        comboBoxSelectSection.setItems(sections);
    }
}
