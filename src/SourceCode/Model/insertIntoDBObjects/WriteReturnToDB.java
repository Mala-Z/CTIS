package SourceCode.Model.insertIntoDBObjects;

public class WriteReturnToDB {
    private String itembarcode;
    private String timeReturned;
    private String functional;


    public WriteReturnToDB() {
    }

    public WriteReturnToDB(String itembarcode, String timeReturned, String functional) {
        this.itembarcode = itembarcode;
        this.timeReturned = timeReturned;
        this.functional = functional;
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

    public String getFunctional() {
        return functional;
    }

    public void setFunctional(String functional) {
        this.functional = functional;
    }

    @Override
    public String toString() {
        return getItembarcode() +" "+ getTimeReturned();
    }
}
