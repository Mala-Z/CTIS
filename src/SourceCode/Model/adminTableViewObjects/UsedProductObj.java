package SourceCode.Model.adminTableViewObjects;

public class UsedProductObj {
    private String employeeName;
    private String itemNumber;
    private String itemName;
    private String timeTaken;
    private int quantityTaken;

    public UsedProductObj(String employeeName, String itemNumber, String itemName, String timeTaken, int quantityTaken) {
        this.employeeName = employeeName;
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.timeTaken = timeTaken;
        this.quantityTaken = quantityTaken;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public int getQuantityTaken() {
        return quantityTaken;
    }

    public void setQuantityTaken(int quantityTaken) {
        this.quantityTaken = quantityTaken;
    }
}
