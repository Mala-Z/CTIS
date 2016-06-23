package SourceCode.Model.adminTableViewObjects;

public class ItemObj {
    private String itemBarcode;
    private String itemNo;
    private String itemName;
    private String itemCategory;

    public ItemObj() {
    }

    public ItemObj(String itemBarcode, String itemNo, String itemName, String itemCategory) {
        this.itemBarcode = itemBarcode;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }
}
