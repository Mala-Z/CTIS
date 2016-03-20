package SourceCode.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    private final IntegerProperty itemBarcode;
    private final StringProperty itemNo;
    private final StringProperty description;
    private final StringProperty category;

    //DEFAULT CONSTRUCTOR
    public Item() {
        this(0, null, null, null);
    }

    //CONSTRUCTOR
    public Item(int itemBarcode, String itemNo, String description, String category) {
        this.itemBarcode = new SimpleIntegerProperty(itemBarcode);
        this.itemNo = new SimpleStringProperty(itemNo);
        this.description = new SimpleStringProperty(description);
        this.category = new SimpleStringProperty(category);
    }

    //GET METHODS
    public final int getBarcode() {
        return itemBarcode.get();
    }

    public final String getItemNo() {
        return itemNo.get();
    }

    public final String getDescription() {
        return description.get();
    }

    public String getCategory() { return category.get(); }


    public IntegerProperty itemBarcodeProperty() { return itemBarcode; }

    public StringProperty itemNoProperty() { return itemNo; }

    public StringProperty descriptionProperty() { return description; }

    public StringProperty categoryProperty() { return category; }
}
