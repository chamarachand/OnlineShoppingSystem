public class Clothing extends Product {
    private String size;
    private String colour;

    public Clothing(String productId, String productName, int availableQty, double price, String size, String colour) {
        super(productId, productName, availableQty, price);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return this.size;
    }

    public String getColour() {
        return this.colour;
    }


    @Override
    public String toString() {
        return "\nProduct Type: Clothing" +
                super.toString() +
                "\nSize: " + this.size +
                "\nColour: " + this.colour;
    }
}
