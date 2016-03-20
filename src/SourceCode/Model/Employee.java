package SourceCode.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {

    private final IntegerProperty employeeBarcode;
    private final StringProperty identificationNo;
    private final StringProperty employeeName;
    private final IntegerProperty employeeTelephoneNo;

    //DEFAULT CONSTRUCTOR
    public Employee() {
            this(0, null, null, 0);
    }

    //CONSTRUCTOR
    public Employee(int employeeBarcode, String identificationNo, String employeeName, int employeeTelephoneNo) {
        this.employeeBarcode = new SimpleIntegerProperty(employeeBarcode);
        this.identificationNo = new SimpleStringProperty(identificationNo);
        this.employeeName = new SimpleStringProperty(employeeName);
        this.employeeTelephoneNo = new SimpleIntegerProperty(employeeTelephoneNo);

    }

    //GET METHODS
    public final int getBarcode() {
        return employeeBarcode.get();
    }

    public final String getIdentificationNo() {
        return identificationNo.get();
    }

    public final String getName() { return employeeName.get(); }

    public final int getTelephoneNo() { return employeeTelephoneNo.get(); }

    public IntegerProperty employeeBarcodeProperty() {
        return employeeBarcode;
    }

    public StringProperty identificationNoProperty() {
        return identificationNo;
    }

    public StringProperty nameProperty() {
        return employeeName;
    }

    public IntegerProperty telephoneProperty() {
        return employeeTelephoneNo;
    }

}
