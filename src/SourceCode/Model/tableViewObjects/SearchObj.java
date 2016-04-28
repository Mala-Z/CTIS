package SourceCode.Model.tableViewObjects;

public class SearchObj {
    private String employeeName;
    private String phoneNo;
    private String itemName;
    private String place;
    private String timeTaken;

    public SearchObj(String employeeName, String phoneNo, String itemName, String place, String timeTaken) {
        this.employeeName = employeeName;
        this.phoneNo = phoneNo;
        this.itemName = itemName;
        this.place = place;
        this.timeTaken = timeTaken;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }
}
