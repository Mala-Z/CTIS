package SourceCode.Model;

import javafx.beans.property.*;

import java.time.format.DateTimeFormatter;

public class SearchedItem {
    private final StringProperty employeeName;
    private final StringProperty itemName;
    private final StringProperty itemPlace;
    private final ObjectProperty<DateTimeFormatter> timeTaken;

    //DEFAULT CONSTRUCTOR
    public SearchedItem() {
        this(null, null, null, null);
    }

    //CONSTRUCTOR
    public SearchedItem(String employeeName, String itemName, String itemPlace, String timeTaken) {
        this.employeeName = new SimpleStringProperty(employeeName);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemPlace = new SimpleStringProperty(itemPlace);
        this.timeTaken = new SimpleObjectProperty(timeTaken);
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public StringProperty employeeNameProperty() {
        return employeeName;
    }

    public String getItemName() {
        return itemName.get();
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public String getItemPlace() {
        return itemPlace.get();
    }

    public StringProperty itemPlaceProperty() {
        return itemPlace;
    }

    public DateTimeFormatter getTimeTaken() {
        return timeTaken.get();
    }

    public ObjectProperty timeTakenProperty() {
        return timeTaken;
    }

}
