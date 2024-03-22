import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class WestminsterShoppingFrame extends JFrame {
    private User user;
    private ArrayList<Product> productList;
    private ArrayList<User> userList;
    private ProductTableModel tableModel;
    private JTable table;
    private JLabel lblProductDetails, lblProductId, lblCategory, lblName, lblSizeOrBrand, lblColourOrWarranty,lblItemsAvailable;
    private JComboBox<String> dropDown;
    private String lastSelectedCategory = "";
    private ShoppingCartFrame shoppingCartFrame;
    public WestminsterShoppingFrame(ArrayList<Product> productList, ArrayList<User> userList, User user) {
        this.productList = productList;
        this.userList = userList;
        this.user = user;

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Select Product Category");
        String[] comboList = {"All", "Electronics", "Clothing"};
        dropDown = new JComboBox<>(comboList);
        dropDown.addActionListener(new ComboSelectionListener());

        JButton btnShoppingCart = new JButton("Shopping Cart");
        btnShoppingCart.addActionListener(new ShoppingCartBtnListener());

        tableModel = new ProductTableModel(productList);
        table = new JTable(tableModel);
        table.setRowHeight(20); //
        table.setPreferredScrollableViewportSize(new Dimension(800, 350)); //
        RowSelectionListener handler = new RowSelectionListener();
        table.getSelectionModel().addListSelectionListener(handler);
        table.setDefaultRenderer(Object.class, new CellRenderer());

        JScrollPane scrollPane = new JScrollPane(table);

        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(dropDown, BorderLayout.CENTER);
        topPanel.add(btnShoppingCart, BorderLayout.EAST);
        topPanel.add(scrollPane, BorderLayout.SOUTH);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new GridLayout(7, 1));

        JButton btnAddToCart = new JButton("Add to Shopping Cart");
        btnAddToCart.addActionListener(new addToCartBtnListener());

        lblProductDetails = new JLabel();
        lblProductId = new JLabel();
        lblCategory = new JLabel();
        lblName = new JLabel();
        lblItemsAvailable = new JLabel();
        lblSizeOrBrand = new JLabel();
        lblColourOrWarranty = new JLabel();

        lowerPanel.add(lblProductDetails);
        lowerPanel.add(lblProductId);
        lowerPanel.add(lblCategory);
        lowerPanel.add(lblName);
        lowerPanel.add(lblSizeOrBrand);
        lowerPanel.add(lblColourOrWarranty);
        lowerPanel.add(lblItemsAvailable);

        JPanel newPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        newPanel.add(btnAddToCart);

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("store.png")));
        this.setTitle("Westminster Shopping Centre");
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(lowerPanel, BorderLayout.CENTER);
        this.getContentPane().add(newPanel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void saveUpdatedDetails() {
        try (FileOutputStream fileOut = new FileOutputStream("products.ser");
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {

            objOut.writeObject(productList);

        } catch (IOException e) {
            System.out.println("Error in Product Saving Data! ");
        }

        try (FileOutputStream fileOut = new FileOutputStream("users.ser");
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {

            objOut.writeObject(userList);

        } catch (IOException e) {
            System.out.println("Error in Saving User Data! ");
        }
    }

    /**
     * For displaying the details of a product when it is selected
     */
    private class RowSelectionListener implements ListSelectionListener {       //Have a look at this

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {           //Study this if condition (error happens if not used)
                    String productId = (String) table.getValueAt(selectedRow, 0);

                    Product selectedProduct = null;
                    for (Product product : productList) {
                        if (product.getProductId().equals(productId))
                            selectedProduct = product;
                    }

                    assert selectedProduct != null;

                    lblProductDetails.setText("Selected Product - Details");
                    lblProductDetails.setFont(new Font("Dialog", Font.BOLD, 14));
                    lblProductId.setText("Product ID: " + selectedProduct.getProductId());
                    lblCategory.setText("Category: " + ((selectedProduct instanceof Electronics) ? "Electronics" : "Clothing"));
                    lblName.setText("Product Name: " + selectedProduct.getProductName());

                    if ((selectedProduct instanceof Electronics)) {
                        lblSizeOrBrand.setText("Brand: " + ((Electronics) selectedProduct).getBrand());
                        lblColourOrWarranty.setText("Warranty Period: " + ((Electronics) selectedProduct).getWarrantyPeriod());

                    } else {
                        lblSizeOrBrand.setText("Size: " + ((Clothing) selectedProduct).getSize());
                        lblColourOrWarranty.setText("Colour: " + ((Clothing) selectedProduct).getColour());
                    }

                    lblItemsAvailable.setText("Items Available: " + selectedProduct.getAvailableQty());

                } else {
                    lblProductDetails.setText("");
                    lblProductId.setText("");
                    lblCategory.setText("");
                    lblName.setText("");
                    lblItemsAvailable.setText("");
                    lblSizeOrBrand.setText("");
                    lblColourOrWarranty.setText("");
                }
            }
        }
    }

    /**
     * For displaying the relevant products based on the dropdown menu selection
     */
    private class ComboSelectionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String category = (String) dropDown.getSelectedItem();

            if (!lastSelectedCategory.equals(category)) {
                ArrayList<Product> newProductsList = new ArrayList<>();

                if (category.equals("Electronics"))
                    updateNewProductList(newProductsList, Electronics.class);

                else if (category.equals("Clothing"))
                    updateNewProductList(newProductsList, Clothing.class);

                else if (category.equals("All"))
                    newProductsList = productList;

                table.clearSelection();
                tableModel.setProductList(newProductsList);
                lastSelectedCategory = category;
            }
        }

        private void updateNewProductList(ArrayList<Product> newProductList, Class<?> categoryClass) {
            for (Product product : productList) {
                if (categoryClass.isInstance(product))
                    newProductList.add(product);
            }
        }
    }

    /**
     * Opening the shopping cart GUI when the 'Shopping Cart' button is pressed
     */
    private class ShoppingCartBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            shoppingCartFrame= new ShoppingCartFrame(user);
            shoppingCartFrame.addKeyListener(new EscKeyListener());
            shoppingCartFrame.setFocusable(true);
        }
    }

    /**
     * Add selected product to the cart when 'Add to Cart' Button is selected
     */
    private class addToCartBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {    // Logic similar to valueChanged in RowSelectionListener. Look into this
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
                String productId = (String) table.getValueAt(selectedRow, 0);

                Product selectedProduct = null;
                for (Product product : productList) {
                    if (product.getProductId().equals(productId))
                        selectedProduct = product;
                }

                if (selectedProduct != null && selectedProduct.getAvailableQty() >= 1) {
                    user.getCart().addProduct(selectedProduct);
                    JOptionPane.showMessageDialog(WestminsterShoppingFrame.this, "Added to cart");

                    int previousQuantity = selectedProduct.getAvailableQty();
                    selectedProduct.setAvailableQty(previousQuantity - 1);
                    lblItemsAvailable.setText("Items Available: " + selectedProduct.getAvailableQty());
                    saveUpdatedDetails();
                } else {
                    JOptionPane.showMessageDialog(WestminsterShoppingFrame.this, "Cannot add to Cart. Product not Available!");
                }
            }
        }
    }

    /**
     * Displaying the products with three or less available quantity in red colour
     */
    private class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String productId = (String) table.getValueAt(row, 0);
            Product selectedProduct = null;

            for (Product product : productList) {
                if (product.getProductId().equals(productId))
                    selectedProduct = product;
            }

            if (selectedProduct.getAvailableQty() < 3)
                renderer.setBackground(Color.RED);
            else
                renderer.setBackground(table.getBackground());

            if (isSelected) {
                renderer.setBackground(table.getSelectionBackground());
            }

            return renderer;
        }
    }

    /**
     * Allows the shopping cart GUI to be closed ob 'esc' key press
     */
    private class EscKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                shoppingCartFrame.dispose();
        }
    }
}
