package dbmsproj.entity;

import java.time.LocalDate;

public class Tenant {
    private int tenantNumber;
    private String firstName;
    private String lastName;
    private String tcNumber;
    private String phoneNumber;

    public Tenant() {
    }

    public int getTenantNumber() {
        return tenantNumber;
    }

    public void setTenantNumber(int tenantNumber) {
        this.tenantNumber = tenantNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTcNumber() {
        return tcNumber;
    }

    public void setTcNumber(String tcNumber) {
        this.tcNumber = tcNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "tenantNumber=" + tenantNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", tcNumber='" + tcNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
