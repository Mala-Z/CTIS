package SourceCode.Model.userTableViewObjects;

public class ReturnObj {
    private String employeeName;
    private String itemCategory;
    private String itemName;
    private String place;
    private String timeTaken;

    public ReturnObj(String employeeName, String itemCategory, String itemName, String place, String timeTaken) {
        this.employeeName = employeeName;
        this.itemCategory = itemCategory;
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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
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
