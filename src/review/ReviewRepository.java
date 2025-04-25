package review;


import java.sql.*;
import java.util.ArrayList;

public class ReviewRepository {
    private static final String URL = "jdbc:sqlite:webbutiken.db";

    public void saveReview(Review review) throws SQLException {
        String sql = "INSERT INTO reviews (product_id, customer_id, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, review.getReview_id());
            pstmt.setInt(1, review.getProduct_id());
            pstmt.setInt(2, review.getCustomer_id());
            pstmt.setInt(3, review.getRating());
            pstmt.setString(4, review.getComment());

            pstmt.executeUpdate();
        }
    }
//************************************************************************************************
    public double getAverageRating(int productId) throws SQLException {
        String sql = "SELECT AVG(rating) AS avg FROM reviews WHERE product_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? rs.getDouble("avg") : 0;// a default value If no reviews exist for the given product_id
        }
    }

}
