import javax.swing.*;
import java.awt.*;

public class ShoppingCartFrame extends JFrame {

    public ShoppingCartFrame(User user) {
        ShoppingCart cart = user.getCart();
        ShoppingCartTableModel tableModel = new ShoppingCartTableModel(cart.getProducts());
        JPanel topPanel = new JPanel();
        JTable table = new JTable(tableModel);
        table.setRowHeight(50);

        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane);
        table.setPreferredScrollableViewportSize(new Dimension(400, 200));

        JPanel lowerPanel = new JPanel(new GridLayout(4,2));
        lowerPanel.add(new JLabel("Total"));
        lowerPanel.add(new JLabel(String.valueOf(cart.getTotal())));

        lowerPanel.add(new JLabel("First Purchase Discount (10%)"));
        lowerPanel.add(new JLabel(String.valueOf(cart.getFirstPurchaseDiscount(user))));

        lowerPanel.add(new JLabel("Three items in same Category Discount (20%)"));
        lowerPanel.add(new JLabel(String.valueOf(cart.getThreeItemDiscount())));

        lowerPanel.add(new JLabel("Final Total"));
        lowerPanel.add(new JLabel(String.valueOf(cart.getFinalTotal(user))));

        this.setTitle("Shopping Cart");
        this.setSize(600, 400);
        this.setVisible(true);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("trolley.png")));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(lowerPanel, BorderLayout.CENTER);
    }
}
