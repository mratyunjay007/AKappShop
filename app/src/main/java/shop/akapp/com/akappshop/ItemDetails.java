package shop.akapp.com.akappshop;

public class ItemDetails {

    private String itemName;
    private String price;
    private String companyName;
    private String itemDescription;
    private String emi;
    private String delievery;
    private String completeDescription;
    private int itemImage;
    private int stamp;

    public ItemDetails(String itemName, String price, String companyName, String itemDescription,
                       String emi, String delievery, String completeDescription, int itemImage, int stamp) {
        this.itemName = itemName;
        this.price = price;
        this.companyName = companyName;
        this.itemDescription = itemDescription;
        this.emi = emi;
        this.delievery = delievery;
        this.completeDescription = completeDescription;
        this.itemImage = itemImage;
        this.stamp = stamp;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getDelievery() {
        return delievery;
    }

    public void setDelievery(String delievery) {
        this.delievery = delievery;
    }

    public String getCompleteDescription() {
        return completeDescription;
    }

    public void setCompleteDescription(String completeDescription) {
        this.completeDescription = completeDescription;
    }

    public int getItemImage() {
        return itemImage;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }

}
