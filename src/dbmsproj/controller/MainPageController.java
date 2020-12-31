package dbmsproj.controller;

import dbmsproj.service.SectionDao;
import dbmsproj.service.TenantDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private TextField textSignupTC;

    @FXML
    private TextField textFieldSignupFirstName;

    @FXML
    private TextField textFieldSignupLastName;

    @FXML
    private TextField textFieldSignupPhoneNumber;

    @FXML
    void onSelectSectionClick(ActionEvent event) {

        //DB function get_unreserved_stands()

        SectionDao sectionDao = new SectionDao();

        sectionDao.getUnreservedStands(
                comboBoxSelectSection.getValue(),
                takenDays.get(0),
                takenDays.get(takenDays.size() - 1)
        );

        /*ArrayList<LocalDate> localDates =
                (ArrayList<LocalDate>) sectionDao.getSectionReservedDays(comboBoxSelectSection.getValue());

        for (LocalDate localDate : localDates) {
            System.out.println("---> " + localDate);
        }*/

//        LocalDate[] localDates = new LocalDate[takenDays.size()];
//
//        for (int i = 0; i < takenDays.size(); i++) {
//            localDates[i] = takenDays.get(i);
//        }
//
//        String section = comboBoxSelectSection.getValue();
//
//        System.out.println("Section: " + section);
//        System.out.println("Dates: " + Arrays.toString(localDates));
    }

    @FXML
    void onDateAction(ActionEvent event) {
        LocalDate localDate = datePickerSelectSection.getValue();
        takenDays.add(localDate);
    }

    @FXML
    void onQueryTCNumber(ActionEvent event) {
        TenantDAO tenantDAO = new TenantDAO();
        List<String> tcNumbers = tenantDAO.getTenantTCNumbers();

        for(String tcNumber : tcNumbers) {
            if(tcNumber.equals(textFieldTC.getText())) {
                setReservationComponents(false);
            }
        }
    }

    @FXML
    void signupButtonClicked(ActionEvent event) {
        TenantDAO tenantDAO = new TenantDAO();

        String firstName = textFieldSignupFirstName.getText();
        String lastName = textFieldSignupLastName.getText();
        String tcNumber = textSignupTC.getText();
        String phoneNumber = textFieldSignupPhoneNumber.getText();

        boolean result = tenantDAO.signupUser(firstName, lastName, phoneNumber, tcNumber);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (result) {
            alert.setTitle("Üyelik Bilgilendirme");
            alert.setHeaderText("Üyelik Tamamlandı.");
            alert.showAndWait().ifPresent(rs -> {
               if (rs == ButtonType.OK) {
                   System.out.println("Pressed OK...");
               }
            });

            clearSignupTextFields();
        }
        else {
            alert.setHeaderText("Üyelik başarısız. Bilgilerinizi kontrol ediniz.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.CANCEL) {
                    System.out.println("Canceled...");
                }
            });
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

    private void clearSignupTextFields() {
        textFieldSignupFirstName.clear();
        textFieldSignupLastName.clear();
        textSignupTC.clear();
        textFieldSignupPhoneNumber.clear();
    }
}
