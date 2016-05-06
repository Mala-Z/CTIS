package SourceCode.Model.insertIntoDBObjects;

public class WriteReturnToDB {
    private String itembarcode;
    private String timeReturned;

    public WriteReturnToDB() {
    }

    public WriteReturnToDB(String itembarcode, String timeReturned) {
        this.itembarcode = itembarcode;
        this.timeReturned = timeReturned;
    }

    public String getItembarcode() {
        return itembarcode;
    }

    public void setItembarcode(String itembarcode) {
        this.itembarcode = itembarcode;
    }

    public String getTimeReturned() {
        return timeReturned;
    }

    public void setTimeReturned(String timeReturned) {
        this.timeReturned = timeReturned;
    }

    @Override
    public String toString() {
        return getItembarcode() +" "+ getTimeReturned();
    }
}
