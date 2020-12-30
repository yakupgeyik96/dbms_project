package dbmsproj.entity;

import java.time.LocalDate;

public class ReservationForm {
    private int reservationNumber;
    private int tenantNumber;
    private int standNumber;
    private double totalPrice;
    private LocalDate dateOfMade;

    public ReservationForm() {
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public int getTenantNumber() {
        return tenantNumber;
    }

    public void setTenantNumber(int tenantNumber) {
        this.tenantNumber = tenantNumber;
    }

    public int getStandNumber() {
        return standNumber;
    }

    public void setStandNumber(int standNumber) {
        this.standNumber = standNumber;
    }

    public LocalDate getDateOfMade() {
        return dateOfMade;
    }

    public void setDateOfMade(LocalDate dateOfMade) {
        this.dateOfMade = dateOfMade;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationNumber=" + reservationNumber +
                ", tenantNumber=" + tenantNumber +
                ", standNumber=" + standNumber +
                ", dateOfMade=" + dateOfMade +
                '}';
    }
}
