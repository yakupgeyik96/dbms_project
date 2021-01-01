package dbmsproj.controller;

import dbmsproj.entity.ReservationForm;
import dbmsproj.entity.Stand;
import dbmsproj.service.ReservationFormDAO;
import dbmsproj.service.ReservedDaysDAO;
import dbmsproj.service.SectionDao;
import dbmsproj.service.TenantDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MainPageController implements Initializable {
    Stand selectedStand;
    ArrayList<LocalDate> takenDays;
    String tcNumber;
    double standTotalPrice;

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
    private ListView<Stand> unreservedStandsList;

    @FXML
    private Label totalStandPrice;

    @FXML
    void onSelectSectionClick(ActionEvent event) {
        SectionDao sectionDao = new SectionDao();
        List<Stand> unreservedStands = new ArrayList<>();

        unreservedStands = sectionDao.getUnreservedStands(
                comboBoxSelectSection.getValue(),
                takenDays.get(0),
                takenDays.get(takenDays.size() - 1)
        );


        ObservableList<Stand> stands = FXCollections.observableArrayList(unreservedStands);

        unreservedStandsList.setItems(stands);

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
        System.out.println(takenDays.size());
    }

    @FXML
    void onDateAction(ActionEvent event) {
        LocalDate localDate = datePickerSelectSection.getValue();
        for (LocalDate takenDay : takenDays) {
            if ((takenDay.getYear() == localDate.getYear())
                    && (takenDay.getMonth() == localDate.getMonth())
                    && (takenDay.getDayOfMonth() == localDate.getDayOfMonth()))
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Aynı tarih eklenemez.");
                return;
            }
        }

        takenDays.add(localDate);
    }

    @FXML
    void onQueryTCNumber(ActionEvent event) {
        TenantDAO tenantDAO = new TenantDAO();
        List<String> tcNumbers = tenantDAO.getTenantTCNumbers();
        tcNumber = textFieldTC.getText();

        for(String tcNumber : tcNumbers) {
            if(tcNumber.equals(textFieldTC.getText())) {
                setReservationComponents(false);
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Lütfen üye olunuz.");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK...");
                setReservationComponents(true);
            }
        });
    }

    @FXML
    void onClearButtonClicked(ActionEvent event) {
        clearReservationInfos();
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

    @FXML
    void onUnreservedStandsListClicked(MouseEvent event) {
        selectedStand = unreservedStandsList.getSelectionModel().getSelectedItem();
        standTotalPrice = selectedStand.getArea() * selectedStand.getExposedSides() * 11.5 * takenDays.size();
        totalStandPrice.setText(standTotalPrice + " TL");
    }

    @FXML
    void onAcceptButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        TenantDAO tenantDAO = new TenantDAO();
        ReservationFormDAO reservationFormDAO = new ReservationFormDAO();
        ReservedDaysDAO reservedDaysDAO = new ReservedDaysDAO();

        LocalDate currentDate = LocalDate.now();
        int tenantNumber = tenantDAO.getTenantNumberByTc(tcNumber);
        int standNumber = selectedStand.getStandNumber();
        double totalPrice = standTotalPrice;

        reservationFormDAO.saveCurrentReservation(currentDate, tenantNumber, standNumber, totalPrice);

        alert.setTitle("Rezervasyon İşlemi Bilgilendirme");
        alert.setHeaderText("Rezervasyon işlemi gerçekleştirilmiştir.");
        alert.showAndWait()
                .ifPresent(rs -> {
                   if (rs == ButtonType.OK) {
                       for (LocalDate date : takenDays) {
                           int reservationNumber = reservationFormDAO
                                   .getReservationNumberByTenantAndStandNumber(tenantNumber, standNumber, LocalDate.now());
                           reservedDaysDAO.saveReservedDays(reservationNumber, date);
                       }
                   }
                });
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

    private void clearReservationInfos() {
        datePickerSelectSection.getEditor().clear();
        datePickerSelectSection.setValue(null);
        takenDays.clear();
    }
}
