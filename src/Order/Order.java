package Order;
import java.sql.Timestamp;
;

public class Order {
    private int order_id;
    private int customer_id;
    private Timestamp order_date;
    private String name;
    private int product_id;
    private int quantity;
    private double unit_price;

    public Order(int order_id, int customer_id, Timestamp order_date, String name) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.order_date = order_date;
        this.name = name;

    }


    public Order(int order_id, int customer_id, Timestamp order_date) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.order_date = order_date;
    }


    public Order(int product_id, int quantity, double unit_price) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }


    public int getProductId() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unit_price;
    }


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }


    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Timestamp getOrder_date() {
        return order_date;
    }


    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return String.format(
                "Order ID: %d\nCustomer ID: %d\nCustomer Name: %s\nOrder Date: %s\n---------------------------",
                order_id, customer_id, name, order_date.toString()
        );
    }

}