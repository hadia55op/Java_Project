
package review;

import Order.OrderRepository;
import customer.CustomerController;

import java.sql.SQLException;

public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final CustomerController customerController;
    private final OrderRepository orderRepository;

    //  accepts both reviewRepository and customerController and orderRepository
    public ReviewController(ReviewRepository reviewRepository, CustomerController customerController, OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.customerController = customerController;
        this.orderRepository = orderRepository;
    }

    public void leaveReview(int productId, int rating, String comment) {
        Integer customerId = customerController.getLoggedInCustomerId();

        if (customerId == null) {
            System.out.println("Du måste vara inloggad för att lämna en recension .");
            return;
        }

        if (rating < 1 || rating > 5) {
            System.out.println("Betyget måste vara mellan 1 och 5.");
            return;
        }

        if (!orderRepository.ifProductsBought(customerId, productId)) {
            System.out.println("Du kan endast recensera produkter du har köpt(gå til kundvagn).");
            return;
        }

        Review review = new Review(productId, customerId, rating, comment);
        try {
            reviewRepository.saveReview(review);
            System.out.println("Tack för din recension, " + customerController.getLoggedInCustomerName() + "!");
        } catch (SQLException e) {
            System.out.println("Fel vid skapande av recension: " + e.getMessage());
        }
    }

//********************************************************************************************
    public void showAverageRating(int productId) {
        try {
            double avg = reviewRepository.getAverageRating(productId);
            if (avg == 0.0) {
                System.out.println("Inga recensioner ännu för denna produkt.");
            } else {
                System.out.printf("Genomsnittligt betyg: %.1f ★\n", avg);
            }
        } catch (SQLException e) {
            System.out.println("Kunde inte hämta betyget: " + e.getMessage());
        }
    }

}
