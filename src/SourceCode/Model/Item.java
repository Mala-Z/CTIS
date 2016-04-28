package SourceCode.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    private final IntegerProperty itemBarcode;
    private final StringProperty itemNo;
    private final StringProperty itemName;
    private final StringProperty category;

    //DEFAULT CONSTRUCTOR
    public Item() {
        this(0, null, null, null);
    }

    //CONSTRUCTOR
    public Item(int itemBarcode, String itemNo, String itemName, String category) {
        this.itemBarcode = new SimpleIntegerProperty(itemBarcode);
        this.itemNo = new SimpleStringProperty(itemNo);
        this.itemName = new SimpleStringProperty(itemName);
        this.category = new SimpleStringProperty(category);
    }

    //GET METHODS
    public final int getBarcode() {
        return itemBarcode.get();
    }

    public final String getItemNo() {
        return itemNo.get();
    }

    public final String getItemName() {
        return itemName.get();
    }

    public String getCategory() { return category.get(); }


    public IntegerProperty itemBarcodeProperty() { return itemBarcode; }

    public StringProperty itemNoProperty() { return itemNo; }

    public StringProperty itemNameProperty() { return itemName; }

    public StringProperty categoryProperty() { return category; }

    public void setItemBarcode(int itemBarcode) {
        this.itemBarcode.set(itemBarcode);
    }

    public void setItemNo(String itemNo) {
        this.itemNo.set(itemNo);
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public void setCategory(String category) {
        this.category.set(category);
    }
}
