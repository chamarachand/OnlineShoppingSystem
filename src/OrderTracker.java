import java.util.HashMap;
import java.util.Map;

public class OrderTracker {
    private static Map<User, Integer> orderCountMap = new HashMap<>();

    public static int getOrderCount(User user) {
        if (!orderCountMap.containsKey(user))
            orderCountMap.put(user, 0);

        return orderCountMap.get(user);
    }

}
