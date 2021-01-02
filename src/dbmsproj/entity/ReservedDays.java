package dbmsproj.entity;

import java.time.LocalDate;

public class ReservedDays {
    private int reservationNumber;
    private LocalDate reservedDays;

    public ReservedDays() {
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public LocalDate getReservedDays() {
        return reservedDays;
    }

    public void setReservedDays(LocalDate reservedDays) {
        this.reservedDays = reservedDays;
    }

    @Override
    public String toString() {
        return "ReservedDays{" +
                "reservationNumber=" + reservationNumber +
                ", reservedDays=" + reservedDays +
                '}';
    }
}
