package Order;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderService {
    OrderRepository orderRepository = new OrderRepository();
    public ArrayList<Order> getOrderHistory(int customerId) throws SQLException {

        return orderRepository.getOrdersByCustomerId(customerId);
    }
    public void insertNewOrder( int customer_id)throws SQLException{
        //System.out.println("service lagar skicker videra ");
        orderRepository.insertOrder(customer_id);
    }
    public void deleteOrder( int order_id)throws SQLException{
        System.out.println("service lagar skicker videra ");
        orderRepository.deleteOrder(order_id);
    }
//    public void insertNewProductOrder( int customer_id)throws SQLException{
//        System.out.println("service lagar skicker videra ");
//        orderRepository.insertOrderProducts(customer_id);
//    }


    public void insertOrderProducts(int orderId, ArrayList<Order> orders) throws SQLException {
        System.out.println("......");
        orderRepository.insertOrderProducts(orderId, orders);
    }
    public int insertNewProductOrder(int customerId) throws SQLException {
        return orderRepository.insertNewProductOrder(customerId);
    }
    public void deleteOrderWithProducts( int order_id)throws SQLException{
        System.out.println("service lagar skicker videra ");
        orderRepository. deleteOrderWithProducts(order_id);
    }
    public void updateProductStock( int product_id, int product_quantity)throws SQLException{
        System.out.println("service lagar skicker videra ");
        orderRepository.updateProductStock( product_id, product_quantity);
    }


}
