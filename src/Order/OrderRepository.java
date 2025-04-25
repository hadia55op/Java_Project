package Order;
import java.sql.*;
import java.util.ArrayList;
import Product.Product;

public class OrderRepository {
    public static final String URL = "jdbc:sqlite:webbutiken.db";

    public ArrayList<Order> getOrdersByCustomerId(int customerId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT o.order_id, o.order_date,  o.customer_id, c.name   " +
                "FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "WHERE c.customer_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setInt(1, customerId);
            //ResultSet is the object that stores the results returned from the database after executing the query.
            ResultSet rs = pstmt.executeQuery();

            // checks for the fields name
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                Timestamp orderDate = rs.getTimestamp("order_date");
                int customerId1 = rs.getInt("customer_id");
                String name = rs.getString("name");

                // Add the Order object to the list
                orders.add(new Order(orderId, customerId1, orderDate, name));
            }
        }
        return orders;
    }

    //################### ########################
    public void insertOrder(int customer_id) throws SQLException {
        if (customer_id <= 0) {
            System.out.println("Ogiltigt kund-ID.");
            return;
        }

        String checkCustomerSql = "SELECT 1 FROM customers WHERE customer_id = ?";
        String insertOrderSql = "INSERT INTO orders (customer_id) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(URL)) {


            try (PreparedStatement checkStmt = conn.prepareStatement(checkCustomerSql)) {
                checkStmt.setInt(1, customer_id);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Fel: Kunden med ID " + customer_id + " finns inte.");
                        return;
                    }
                }
            }


            try (PreparedStatement insertStmt = conn.prepareStatement(insertOrderSql)) {
                insertStmt.setInt(1, customer_id);
                insertStmt.executeUpdate();
                System.out.println("Ny order har lagts till!");
            }
        }
    }

   //####################################################################################################################3

    public void insertOrderProducts(int orderId, ArrayList<Order> products) throws SQLException {
        String sql = "INSERT INTO orders_products ( order_id,product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Order product : products) {
                pstmt.setInt(1, orderId);//hämter order_id från ny skapade order i orders tabel
                pstmt.setInt(2, product.getProductId());
                pstmt.setInt(3, product.getQuantity());
                pstmt.setDouble(4, product.getUnitPrice());
                pstmt.addBatch();
            }

            pstmt.executeBatch(); // Execute all inserts at once
            System.out.println("Produkter har lagts till i ordern!");
        }

    }
//############################################################################################################333
    public int insertNewProductOrder(int customerId) throws SQLException {
        String sql = "INSERT INTO orders (customer_id) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(URL)) {
            // Enable foreign key constraint
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, customerId);
                pstmt.executeUpdate();

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // return the generated order_id
                    } else {
                        throw new SQLException("Failed to create order, no ID obtained.");
                    }
                }
            }
        }
    }
//#########################################################################################################3
    public void deleteOrderWithProducts(int order_id) throws SQLException {
        String sql = "DELETE FROM orders_products WHERE order_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, order_id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order med ID " + order_id + " har tagits bort!");
            } else {
                System.out.println("Ingen order hittades med ID: " + order_id);
            }
        }
    }
//###############################################################################################33
    public int getStockQuantity(int productId) throws SQLException {
        String sql = "SELECT stock_quantity FROM products WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock_quantity");
                } else {
                    // if Product does not exist
                    return -1;
                }
            }
        }
    }

//##############################################################################################################33
    public void updateProductStock(int productId, int quantity) throws SQLException {
        String sql = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        }
    }


    public boolean ifProductsBought(int customerId, int productId) {
        String sql = """
        SELECT 1 FROM orders_products op
        JOIN orders o ON o.order_id = op.order_id
        WHERE o.customer_id = ? AND op.product_id = ?
        LIMIT 1;
    """;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            pstmt.setInt(2, productId);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();  // true if customer bought the product

        } catch (SQLException e) {
            System.out.println("Fel vid kontroll av tidigare köp: " + e.getMessage());
            return false;
        }
    }
    public ArrayList<Product> getProductsForOrder(int orderId) {
        ArrayList<Product> products = new ArrayList<>();
        String sql = """
        SELECT p.* FROM products p
        JOIN orders_products op ON p.product_id = op.product_id
        WHERE op.order_id = ?
    """;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("product_id"),
                        rs.getInt("manufacturer_id"),
                        rs.getString("description"),
                        rs.getInt("stock_quantity")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av produkter till order: " + e.getMessage());
        }

        return products;
    }



}