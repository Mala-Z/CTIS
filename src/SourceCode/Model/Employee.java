package SourceCode.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {

    private final IntegerProperty employeeBarcode;
    private final IntegerProperty identificationNo;
    private final StringProperty employeeName;
    private final IntegerProperty employeeTelephoneNo;

    //DEFAULT CONSTRUCTOR
    public Employee() {
            this(0, 0, null, 0);
    }

    //CONSTRUCTOR
    public Employee(int employeeBarcode, int identificationNo, String employeeName, int employeeTelephoneNo) {
        this.employeeBarcode = new SimpleIntegerProperty(employeeBarcode);
        this.identificationNo = new SimpleIntegerProperty(identificationNo);
        this.employeeName = new SimpleStringProperty(employeeName);
        this.employeeTelephoneNo = new SimpleIntegerProperty(employeeTelephoneNo);

    }

    //GET METHODS
    public final int getBarcode() {
        return employeeBarcode.get();
    }

    public final int getIdentificationNo() {
        return identificationNo.get();
    }

    public final String getName() { return employeeName.get(); }

    public final int getTelephoneNo() { return employeeTelephoneNo.get(); }

    public IntegerProperty employeeBarcodeProperty() {
        return employeeBarcode;
    }

    public IntegerProperty identificationNoProperty() {
        return identificationNo;
    }

    public StringProperty nameProperty() {
        return employeeName;
    }

    public IntegerProperty telephoneProperty() {
        return employeeTelephoneNo;
    }

}
