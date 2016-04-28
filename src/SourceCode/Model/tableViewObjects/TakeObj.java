package SourceCode.Model.tableViewObjects;

public class TakeObj {
    private String itemNumber;
    private String itemName;
    private String place;

        public TakeObj(String itemNumber, String itemName, String place) {
            this.itemNumber = itemNumber;
            this.itemName = itemName;
            this.place = place;
        }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
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
