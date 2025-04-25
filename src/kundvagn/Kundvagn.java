package kundvagn;

public class Kundvagn {
    String productName;
    double price;
    int productId;
    int quantity;

    public Kundvagn(String productName, double price, int productId, int quantity) {
        this.productName = productName;
        this.price = price;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "Kundvagn{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
