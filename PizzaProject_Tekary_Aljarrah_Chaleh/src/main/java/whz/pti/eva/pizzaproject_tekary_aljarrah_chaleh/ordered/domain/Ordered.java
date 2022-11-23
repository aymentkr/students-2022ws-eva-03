package whz.pti.eva.pizzaproject_tekary_aljarrah_chaleh.ordered.domain;

import javax.persistence.Id;
import java.util.List;

public class Ordered {
    @Id
    private String userId;
    private int numberOfItems;
    private List<OrderItem> orderItems;

    public Ordered(int numberOfItems, String userId, List<OrderItem> orderItems) {
        this.numberOfItems = numberOfItems;
        this.userId = userId;
        this.orderItems = orderItems;
    }
    public Ordered(){}

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
