package whz.pti.eva.pizzaproject_tekary_aljarrah_chaleh.cart.domain;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;

 //@Entity
public class Cart {
    @Id
    private String userId;
    private int quantity;

    private List<Item> items=new ArrayList<>();



    public Cart(int quantity, String userId, ArrayList<Item> items) {
        this.quantity = quantity;
        this.userId = userId;
        this.items = items;
    }

    public Cart() {

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
