import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {
    Map<Product, Integer> productMap;
    private final double FIRST_PURCHASE_DISCOUNT = 0.10;
    private final double THREE_ITEM_DISCOUNT = 0.20;

    public ShoppingCart() {
        this.productMap = new HashMap<>();
    }

    public void addProduct(Product product) {
        if (this.productMap.containsKey(product))
            this.productMap.put(product, this.productMap.get(product) + 1);
        else
            this.productMap.put(product, 1);
    }

    public Map<Product, Integer> getProducts() {
        return this.productMap;
    }

    /**
     * @return the tentative total of the products in the cart (without applying discounts)
     */
    public double getTotal() {
        double total = 0;
        for (Product product : productMap.keySet()) {
            int quantity = productMap.get(product);
            total += product.getPrice() * quantity;
        }
        return total;
    }

    /**
     * @return the discount when user buys at least three products of the same category.
     */
    public double getThreeItemDiscount() {
        int electronicCount = 0;
        int clothingCount = 0;

        for (Product product : productMap.keySet()) {
            if (product instanceof Electronics)
                electronicCount += productMap.get(product);
            else
                clothingCount += productMap.get(product);
        }

        if (electronicCount >= 3 || clothingCount >= 3)
            return this.getTotal() * THREE_ITEM_DISCOUNT;
        else
            return 0.0;
    }

    /**
     * @return the discount for a user on their very first purchase on the system
     */
    public double getFirstPurchaseDiscount(User user) {
        if (OrderTracker.getOrderCount(user) == 0)
            return this.getTotal() * FIRST_PURCHASE_DISCOUNT;
        else return 0.0;
    }

    /**
     * @return the final total of the products in the shopping cart (Applying the discounts)
     */
    public double getFinalTotal(User user) {
        return this.getTotal() - this.getFirstPurchaseDiscount(user) - this.getThreeItemDiscount();
    }
}
