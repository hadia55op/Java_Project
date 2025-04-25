package Product;// this VehicleProduct class we can later on use in future to our databese by makeing column for model
               // then we can add products by product type to our database
//  using Product product = new VehicleProducts("Car", 50000, productId, 101, "A fast car", 10, "Model volvo");
// // Using a Product reference to refer to the VehicleProduct object (polymorphism)
//        Product product = vehicleProduct;

public class VehicleProducts extends Product {
    private String model;

    // Constructor for VehicleProducts
    public VehicleProducts(String name, double price, int product_id, int manufacturer_id, String description, int stock_quantity, String model) {
        super(name, price, product_id, manufacturer_id, description, stock_quantity); // Calling the Product constructor
        this.model = model;
    }


    public String getModel() {
        return model;
    }
    @Override
    public String toString() {
        return super.toString() + ", model=" + model;
    }
}
