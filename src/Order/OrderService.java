package Order;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderService {
    OrderRepository orderRepository = new OrderRepository();
    public ArrayList<Order> getOrderHistory(int customerId) throws SQLException {

        return orderRepository.getOrdersByCustomerId(customerId);
    }
    public void insertNewOrder( int customer_id)throws SQLException{

        orderRepository.insertOrder(customer_id);
    }

    public void insertOrderProducts(int orderId, ArrayList<Order> orders) throws SQLException {
        System.out.println("......");
        orderRepository.insertOrderProducts(orderId, orders);
    }
    public int insertNewProductOrder(int customerId) throws SQLException {
        return orderRepository.insertNewProductOrder(customerId);
    }

    public void updateProductStock( int product_id, int product_quantity)throws SQLException{
        System.out.println("service lagar skicker videra ");
        orderRepository.updateProductStock( product_id, product_quantity);
    }


}
