package SourceCode.Model.userTableViewObjects;

public class TakeObj {
    private String itemNumber;
    private String itemCategory;
    private String itemName;
    private String place;

    public TakeObj(String itemNumber, String itemCategory, String itemName, String place) {
        this.itemNumber = itemNumber;
        this.itemCategory = itemCategory;
        this.itemName = itemName;
        this.place = place;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
