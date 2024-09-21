import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
class WestminsterShoppingManager implements ShoppingManager, Serializable {
    private List<Product> productList;
    private ShoppingCart shoppingCart;

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
        this.shoppingCart = new ShoppingCart();
    }

    public void addProduct(Product product) {
        if (productList.size() < 50) {
            // Check if the total quantity will exceed the maximum allowed (50)
            int totalQuantity = product.getQuantity();
            for (Product existingProduct : productList) {
                totalQuantity += existingProduct.getQuantity();
            }

            if (totalQuantity > 50) {
                System.out.println("Cannot add more products. Maximum limit reached (max 50 products).");
            } else {
                productList.add(product);
                System.out.println("Product added successfully.");
            }
        } else {
            System.out.println("Cannot add more products. Maximum limit reached (max 50 products).");
        }
    }


    public void deleteProduct(String productID) {
        Product deletedProduct = null;
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                deletedProduct = product;
                productList.remove(product);
                break;
            }
        }

        if (deletedProduct != null) {
            System.out.println("Product deleted successfully. Product Info:\n" + deletedProduct.toString());
            System.out.println("Total number of products left: " + productList.size());
        } else {
            System.out.println("Product with ID " + productID + " not found.");
        }
    }

    public void printProductList() {
        System.out.println("===== Product List =====");
        productList.forEach(product -> System.out.println(product.toString()));
    }

   public void saveToTextFile(String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        for (Product product : productList) {
            if (product instanceof Electronics) {
                writer.write("Electronics|");
            } else if (product instanceof Clothing) {
                writer.write("Clothing|");
            }

            // Write common attributes
            writer.write("ProductID :" + product.getProductID() + "|");
            writer.write("ProductName :" + product.getProductName() + "|");
            writer.write("Price :" + product.getPrice() + "|");
            writer.write("Info :" + product.getInfo() + "|");
            writer.write("Quantity :" + product.getQuantity() + "|");

            // Write type-specific attributes
            if (product instanceof Electronics) {
                Electronics electronicsProduct = (Electronics) product;
                writer.write("getBrand :" + electronicsProduct.getBrand() + "|");
                writer.write("WarrantyPeriod :" + electronicsProduct.getWarrantyPeriod() + "|");
            } else if (product instanceof Clothing) {
                Clothing clothingProduct = (Clothing) product;
                writer.write("getSize :" + clothingProduct.getSize() + "|");
                writer.write("getColor :" + clothingProduct.getColor() + "|");
            }

            writer.newLine();
        }
        System.out.println("Product list saved successfully to " + fileName);
    } catch (IOException e) {
        System.out.println("Error saving product list to file: " + e.getMessage());
    }
}
    public Product getProductByID(String productID) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public List<Electronics> getElectronicsList() {
        List<Electronics> electronicsList = new ArrayList<>();
        for (Product product : productList) {
            if (product instanceof Electronics) {
                electronicsList.add((Electronics) product);
            }
        }
        return electronicsList;
    }

    public List<Clothing> getClothingList() {
        List<Clothing> clothingList = new ArrayList<>();
        for (Product product : productList) {
            if (product instanceof Clothing) {
                clothingList.add((Clothing) product);
            }
        }
        return clothingList;
    }

   public double calculateDiscount() {
        double discountAmount = 0.0;

        Map<String, Integer> categoryQuantityMap = new HashMap<>();

        for (Product product : shoppingCart.getProductList()) {
            int quantity = product.getQuantity();
            String category = getCategoryFromProduct(product);

            categoryQuantityMap.put(category, categoryQuantityMap.getOrDefault(category, 0) + quantity);
        }

        for (int quantity : categoryQuantityMap.values()) {
            if (quantity >= 3) {
                discountAmount += calculateCategoryDiscount(quantity);
            }
        }

        return discountAmount;
    }

    private String getCategoryFromProduct(Product product) {
        if (product instanceof Electronics) {
            return "Electronics";
        } else if (product instanceof Clothing) {
            return "Clothing";
        } else {
            return "Other";
        }
    }

    private double calculateCategoryDiscount(int quantity) {
        return 0.2 * quantity;
    }
}

