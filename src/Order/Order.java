package Order;
//import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

//    public Order(int quantity) {
//        this.quantity= quantity;
//    }

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


public String getName(){
        return name;
}

//    @Override
//    public String toString() {
//        return "Order{" +
//                "order_id=" + order_id +
//                ", customer_id=" + customer_id +
//                ", order_date=" + order_date +
//                ", name='" + name + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return String.format(
                "Order ID: %d\nCustomer ID: %d\nCustomer Name: %s\nOrder Date: %s\n---------------------------",
                order_id, customer_id, name, order_date.toString()
        );
    }

//    public static class ProductItem {
//        private int order_product_id;
//        private int prodocut_id;
//        private double unit_price;
//        private int quantity;
//
//
//        public int getProdocut_id() {
//            return prodocut_id;
//        }
//
//        public void setProdocut_id(int prodocut_id) {
//            this.prodocut_id = prodocut_id;
//        }
//
//        public int getOrder_product_id() {
//            return order_product_id;
//        }
//
//        public void setOrder_product_id(int order_product_id) {
//            this.order_product_id = order_product_id;
//        }
//
//        public double getUnit_price() {
//            return unit_price;
//        }
//
//        public void setUnit_price(double unit_price) {
//            this.unit_price = unit_price;
//        }
//
//        public int getQuantity() {
//            return quantity;
//        }
//
//        public void setQuantity(int quantity) {
//            this.quantity = quantity;
//        }
//
//        public ProductItem(int order_product_id, int prodocut_id, double unit_price, int quantity) {
//            this.order_product_id = order_product_id;
//            this.prodocut_id = prodocut_id;
//            this.unit_price = unit_price;
//            this.quantity = quantity;
//        }
//        private List<ProductItem> items = new ArrayList<>();
//        public void addItem(String productName, int quantity) {
//            items.add(new ProductItem(productName, quantity));
//        }
//    }
}