package SourceCode.Model.adminTableViewObjects;

public class ItemObj {
    private String itemBarcode;
    private String itemNo;
    private String itemName;
    private String category;

    public ItemObj() {
    }

    public ItemObj(String itemBarcode, String itemNo, String itemName, String category) {
        this.itemBarcode = itemBarcode;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.category = category;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
