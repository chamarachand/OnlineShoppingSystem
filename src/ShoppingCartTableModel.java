import javax.swing.table.AbstractTableModel;
import java.util.Map;

public class ShoppingCartTableModel extends AbstractTableModel {
    private String[] columnNames = {"Product", "Quantity", "Price"};
    private Map<Product, Integer> productList;

    public ShoppingCartTableModel(Map<Product, Integer> productList) {
        this.productList = productList;
    }

    @Override
    public int getRowCount() {
        return productList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product[] keys = productList.keySet().toArray(new Product[0]);
        Product product = keys[rowIndex];
        Object temp = null;

        switch (columnIndex) {
            case 0 -> temp = "<html>" +
                    product.getProductId() + "<br/>" +
                    product.getProductName() + "<br/>" +
                    ((product instanceof Electronics) ?
                            (((Electronics) product).getBrand() + ", " + ((Electronics) product).getWarrantyPeriod()) :
                            (((Clothing) product).getSize() + ", " + ((Clothing) product).getColour())) +
                    "</html>";
            case 1 -> temp = productList.get(product);
            case 2 -> temp = product.getPrice();
        }
        return temp;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
