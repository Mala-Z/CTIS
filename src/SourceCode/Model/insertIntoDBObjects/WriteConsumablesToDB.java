package SourceCode.Model.insertIntoDBObjects;

public class WriteConsumablesToDB {
    private String employeeBarcode;
    private String itemBarcode;
    private String quantity;
    private String timeTaken;

    public WriteConsumablesToDB(String employeeBarcode, String itemBarcode, String quantity, String timeTaken) {
        this.employeeBarcode = employeeBarcode;
        this.itemBarcode = itemBarcode;
        this.quantity = quantity;
        this.timeTaken = timeTaken;
    }

    public String getEmployeeBarcode() {
        return employeeBarcode;
    }

    public void setEmployeeBarcode(String employeeBarcode) {
        this.employeeBarcode = employeeBarcode;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    @Override
    public String toString() {
        return getEmployeeBarcode() +" "+ getItemBarcode() +" " + getQuantity() +" " + getTimeTaken();
    }
}
