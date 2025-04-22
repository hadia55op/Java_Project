package Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {
    public static final String URL = "jdbc:sqlite:webbutiken.db";
    public ArrayList<Product>getAll()throws SQLException{
        ArrayList<Product> products= new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                Product product = new Product(rs.getString("name"), rs.getDouble("price"),rs.getInt("product_id"), rs.getInt("manufacturer_id"), rs.getString("description"), rs.getInt("stock_quantity"));


                products.add(product);


            }
        }
            return products;
        }


public Product getProductByName(String productName) {
    if (productName == null || productName.trim().isEmpty()) {
        System.out.println("namn på produkten får inte vara tomt.");
        return null;
    }

    String sql = "SELECT * FROM products WHERE LOWER(name) = LOWER(?)";
    try (Connection conn = DriverManager.getConnection(URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, productName.trim());
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return new Product(
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("product_id"),
                    rs.getInt("manufacturer_id"),
                    rs.getString("description"),
                    rs.getInt("stock_quantity")
            );
        } else {
            System.out.println("Produkten :\"" + productName + "\" hittades inte i databasen.");
            return null;
        }

    } catch (SQLException e) {
        System.out.println(" Ett fel inträffade vid hämtning av produkten:");
        if (e.getMessage().contains("no such table")) {
            System.out.println("Tabellen 'products' finns inte i databasen.");
        } else {
            System.out.println(" Tekniskt fel: " + e.getMessage());
        }
        return null;
    }
}


    public ArrayList<Product> getProductsByCategory(int category) {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT p.* FROM products p " +
                "JOIN products_categories pc ON p.product_id = pc.product_id " +
                "WHERE pc.category_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("product_id"),
                        rs.getInt("manufacturer_id"),
                        rs.getString("description"),
                        rs.getInt("stock_quantity")
                ));
            }

            if (products.isEmpty()) {
                System.out.println("Inga produkter hittades för kategori-ID: " + category);
            }

        } catch (SQLException e) {
            System.out.println(" SQL-fel inträffade:");
            if (e.getMessage().contains("no such table i datasbasen")) {
                System.out.println("Tabellen finns inte i databasen.");
            } else if (e.getMessage().contains("datatype mismatch")) {
                System.out.println("kategori-ID för produkten måste vara ett nummer.");
            } else {
                System.out.println("Tekniskt fel: " + e.getMessage());
            }
        }

        return products;
    }

    public boolean updateProduct(int productId, double price) {
        if (price < 0) {
            System.out.println(" Priset kan inte vara negativt.");
            return false;
        }

        String sql = "UPDATE products SET price = ? WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, price);
            pstmt.setInt(2, productId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println(" Ingen produkt hittades med ID: " + productId);
                return false;
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Ett SQL-fel inträffade:");
            if (e.getMessage().contains("no such table")) {
                System.out.println(" Tabellen för produkter saknas i databasen.");
                System.out.println(" Felaktig datatyp – kontrollera att priset är ett giltigt tal.");
            }

            else {
                System.out.println(" Tekniskt fel: " + e.getMessage());
            }
            return false;
        }
    }


    public boolean updateStockQuantity(int productId, int quantity) {
        String sql = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantity);  // Corrected here
            pstmt.setInt(2, productId);  // Corrected here

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


//    public void insertProduct(String name, double price,  int manufacturer_id, String description, int stock_quantity) throws SQLException {
//            String sql = "INSERT INTO products(name, price,  manufacturer_id, description, stock_quantity) VALUES (?, ?, ?, ?, ?)";
//
//            try (Connection conn = DriverManager.getConnection(URL);
//                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//                pstmt.setString(1, name);
//                pstmt.setDouble(2, price);
//
//                pstmt.setInt(3, manufacturer_id);
//                pstmt.setString(4, description);
//                pstmt.setInt(5, stock_quantity);
//
//                pstmt.executeUpdate();
//                System.out.println("Produkt har lagts till!");
//            }
//        }
//public void insertProduct(String name, double price, int manufacturer_id, String description, int stock_quantity) {
//    if (name == null || name.trim().isEmpty()) {
//        System.out.println("Namnet på produkten får inte vara tomt.");
//        return;
//    }
//    if (price < 0) {
//        System.out.println("Priset måste vara ett positivt tal.");
//        return;
//    }
//    if (stock_quantity < 0) {
//        System.out.println(" Lagerantal kan inte vara negativt.");
//        return;
//    }
//
//    String sql = "INSERT INTO products(name, price, manufacturer_id, description, stock_quantity) VALUES (?, ?, ?, ?, ?)";
//
//    try (Connection conn = DriverManager.getConnection(URL);
//
//         PreparedStatement pstmt = conn.prepareStatement(sql)) {
//        pstmt.execute("PRAGMA foreign_keys = ON;");
//
//        pstmt.setString(1, name);
//        pstmt.setDouble(2, price);
//        pstmt.setInt(3, manufacturer_id);
//        pstmt.setString(4, description);
//        pstmt.setInt(5, stock_quantity);
//
//        pstmt.executeUpdate();
//        System.out.println(" Produkten har lagts till!");
//
//    } catch (SQLException e) {
//        System.out.println("Ett SQL-fel inträffade vid inmatning av produkt:");
//        if (e.getMessage().contains("FOREIGN KEY")) {
//            System.out.println(" Ogiltigt manufacturere-ID. manufacturer_ID finns inte.");
//        } else if (e.getMessage().contains("NOT NULL")) {
//            System.out.println(" Alla obligatoriska fält måste fyllas i.");
//        } else {
//            System.out.println("Tekniskt fel: " + e.getMessage());
//        }
//    }
//}
public void insertProduct(String name, double price, int manufacturer_id, String description, int stock_quantity) {
    if (name == null || name.trim().isEmpty()) {
        System.out.println("Namnet på produkten får inte vara tomt.");
        return;
    }
    if (price < 0) {
        System.out.println("Priset måste vara ett positivt tal.");
        return;
    }
    if (stock_quantity < 0) {
        System.out.println("Lagerantal kan inte vara negativt.");
        return;
    }

    String sql = "INSERT INTO products(name, price, manufacturer_id, description, stock_quantity) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(URL)) {
        // to give correct manufacturer_id
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }


        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, manufacturer_id);
            pstmt.setString(4, description);
            pstmt.setInt(5, stock_quantity);

            pstmt.executeUpdate();
            System.out.println("Produkten har lagts till!");
        }

    } catch (SQLException e) {
        System.out.println("Ett SQL-fel inträffade vid inmatning av produkt:");
        if (e.getMessage().contains("FOREIGN KEY")) {
            System.out.println("Ogiltigt manufacturer_ID. manufacturer_ID finns inte.");
        } else if (e.getMessage().contains("NOT NULL")) {
            System.out.println("Alla obligatoriska fält måste fyllas i.");
        } else {
            System.out.println("Tekniskt fel: " + e.getMessage());
        }
    }
}


    public boolean deleteProduct(int product_id) throws SQLException {
       String sql = "DELETE FROM products WHERE product_id= ?";
       try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setInt(1,product_id);
          return pstmt.executeUpdate() > 0;
       }

   }

    }






