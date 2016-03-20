package SourceCode.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    private final IntegerProperty itemBarcode;
    private final IntegerProperty itemNo;
    private final StringProperty description;

    //DEFAULT CONSTRUCTOR
    public Item() {
        this(0, 0, null);
    }

    //CONSTRUCTOR
    public Item(int itemBarcode, int itemNo, String description) {
        this.itemBarcode = new SimpleIntegerProperty(itemBarcode);
        this.itemNo = new SimpleIntegerProperty(itemNo);
        this.description = new SimpleStringProperty(description);
    }

    //GET METHODS
    public final int getBarcode() {
        return itemBarcode.get();
    }

    public final int getItemNo() {
        return itemNo.get();
    }

    public final String getDescription() {
        return description.get();
    }

    public IntegerProperty itemBarcodeProperty() {
        return itemBarcode;
    }

    public IntegerProperty itemNoProperty() {
        return itemNo;
    }

    public StringProperty descriptionProperty() {
        return description;
    }
}
