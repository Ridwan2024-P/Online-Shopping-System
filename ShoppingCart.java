import java.util.List;
import java.util.ArrayList;

class ShoppingCart {
    private List<Product> productList;

    public ShoppingCart() {
        this.productList = new ArrayList<>();
    }

   public void addProduct(Product product) {
        for (Product cartProduct : productList) {
            if (cartProduct.getProductID().equals(product.getProductID())) {
                // Product already in the cart, update quantity and total price
                int newQuantity = cartProduct.getQuantity() + 1;
                cartProduct.setQuantity(newQuantity);
                System.out.println("Quantity updated in the shopping cart.");
                return;
            }
        }

        // Product not in the cart, add it
        productList.add(product);
        System.out.println("Product added to the shopping cart.");
    }

    public void removeProduct(Product product) {
        if (productList.contains(product)) {
            productList.remove(product);
            System.out.println("Product removed from the shopping cart.");
        } else {
            System.out.println("Product not found in the shopping cart.");
        }
    }

    public boolean containsProduct(Product product) {
        return productList.contains(product);
    }

    public double calculateTotalCost() {
        double totalCost = 0;
        for (Product product : productList) {
            totalCost += product.getPrice();
        }
        return totalCost;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void displayShoppingCart() {
        if (productList.isEmpty()) {
            System.out.println("Shopping cart is empty.");
        } else {
            System.out.println("Shopping Cart Contents:");
            for (Product product : productList) {
                System.out.println(product.toString());
            }
        }
    }
}