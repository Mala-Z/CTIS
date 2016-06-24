package SourceCode.Model.userTableViewObjects;

public class ConsumablesObj {
    private String itemName;
    private int quantity;
    private String timeTaken;

    public ConsumablesObj(String itemName, int quantity, String timeTaken) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }
}
