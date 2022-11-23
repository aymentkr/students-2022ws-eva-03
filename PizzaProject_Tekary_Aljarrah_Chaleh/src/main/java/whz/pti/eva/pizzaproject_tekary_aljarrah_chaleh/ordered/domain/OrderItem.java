package whz.pti.eva.pizzaproject_tekary_aljarrah_chaleh.ordered.domain;

import whz.pti.eva.pizzaproject_tekary_aljarrah_chaleh.pizza.domain.PizzaSize;

import javax.persistence.Id;

public class OrderItem {
    @Id
    private long pizzaId;
    private String name;
    private int quantity;

    public OrderItem(long pizzaId, String name, int quantity, String userId, PizzaSize pizzaSize) {
        this.pizzaId = pizzaId;
        this.name = name;
        this.quantity = quantity;
        this.userId = userId;
        this.pizzaSize = pizzaSize;
    }

    public long getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(long pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PizzaSize getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(PizzaSize pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    private String userId;
    private PizzaSize pizzaSize;
}
