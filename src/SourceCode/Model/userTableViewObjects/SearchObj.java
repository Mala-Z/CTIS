package SourceCode.Model.userTableViewObjects;

public class SearchObj {
    private String employeeName;
    private String phoneNo;
    private String itemCategory;
    private String itemName;
    private String place;
    private String placeReference;
    private String timeTaken;

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

    public String getPlaceReference() {
        return placeReference;
    }

    public void setPlaceReference(String placeReference) {
        this.placeReference = placeReference;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public SearchObj(String employeeName, String phoneNo, String itemCategory, String itemName, String place, String placeReference, String timeTaken) {
        this.employeeName = employeeName;
        this.phoneNo = phoneNo;
        this.itemCategory = itemCategory;
        this.itemName = itemName;
        this.place = place;
        this.placeReference = placeReference;
        this.timeTaken = timeTaken;

    }
}