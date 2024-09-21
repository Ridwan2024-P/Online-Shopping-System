import java.util.Scanner;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = loadShoppingManagerFromTextFile();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("1. Add Product");
            System.out.println("2. Run Program");
            System.out.println("3. Delete Any Product");
            System.out.println("4. Save Product List to Text File"); // New option
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addProductFromConsole(shoppingManager);
                    break;
                case 2:
                    runProgram(shoppingManager);
                    break;
                case 3:
                    deleteProductFromConsole(shoppingManager);
                    break;
                case 4:
                    saveShoppingManagerToTextFile(shoppingManager); // New case
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 5);
    }

private static WestminsterShoppingManager loadShoppingManagerFromTextFile() {
    WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

    try (BufferedReader reader = new BufferedReader(new FileReader("productList.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Print each line to debug
            System.out.println("Read Line: " + line);

            // Parse the line and add products to the shopping manager
            Product parsedProduct = parseProductFromLine(line);

            if (parsedProduct != null) {
                shoppingManager.addProduct(parsedProduct);
            }
        }
    } catch (IOException e) {
        System.out.println("Error loading product list from file: " + e.getMessage());
    }

    return shoppingManager;
}

private static Product parseProductFromLine(String line) {
    // Your parsing logic goes here
    // Split the line and create a Product object based on your data format

    // Example: Assuming the format is "ProductType|ProductID|ProductName|Price|Info|Quantity|..."
    String[] parts = line.split("\\|");

    if (parts.length < 6) {
        System.out.println("Invalid line format: " + line);
        return null;
    }

    // Extract the values from the line using the appropriate prefixes
    String productType = parts[0];
    String productID = extractValue(parts, "ProductID");
    String productName = extractValue(parts, "ProductName");
    double price = Double.parseDouble(extractValue(parts, "Price"));
    String info = extractValue(parts, "Info");
    int quantity = Integer.parseInt(extractValue(parts, "Quantity"));

    // Create and return the appropriate Product subclass based on productType
    if (productType.equals("Electronics") && parts.length >= 8) {
        String brand = extractValue(parts, "getBrand");
        int warrantyPeriod = Integer.parseInt(extractValue(parts, "WarrantyPeriod"));
        return new Electronics(productID, productName, price, brand, warrantyPeriod, quantity);
    } else if (productType.equals("Clothing") && parts.length >= 8) {
        String size = extractValue(parts, "getSize");
        String color = extractValue(parts, "getColor");
        return new Clothing(productID, productName, price, size, color, quantity);
    } else {
        System.out.println("Invalid product type or incomplete data: " + line);
        return null;
    }
}

private static String extractValue(String[] parts, String prefix) {
    for (String part : parts) {
        if (part.startsWith(prefix)) {
            // Remove the prefix and return the rest of the string
            return part.substring(prefix.length() + 2);
        }
    }
    return ""; // Return an empty string if the prefix is not found
}


    private static void saveShoppingManagerToTextFile(WestminsterShoppingManager shoppingManager) {
        shoppingManager.saveToTextFile("productList.txt");
       // System.out.println("Product list saved to 'productList.txt'");
    }

    private static void addProductFromConsole(WestminsterShoppingManager shoppingManager) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select product type:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.print("Enter your choice: ");

        int option = scanner.nextInt();

        if (option == 1) {
            // Add Electronics product
            Electronics newElectronics = createElectronicsProductFromConsole(scanner);
             newElectronics.setInfo("Electronics"); // Set the info field
            shoppingManager.addProduct(newElectronics);

           
            System.out.println("Electronics product added successfully.");
            displayProductDetails(newElectronics);
        } else if (option == 2) {
            // Add Clothing product
            Clothing newClothing = createClothingProductFromConsole(scanner);
            newClothing.setInfo("Clothing"); // Set the info field
           
            shoppingManager.addProduct(newClothing);
            System.out.println("Clothing product added successfully.");
            displayProductDetails(newClothing);
        } else {
            System.out.println("Invalid choice. Product not added.");
        }
    }

    private static void displayProductDetails(Product product) {
        System.out.println("Product Details:");
        System.out.println(product.toString());
        System.out.println();
    }

    private static Electronics createElectronicsProductFromConsole(Scanner scanner) {
        System.out.print("Enter Electronics Product ID: ");
        String productID = scanner.next();

        System.out.print("Enter Electronics Product Name: ");
        String productName = scanner.next();

        System.out.print("Enter Electronics Product Price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter Electronics Brand: ");
        String brand = scanner.next();

        System.out.print("Enter Electronics Warranty Period (in months): ");
        int warrantyPeriod = scanner.nextInt();

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

         return new Electronics(productID, productName, price, brand, warrantyPeriod, quantity);
    }

    private static Clothing createClothingProductFromConsole(Scanner scanner) {
        System.out.print("Enter Clothing Product ID: ");
        String productID = scanner.next();

        System.out.print("Enter Clothing Product Name: ");
        String productName = scanner.next();

        System.out.print("Enter Clothing Product Price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter Clothing Size: ");
        String size = scanner.next();

        System.out.print("Enter Clothing Color: ");
        String color = scanner.next();

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        return new Clothing(productID, productName, price, size, color, quantity);
    }

    private static void runProgram(WestminsterShoppingManager shoppingManager) {
        shoppingManager.printProductList();  // Add this line to print the loaded product list (optional)
        SwingUtilities.invokeLater(() -> {
            ShoppingGUI shoppingGUI = new ShoppingGUI(shoppingManager);
            shoppingGUI.setVisible(true);
        });
    }

    private static void deleteProductFromConsole(WestminsterShoppingManager shoppingManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the product ID to delete: ");
        String productID = scanner.next();
        shoppingManager.deleteProduct(productID);
    }
}