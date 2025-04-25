package Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import customer.CustomerController;
import Product.ProductService;
import Product.Product;
import review.ReviewController;
import review.ReviewRepository;
//import customer.Inlogning;
//import java.util.Scanner;
import kundvagn.Kundvagn;


public class OrderController {
    private OrderService orderService;
    private CustomerController customerController;
    private OrderRepository orderRepository;
    private ProductService productService;
    private final Scanner scanner = new Scanner(System.in);
    private ArrayList<Kundvagn> kundvagn;
    private final ReviewController reviewController;

    public OrderController(CustomerController customerController) {
        this.customerController = customerController;
        this.orderService = new OrderService();
        this.orderRepository = new OrderRepository();
        this.productService = new ProductService();
        this.kundvagn = customerController.getkundvagnProduct();
        // Set up in this constructor for review system
        ReviewRepository reviewRepository = new ReviewRepository();
        OrderRepository orderRepository = new OrderRepository();
        this.reviewController = new ReviewController(reviewRepository, customerController, orderRepository);

    }




    public void runMenu3() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n***** ORDERMENY *****");
            System.out.println("1. Se orderhistorik");
            System.out.println("2. Lägg ny order");
            System.out.println("3. Lägg till order med flera produkter");
            System.out.println("4. Gå till kundvagn");
            System.out.println("5. Lämna recension på köpt produkt");
            System.out.println("6. Visa genomsnittligt betyg för en produkt\");");
            System.out.println("0. Avsluta");

            String select = scanner.nextLine();

            switch (select) {
                case "1" -> showOrderHistory();
                case "2" -> insertOrder();
                case "3" -> insertMultiProductOrder();                                           //methods and kundvagn menu below in the file
                case "4" -> openKundvagnMenu();
                case "5" -> {
                    System.out.print("Ange produkt-ID som du vill recensera: ");
                    int productId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Betyg (1-5): ");
                    int rating = Integer.parseInt(scanner.nextLine());

                    System.out.print("Kommentar: ");
                    String comment = scanner.nextLine();

                    reviewController.leaveReview(productId, rating, comment);
                }
                case "6" -> {
                    System.out.print("Ange produkt-ID för att se genomsnittligt betyg: ");
                    int productId = Integer.parseInt(scanner.nextLine());

                    reviewController.showAverageRating(productId);
                }
                case "0" -> {
                    System.out.println("Avslutar ordermenyn...");
                    return;
                }
                default -> System.out.println("Ogiltigt val. Försök igen.");

            }

        }
    }
//**********************************************************************************'
    public void runKundvagnMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n** KUNDVAGN **");
            System.out.println("1. Lägg till produkt i kundvagn");
            System.out.println("2. Ta bort produkt");
            System.out.println("3. Ändra antal");
            System.out.println("4. Visa kundvagn");
            System.out.println("5. Lägg order från kundvagn");
            System.out.println("0. Tillbaka");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> addProductToKundvagn();
                case "2" -> removeProductFromKundvagn();
                case "3" -> updateKundvagnQuantity();
                case "4" -> showKundvagn();
                case "5" -> {
                    try {
                        convertKundvagnToOrder();
                    } catch (SQLException e) {
                        System.out.println("Fel: " + e.getMessage());
                    }
                }
                case "0" -> {
                    return;
                }
                default -> System.out.println("Ogiltigt val.");
            }
        }
    }

    //******************************************************************************************
    private void showOrderHistory() {
        try {
            Integer customerId = customerController.getLoggedInCustomerId();
            if (customerId == null) {
                System.out.println("Du måste vara inloggad för att se orderhistorik.");
                return;
            }

            ArrayList<Order> orders = orderService.getOrderHistory(customerId);
            if (orders.isEmpty()) {
                System.out.println("Du har inga tidigare ordrar.");
            } else {
                for (Order order : orders) {
                    System.out.println("---------------------------");
                    System.out.println(order);
                }
            }
        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av orderhistorik: " + e.getMessage());
        }
    }
//***************************************************************************************
    private void insertOrder() {
        Integer customerId = customerController.getLoggedInCustomerId();
        if (customerId == null) {
            System.out.println("Du måste vara inloggad för att lägga en order.");
            return;
        }

        try {
            orderService.insertNewOrder(customerId);
            System.out.println("Order skapad!");
        } catch (SQLException e) {
            System.out.println("Fel vid skapande av order: " + e.getMessage());
        }
    }
//****************************************************************************************

    private void insertMultiProductOrder() {
        Scanner scanner = new Scanner(System.in);
        try {
            Integer customer_id = customerController.getLoggedInCustomerId();
            ArrayList<Order> products = new ArrayList<>();

            System.out.print("Hur många produkter vill du lägga till i ordern? ");
            int count = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < count; i++) {
                System.out.println("Produkt " + (i + 1) + ":");

                System.out.print("Produkt ID: ");
                int productId = Integer.parseInt(scanner.nextLine());

                System.out.print("Antal: ");
                int quantity = Integer.parseInt(scanner.nextLine());

                int stockQuantity = orderRepository.getStockQuantity(productId);

                if (stockQuantity == -1) {
                    System.out.println("Fel: Produkt med ID " + productId + " finns inte.");
                    continue;
                }

                if (quantity > stockQuantity) {
                    System.out.println("Fel: Beställd mängd (" + quantity + ") överstiger lagersaldo (" + stockQuantity + ").");
                    continue;
                }

                System.out.print("Pris per enhet: ");
                double unitPrice = Double.parseDouble(scanner.nextLine());

                products.add(new Order(productId, quantity, unitPrice));
                orderService.updateProductStock(productId, quantity);
            }

            if (products.isEmpty()) {
                System.out.println("Ingen giltig produkt lades till. Ordern skapades inte.");
                return;
            }

            int orderId = orderService.insertNewProductOrder(customer_id);
            orderService.insertOrderProducts(orderId, products);

            double total = 0;
            for (Order o : products) {
                total += o.getQuantity() * o.getUnitPrice();
            }

            System.out.printf("Totalt pris för ordern: %.2f kr\n", total);

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Fel: " + e.getMessage());
        }
    }


    private void openKundvagnMenu() {
        if (customerController.getLoggedInCustomerId() != null) {
            runKundvagnMenu();
        } else {
            System.out.println("Du måste vara inloggad för att hantera kundvagnen.");
        }
    }

    //########################################
    public void addProductToKundvagn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ange produkt-ID: ");
        int productId = Integer.parseInt(scanner.nextLine());

        Product product = null;
        try {
            ArrayList<Product> products = productService.getAllProducts();
            for (Product p : products) {
                if (p.getProductId() == productId) {
                    product = p;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av produkts information: " + e.getMessage());
            return;
        }
        if (product == null) {
            System.out.println("Den har Produkt finns inte i lagar.");
            return;
        }
        System.out.println("Produkt som hittades: " + product.getName() + ", Pris: " + product.getPrice() + " kr, I lager: " + product.getStock_quantity());
        System.out.print("Ange antal att lägga till i kundvagnen: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        if (quantity > product.getStock_quantity()) {
            System.out.println(" Det finns bara " + product.getStock_quantity() + " i lager.");
            return;
        }

        Kundvagn valdaProduckt = new Kundvagn(
                product.getName(),
                product.getPrice(),
                product.getProductId(),
                quantity
        );
        // we add in arraylist of kundvagn products
        customerController.getkundvagnProduct().add(valdaProduckt);
        System.out.println("Nu produkten har lagts till i kundvagnen.");
    }

//*********************************************************************************************
    public void removeProductFromKundvagn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ange produkt-ID att ta bort från kundvagnen: ");
        int productId = Integer.parseInt(scanner.nextLine());
       // ArrayList<Kundvagn> kundvagn = customerController.getkundvagnProduct();


        boolean removed = kundvagn.removeIf(p -> p.getProductId() == productId);

        if (removed) {
            System.out.println("Produkten togs bort från kundvagnen.");
        } else {
            System.out.println("Produkten med ID " + productId + " hittades inte i kundvagnen.");
        }
    }

//*****************************************************************************************
    public void updateKundvagnQuantity() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ange produkt-ID (att ändra antal): ");
        int productId = Integer.parseInt(scanner.nextLine());


        Product product = new ProductService().getProductById(productId);
        ;
        if (product == null) {
            System.out.println("Produkten finns inte i lagar.");
            return;
        }


        for (Kundvagn item : customerController.getkundvagnProduct()) {
            if (item.getProductId() == productId) {
                System.out.print("Ange nytt antal: ");
                int newQuantity = Integer.parseInt(scanner.nextLine());
                item.setQuantity(newQuantity);
                System.out.println("Antalet har uppdaterats.");
                return;
            }
        }


    }
//**********************************************************************************

    public void showKundvagn() {
        if (kundvagn.isEmpty()) {
            System.out.println("Kundvagnen är tom.");
        } else {
            double total = 0;
            for (Kundvagn item : kundvagn) {
                System.out.println(item);
                total += item.getTotalPrice();
            }
            System.out.println("Totalt pris: " + total + " kr");
        }
    }
    //*************************************************************************
    public void convertKundvagnToOrder() throws SQLException {
        if (kundvagn.isEmpty()) {
            System.out.println("Kundvagnen är tom. Kan inte skapa en order.");
            return;
        }

        Integer customerId = customerController.getLoggedInCustomerId();
        if (customerId == null) {
            System.out.println("Ingen kund är inloggad.");
            return;
        }

        ArrayList<Order> orderItems = new ArrayList<>();
        for (Kundvagn item : kundvagn) {
            Product product = productService.getProductById(item.getProductId());
            if (product == null) {
                System.out.println("Produkten med ID " + item.getProductId() + " finns inte längre.");
                continue;
            }

            if (item.getQuantity() > product.getStock_quantity()) {
                System.out.println("Produkten '" + product.getName() + "' har endast " + product.getStock_quantity() + " i lager. Hoppar över.");
                continue;
            }
            orderItems.add(new Order(product.getProductId(), item.getQuantity(), product.getPrice()));
            int newStock = product.getStock_quantity() - item.getQuantity();
            productService.updateStock(product.getProductId(), newStock);
        }

        if (orderItems.isEmpty()) {
            System.out.println("Inga giltiga produkter kunde beställas.");
            return;
        }

        int orderId = orderService.insertNewProductOrder(customerId);
        orderService.insertOrderProducts(orderId, orderItems);

        double total = 0;
        for (Order o : orderItems) {
            total += o.getQuantity() * o.getUnitPrice();
        }


        kundvagn.clear();
        String customerName = customerController.getLoggedInCustomerName();

        System.out.printf("Order %d skapades. Totalt pris: %.2f kr\n", orderId, total);
        System.out.println("Tack för din beställning, " + customerName + "!");
    }



}
