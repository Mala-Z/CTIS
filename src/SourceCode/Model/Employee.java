package SourceCode.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {

    private final IntegerProperty employeeBarcode;
    private final StringProperty employeeNo;
    private final StringProperty employeeName;
    private final IntegerProperty phoneNumber;

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
    public final int getBarcode() {
        return employeeBarcode.get();
    }

    public final String getEmployeeNo() {
        return employeeNo.get();
    }

    public final String getName() { return employeeName.get(); }

    public final int getTelephoneNo() { return phoneNumber.get(); }

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
