public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId, String productName, int availableQty, double price, String brand, int warrantyPeriod) {
        super(productId, productName, availableQty, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return this.brand;
    }

    public int getWarrantyPeriod() {
        return this.warrantyPeriod;
    }


    @Override
    public String toString() {
        return "\nProduct Type: Electronic" +
                super.toString() +
                "\nBrand: " + this.brand +
                "\nWarranty Period: " + this.warrantyPeriod;
    }
}