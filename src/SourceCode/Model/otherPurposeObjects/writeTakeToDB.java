package SourceCode.Model.otherPurposeObjects;

public class WriteTakeToDB {
    private String employeeBarcode;
    private String itemBarcode;
    private String timeTaken;
    private String place;
    private String placeReference;

    public WriteTakeToDB(String employeeBarcode, String itemBarcode, String timeTaken, String place, String placeReference) {
        this.employeeBarcode = employeeBarcode;
        this.itemBarcode = itemBarcode;
        this.timeTaken = timeTaken;
        this.place = place;
        this.placeReference = placeReference;
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

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
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

    @Override
    public String toString() {
        return getEmployeeBarcode() +" "+ getItemBarcode() +" " + getTimeTaken() +" " + getPlace()+"" + getPlaceReference();
    }
}
