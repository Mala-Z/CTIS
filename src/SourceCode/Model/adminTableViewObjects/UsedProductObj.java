package SourceCode.Model.adminTableViewObjects;

public class UsedProductObj {
    private String employeeName;
    private String itemName;
    private String timeTaken;
    private String quantityTaken;

    public UsedProductObj(String employeeName, String itemName, String timeTaken, String quantityTaken) {
        this.employeeName = employeeName;
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

    public String getQuantityTaken() {
        return quantityTaken;
    }

    public void setQuantityTaken(String quantityTaken) {
        this.quantityTaken = quantityTaken;
    }
}
