package SourceCode.Model.userTableViewObjects;

public class ConsumablesObj {
    private String itemName;
    private String quantity;
    private String timeTaken;

    public ConsumablesObj(String itemName, String quantity, String timeTaken) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.timeTaken = timeTaken;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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
}
