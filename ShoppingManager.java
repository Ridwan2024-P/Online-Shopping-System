import java.util.List;
interface ShoppingManager {
    void addProduct(Product product);

    void deleteProduct(String productID);

    void printProductList();

    // Add other methods as needed
    List<Product> getProductList();

    ShoppingCart getShoppingCart();

    List<Electronics> getElectronicsList();

    List<Clothing> getClothingList();

    double calculateDiscount();
}
