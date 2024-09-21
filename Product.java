import java.io.Serializable;
abstract class Product {
    private String productID;
    private String productName;
    private double price;
    private String info;
    private int quantity;

    public Product(String productID, String productName, double price, String info) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.info = info;
        this.quantity=quantity;
    }
     public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "Product ID: " + productID +
                "\nProduct Name: " + productName +
                "\nPrice: $" + price +
                "\nQuantity: " + quantity;
    }
}
