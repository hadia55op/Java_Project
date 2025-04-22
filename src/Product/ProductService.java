package Product;
import java.util.ArrayList;
import java.sql.SQLException;

public class ProductService {
    ProductRepository productRepository= new ProductRepository();
    public ArrayList<Product> getAllProducts()throws SQLException{
        return productRepository.getAll();
   }
    public boolean updateProductPrice(int product_id, double price) {
        System.out.println("server skicker vidare update");
        return productRepository.updateProduct(product_id, price);
    }
    public boolean updateStock(int product_id, int quantity) throws SQLException{
        System.out.println("server skicker vidare update");
        return productRepository.updateStockQuantity(product_id, quantity);
    }
    public void insertNewProduct(String name, double price,  int manufacturer_id, String description, int stock_quantity)throws SQLException{
        System.out.println("service lagar skicker videra name,price,product_id,manfacturer_id,description, stock quantity");
        productRepository.insertProduct(name, price, manufacturer_id, description, stock_quantity);
    }
    public boolean deleteProduct(int id) throws SQLException{
        System.out.println("server skicker vidare id");
        return productRepository.deleteProduct(id);
    }

}
