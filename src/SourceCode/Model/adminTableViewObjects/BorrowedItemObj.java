package SourceCode.Model.adminTableViewObjects;

public class BorrowedItemObj {

    private String employeeName;
    private String itemCategory;
    private String itemNumber;
    private String itemName;
    private String timeTaken;
    private String timeReturned;
    private String functional;

    public BorrowedItemObj(String employeeName, String itemCategory, String itemNumber, String itemName, String timeTaken, String timeReturned, String functional) {
        this.employeeName = employeeName;
        this.itemCategory = itemCategory;
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.timeTaken = timeTaken;
        this.timeReturned = timeReturned;
        this.functional = functional;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
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

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getTimeReturned() {
        return timeReturned;
    }

    public void setTimeReturned(String timeReturned) {
        this.timeReturned = timeReturned;
    }

    public String getFunctional() {
        return functional;
    }

    public void setFunctional(String functional) {
        this.functional = functional;
    }
}