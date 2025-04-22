

import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {

    public static final String URL = "jdbc:sqlite:webbutiken.db";

   public Inlogning login(String email, String password) throws SQLException {
       String sql = "SELECT customer_id, name FROM customers WHERE email = ? AND password = ?";

       try (Connection conn = DriverManager.getConnection(CustomerRepository.URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

           pstmt.setString(1, email);
           pstmt.setString(2, password);

           try (ResultSet rs = pstmt.executeQuery()) {
               if (rs.next()) {
                   int id = rs.getInt("customer_id");
                   String name = rs.getString("name");
                   //System.out.println("Du är nu  Inlogin ! Välkommen, " + name + "!");
                   return new Inlogning(id, name);
               } else {
                   System.out.println(" Ogiltig e-post eller lösenord.");
                   return null;
               }
           }
       }
   }



    //#########
    public void insertCustomer(String name,String email, String phone,String address,String password)throws SQLException{
        String sql = "INSERT INTO customers(name,email, phone, address,password)VALUES(?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,name);
            pstmt.setString(2,email);
            pstmt.setString(3,phone);
            pstmt.setString(4,address);
            pstmt.setString(5,password);
            pstmt.execute();
        }
    }

public boolean updateCustomer(int customerId, String email) throws SQLException {
    String checkCustomerSql = "SELECT 1 FROM customers WHERE customer_id = ?";
    String updateSql = "UPDATE customers SET email = ? WHERE customer_id = ?";

    try (Connection conn = DriverManager.getConnection(URL)) {

        //  validate if the customer exists
        try (PreparedStatement checkStmt = conn.prepareStatement(checkCustomerSql)) {
            checkStmt.setInt(1, customerId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println(" Kunden med ID " + customerId + " finns inte.");
                    return false;
                }
            }
        }

        //  Perform the email update
        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            updateStmt.setString(1, email);
            updateStmt.setInt(2, customerId);
            int affectedRows = updateStmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}


}