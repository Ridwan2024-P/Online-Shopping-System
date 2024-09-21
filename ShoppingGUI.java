import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class ShoppingGUI extends JFrame {
    private WestminsterShoppingManager shoppingManager;
    private JComboBox<String> productTypeComboBox;
    private JTable productListTable;
    private JTextArea productDetailsTextArea;
    private JButton addToCartButton;
    private JButton viewShoppingCartButton;
        private JPanel productDetailsPanel;

    public ShoppingGUI(WestminsterShoppingManager shoppingManager) {
        this.shoppingManager = shoppingManager;
        initializeComponents();
        setupLayout();
        setupListeners();
        updateProductList();
        productListTable.getSelectionModel().addListSelectionListener(e -> showProductDetails());

        setTitle("Westminster Shopping System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        productListTable = new JTable();
        productDetailsTextArea = new JTextArea();
        addToCartButton = new JButton("Add to Cart");
        viewShoppingCartButton = new JButton("Shopping Cart");
    }

   private void setupLayout() {
    setLayout(new BorderLayout());

    JPanel topPanel = new JPanel(new FlowLayout());
    topPanel.add(new JLabel("Product Type: "));
    topPanel.add(productTypeComboBox);
    topPanel.add(viewShoppingCartButton);
    add(topPanel, BorderLayout.NORTH);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    add(bottomPanel, BorderLayout.CENTER);

    //JPanel productDetailsPanel = new JPanel(new BorderLayout());
    productDetailsPanel = new JPanel(new BorderLayout()); 
    productDetailsTextArea.setEditable(false);
    productDetailsPanel.add(productDetailsTextArea, BorderLayout.CENTER);

    JScrollPane productListScrollPane = new JScrollPane(productListTable); // Wrap the JTable in a JScrollPane
    productListScrollPane.setPreferredSize(new Dimension(600, 200)); // Set preferred size for the scroll pane
    bottomPanel.add(productListScrollPane, BorderLayout.NORTH);
    bottomPanel.add(productDetailsPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(addToCartButton);
    add(buttonPanel, BorderLayout.SOUTH);
}

    private void setupListeners() {
        productTypeComboBox.addActionListener(e -> updateProductList());
        addToCartButton.addActionListener(e -> addSelectedProductToCart());
        viewShoppingCartButton.addActionListener(e -> viewShoppingCart());
    }

  private void updateProductDetailsPanel(Product product) {
    String productDetails = "Product ID: " + product.getProductID() + "\n" +
            "Availability: " + (product.getQuantity() > 0 ? "In Stock" : "Out of Stock") + "\n" +
            "Quantity: " + product.getQuantity() + "\n";  // Added quantity information

    // Check the type of product (Electronics or Clothing) and display relevant details
    if (product instanceof Electronics) {
        Electronics electronicsProduct = (Electronics) product;
        productDetails += "Category: Electronics\n" +
                "Brand: " + electronicsProduct.getBrand() + "\n" +
                "Warranty Period: " + electronicsProduct.getWarrantyPeriod() + " months\n" +
                "Color: " + getColorInformation(product) + "\n"; // Added color information
    } else if (product instanceof Clothing) {
        Clothing clothingProduct = (Clothing) product;
        productDetails += "Category: Clothing\n" +
                "Size: " + clothingProduct.getSize() + "\n" +
                "Color: " + getColorInformation(product) + "\n";
    }

    productDetailsTextArea.setText(productDetails);

    // Set the background color based on the quantity
  
    
}

// Helper method to get color information
private String getColorInformation(Product product) {
    int quantity1 = product.getQuantity();

    if (quantity1 <= 2) {
        System.out.println("Quantity: " + quantity1);
        return "red";
    } 
     if (quantity1 >= 3) {
        System.out.println("Quantity: " + quantity1);
        return "white";
    } else {
        System.out.println("Quantity: " + quantity1);
        return "white";
    }
}
private void showProductDetails() {
    int selectedRow = productListTable.getSelectedRow();
    if (selectedRow >= 0 && selectedRow < productListTable.getRowCount()) {
        String productID = (String) productListTable.getValueAt(selectedRow, 0);
        Product selectedProduct = shoppingManager.getProductByID(productID);
        updateProductDetailsPanel(selectedProduct);
    }
}

    private void updateProductList() {
        String selectedType = (String) productTypeComboBox.getSelectedItem();
        List<? extends Product> productsToShow = getProductsToShow(selectedType);

        Object[][] data = new Object[productsToShow.size()][5];
        for (int i = 0; i < productsToShow.size(); i++) {
            Product product = productsToShow.get(i);
            data[i][0] = product.getProductID();
            data[i][1] = product.getProductName();
            data[i][2] = (product instanceof Electronics) ? "Electronics" : "Clothing";
            data[i][3] = product.getPrice();
            data[i][4] = product.toString();
        }

        String[] columnNames = {"Product ID", "Product Name", "Category", "Price", "Info"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        productListTable.setModel(model);

        if (!productsToShow.isEmpty()) {
            updateProductDetailsPanel(productsToShow.get(0));
        }
          productListTable.repaint();
    }

    private List<? extends Product> getProductsToShow(String selectedType) {
        if (selectedType.equals("All")) {
            return shoppingManager.getProductList();
        } else if (selectedType.equals("Electronics")) {
            return shoppingManager.getElectronicsList();
        } else {
            return shoppingManager.getClothingList();
        }
    }

    private void addSelectedProductToCart() {
        int selectedRow = productListTable.getSelectedRow();
        if (selectedRow >= 0) {
            String productID = (String) productListTable.getValueAt(selectedRow, 0);
            Product selectedProduct = shoppingManager.getProductByID(productID);
            shoppingManager.getShoppingCart().addProduct(selectedProduct);
            JOptionPane.showMessageDialog(this, "Product added to the shopping cart.");
        }
    }
private void viewShoppingCart() {
    ShoppingCart shoppingCart = shoppingManager.getShoppingCart();
    List<Product> cartContents = shoppingCart.getProductList();

    Object[][] cartData = new Object[cartContents.size()][3];
    Map<String, Integer> productQuantityMap = new HashMap<>();
    double totalCost = 0.0;

    for (int i = 0; i < cartContents.size(); i++) {
        Product product = cartContents.get(i);
        cartData[i][0] = product.getProductID();
        int quantity = product.getQuantity();
        cartData[i][1] = quantity;
        double price = product.getPrice();
        double totalPrice = price * quantity;
        cartData[i][2] = totalPrice;
        totalCost += totalPrice;

        // Update the quantity map for the product
        String productID = product.getProductID();
        productQuantityMap.put(productID, productQuantityMap.getOrDefault(productID, 0) + quantity);
    }

    // Calculate the 20% discount for products with a quantity of 3 or more
    double discountAmount = 0.0;
    for (int i = 0; i < cartContents.size(); i++) {
        Product product = cartContents.get(i);
        String productID = product.getProductID();
        int quantity = productQuantityMap.get(productID);

        if (quantity >= 3) {
            // Apply a 20% discount to the total price of each qualifying product
            discountAmount += product.getPrice() * 0.2 * quantity;
        }
    }

    // Calculate the discounted total cost
    double discountedTotalCost = totalCost - discountAmount;

    // Format the values to two decimal places
    String formattedTotalCost = String.format("%.2f", totalCost);
    String formattedDiscountAmount = String.format("%.2f", discountAmount);
    String formattedDiscountedTotalCost = String.format("%.2f", discountedTotalCost);

    String[] cartColumnNames = {"Product ID", "Quantity", "Price"};

    DefaultTableModel cartModel = new DefaultTableModel(cartData, cartColumnNames);

    JTable cartTable = new JTable(cartModel);

    JFrame cartFrame = new JFrame("Shopping Cart");
    cartFrame.setLayout(new BorderLayout());

    JScrollPane cartScrollPane = new JScrollPane(cartTable);
    cartFrame.add(cartScrollPane, BorderLayout.CENTER);

    // Add a summary panel at the bottom
    JPanel summaryPanel = new JPanel();
    summaryPanel.setLayout(new BorderLayout());

    JTextArea totalCostTextArea = new JTextArea();
    totalCostTextArea.setEditable(false);
    totalCostTextArea.setText("                                                                                      Total Cost: $" + formattedTotalCost);

    JTextArea discountTextArea = new JTextArea();
    discountTextArea.setEditable(false);
    discountTextArea.setText("                                                                                    Discount (20%): -$" + formattedDiscountAmount);

    JTextArea discountedTotalCostTextArea = new JTextArea();
    discountedTotalCostTextArea.setEditable(false);
    discountedTotalCostTextArea.setText("                                                                                    Final Total: $" + formattedDiscountedTotalCost);

    // Add the components to the SOUTH side of the summary panel
    summaryPanel.add(totalCostTextArea, BorderLayout.NORTH);
    summaryPanel.add(discountTextArea, BorderLayout.CENTER);
    summaryPanel.add(discountedTotalCostTextArea, BorderLayout.SOUTH);

    // Add the summary panel to the SOUTH side of the main frame
    cartFrame.add(summaryPanel, BorderLayout.SOUTH);

    cartFrame.setSize(400, 400);
    cartFrame.setLocationRelativeTo(null);
    cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    cartFrame.setVisible(true);
}

}