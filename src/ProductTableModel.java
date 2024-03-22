import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ProductTableModel extends AbstractTableModel {
    private String[] columnNames = {"Product ID", "Name", "Category", "Price(£)", "Info"};
    private ArrayList<Product> productList;

    public ProductTableModel(ArrayList<Product> productList) {
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
        Product product = productList.get(rowIndex);
        Object temp = null;

        switch(columnIndex) {
            case 0 -> temp = product.getProductId();
            case 1 -> temp = product.getProductName();
            case 2 -> temp = product instanceof Electronics ? "Electronic" : "Clothing";
            case 3 -> temp = product.getPrice();
            case 4 -> {
                if (product instanceof Electronics)
                    temp = ((Electronics) product).getBrand() + ", " + ((Electronics) product).getWarrantyPeriod() + " weeks warranty";
                else
                    temp = ((Clothing) product).getSize() + ", " + ((Clothing) product).getColour();
            }
        }
        return temp;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public void setProductList(ArrayList<Product> newProductList) {
        this.productList = newProductList;
        this.fireTableDataChanged();
    }
}
