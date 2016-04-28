package SourceCode.Model.adminTableViewObjects;

public class BorrowedItemObj {

        private String employeeName;
        private String itemName;
        private String timeTaken;
        private String timeReturned;

    public BorrowedItemObj(String employeeName, String itemName, String timeTaken, String timeReturned) {
        this.employeeName = employeeName;
        this.itemName = itemName;
        this.timeTaken = timeTaken;
        this.timeReturned = timeReturned;
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

    public String getTimeReturned() {
        return timeReturned;
    }

    public void setTimeReturned(String timeReturned) {
        this.timeReturned = timeReturned;
    }
}
