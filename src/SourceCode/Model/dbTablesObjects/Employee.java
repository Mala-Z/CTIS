package SourceCode.Model.dbTablesObjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {

    private IntegerProperty employeeBarcode;
    private StringProperty employeeNo;
    private StringProperty employeeName;
    private IntegerProperty phoneNumber;

    //DEFAULT CONSTRUCTOR
    public Employee() {
            this(0, null, null, 0);

    }

    //CONSTRUCTOR
    public Employee(int employeeBarcode, String employeeNo, String employeeName, int phoneNumber) {
        this.employeeBarcode = new SimpleIntegerProperty(employeeBarcode);
        this.employeeNo = new SimpleStringProperty(employeeNo);
        this.employeeName = new SimpleStringProperty(employeeName);
        this.phoneNumber = new SimpleIntegerProperty(phoneNumber);

    }

    //GET METHODS
    public int getBarcode() {
        return employeeBarcode.get();
    }

    public String getEmployeeNo() {
        return employeeNo.get();
    }

    public String getName() { return employeeName.get(); }

    public int getTelephoneNo() { return phoneNumber.get(); }

    public void setEmployeeBarcode(int employeeBarcode) {
        this.employeeBarcode.set(employeeBarcode);
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo.set(employeeNo);
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName.set(employeeName);
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public IntegerProperty employeeBarcodeProperty() {
        return employeeBarcode;
    }

    public StringProperty employeeNoProperty() {
        return employeeNo;
    }

    public StringProperty nameProperty() {
        return employeeName;
    }

    public IntegerProperty telephoneProperty() {
        return phoneNumber;
    }

}
