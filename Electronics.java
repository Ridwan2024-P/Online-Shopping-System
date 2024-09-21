class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

  public Electronics(String productID, String productName, double price, String brand, int warrantyPeriod, int quantity) {
        super(productID, productName, price, "");
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
        setQuantity(quantity); 
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public void displayElectronicsDetails() {
        System.out.println("Electronics Details:");
        System.out.println("Brand: " + brand);
        System.out.println("Warranty Period: " + warrantyPeriod + " months");
    }

    public boolean isUnderWarranty() {
        return warrantyPeriod > 0;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nBrand: " + brand +
                "\nWarranty Period: " + warrantyPeriod + " months";
    }
}
