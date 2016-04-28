package SourceCode.Model;

import javafx.beans.property.*;

import java.time.format.DateTimeFormatter;

public class BorrowedItem {
    private final IntegerProperty id;
    private final IntegerProperty employeeBarcode;
    private final IntegerProperty itemBarcode;
    private final StringProperty place;
    private final ObjectProperty<DateTimeFormatter> timeTaken;
    private final ObjectProperty<DateTimeFormatter> timeReturned;
    //set a place for the item - either address or car plate number

    //DEFAULT CONSTRUCTOR
    public BorrowedItem() {
        this(0, 0, 0, null, null, null);
    }

    //CONSTRUCTOR
    public BorrowedItem(int id, int employeeBarcode, int itemBarcode, String place, String timeTaken, String timeReturned) {
        this.id = new SimpleIntegerProperty(id);
        this.employeeBarcode = new SimpleIntegerProperty(employeeBarcode);
        this.itemBarcode = new SimpleIntegerProperty(itemBarcode);
        this.place = new SimpleStringProperty(place);
        this.timeTaken = new SimpleObjectProperty(timeTaken);
        this.timeReturned = new SimpleObjectProperty(timeReturned);
    }

    //GET METHODS
    public final int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty employeeBarcodeProperty() {
        return employeeBarcode;
    }

    public IntegerProperty itemBarcodeProperty() { return itemBarcode; }

    public StringProperty placeProperty(){ return place; }

    public ObjectProperty timeTakenProperty() {
        return timeTaken;
    }

    public ObjectProperty timeReturnedProperty() {
        return timeReturned;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setEmployeeBarcode(int employeeBarcode) {
        this.employeeBarcode.set(employeeBarcode);
    }

    public void setItemBarcode(int itemBarcode) {
        this.itemBarcode.set(itemBarcode);
    }

    public void setPlace(String place) {
        this.place.set(place);
    }

    public void setTimeTaken(DateTimeFormatter timeTaken) {
        this.timeTaken.set(timeTaken);
    }

    public void setTimeReturned(DateTimeFormatter timeReturned) {
        this.timeReturned.set(timeReturned);
    }
}
