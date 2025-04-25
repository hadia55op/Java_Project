
package review;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewService {
    private ReviewRepository reviewRepository = new ReviewRepository();

    public void leaveReview(Review review) throws SQLException {
        reviewRepository.saveReview(review);
    }
}
