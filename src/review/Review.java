package review;

public class Review {
    private int review_id;
    private int product_id;
    private int customer_id;
    private int rating;
    private String comment;

    public Review(int review_id, int product_id, int customer_id, int rating, String comment) {
        this.review_id = review_id;
        this.product_id = product_id;
        this.customer_id = customer_id;
        this.rating = rating;
        this.comment = comment;
    }
    // Constructor used for saving new reviews (no reviewId yet)


    public Review(int product_id, int customer_id, int rating, String comment) {
        this.product_id = product_id;
        this.customer_id = customer_id;
        this.rating = rating;
        this.comment = comment;
    }

    public int getReview_id() {
        return review_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
