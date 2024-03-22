import java.io.Serializable;
import java.util.Objects;

public abstract class Product implements Comparable<Product>, Serializable {
    private String productId;
    private String productName;
    private int availableQty;
    private double price;

    public Product(String productId, String productName, int availableQty, double price) {
        this.productId = productId;
        this.productName = productName;
        this.availableQty = availableQty;
        this.price = price;
    }

    public String getProductId() {
        return this.productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public int getAvailableQty() {
        return this.availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "\nProduct ID: " + this.productId +
                "\nProduct Name: " + this.productName +
                "\nAvailable Quantity: " + this.availableQty +
                "\nPrice: " + this.price;
    }

    @Override
    public int compareTo(Product otherProduct) {
        return this.productId.compareTo(otherProduct.getProductId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Product otherProduct = (Product) obj;
        return this.productId.equals(otherProduct.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
