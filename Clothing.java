
class Clothing extends Product {
    private String size;
    private String color;


    public Clothing(String productID, String productName, double price, String size, String color,int quantity) {
        super(productID, productName, price, "");
        this.size = size;
        this.color = color;
        setQuantity(quantity); 
       
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void displayClothingDetails() {
        System.out.println("Clothing Details:");
        System.out.println("Size: " + size);
        System.out.println("Color: " + color);
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nSize: " + size +
                "\nColor: " + color;
    }
}