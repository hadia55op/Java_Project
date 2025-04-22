package Product;

public class Product {
    String name;
    double price;
    int product_id;
    int manufacturer_id;
    String description;
    int stock_quantity;


    public Product(String name, double price, int product_id, int manufacturer_id, String description, int stock_quantity) {
        this.name = name;
        this.price = price;
        this.product_id = product_id;
        this.manufacturer_id = manufacturer_id;
        this.description = description;
        this.stock_quantity = stock_quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public int getProductId() {
        return product_id;
    }
}
