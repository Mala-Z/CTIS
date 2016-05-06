package SourceCode.Model.adminTableViewObjects;

public class EmployeeObj {
    private String employeeBarcode;
    private String employeeNo;
    private String employeeName;
    private String phoneNumber;

    public EmployeeObj() {

    }

    public EmployeeObj(String employeeBarcode, String employeeNo, String employeeName, String phoneNumber) {
        this.employeeBarcode = employeeBarcode;
        this.employeeNo = employeeNo;
        this.employeeName = employeeName;
        this.phoneNumber = phoneNumber;
    }

    public String getEmployeeBarcode() {
        return employeeBarcode;
    }

    public void setEmployeeBarcode(String employeeBarcode) {
        this.employeeBarcode = employeeBarcode;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return getEmployeeBarcode();
    }
}
