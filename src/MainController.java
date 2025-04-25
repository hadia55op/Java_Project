import java.util.Scanner;

import Order.OrderController;
import Order.OrderRepository;
import Product.ProductController;
import customer.CustomerController;
import customer.CustomerService;
import customer.Inlogning;


public class MainController {
    private final Scanner scanner = new Scanner(System.in);
    private final CustomerController customerController;
    private final ProductController productController;
    private final OrderController orderController;
    private final OrderRepository orderRepository;
    private final CustomerService customerService = new CustomerService();


    // constructor for Main.java
    public MainController() {
        this.customerController = new CustomerController();
        this.productController = new ProductController();
        this.orderController = new OrderController(customerController);
        this.orderRepository = new OrderRepository();
        run();
    }
    private void run() {
        try {

            while (customerController.getLoggedInCustomerId() == null) {
                System.out.println(" *** Logga in för att använda Huvudmeny  ***");
                System.out.print("Ange Email: ");
                String email = scanner.nextLine();

                System.out.print("Ange Lösenord: ");
                String password = scanner.nextLine();

                Inlogning loggedInCustomer = customerService.loginCustomer(email, password);

                if (loggedInCustomer != null) {
                    customerController.setLoggedInCustomer(loggedInCustomer);
                    System.out.println("Välkommen, " + loggedInCustomer.getName() + "!");
                } else {
                    System.out.println("Felaktig e-post eller lösenord. Försök igen.");
                }
            }


            while (true) {
                System.out.println("\n** HUVUDMENY **");
                System.out.println("1. Kundmeny");
                System.out.println("2. Produktmeny");
                System.out.println("3. Ordermeny");
                System.out.println("0. Avsluta programmet");

                System.out.println("Inloggad som: " + customerController.getLoggedInCustomerName());

                System.out.print("Ditt val: ");
                String select = scanner.nextLine();

                switch (select) {
                    case "1":
                        customerController.runMenu();
                        break;
                    case "2":
                        productController.runMenu2();
                        break;
                    case "3":
                        orderController.runMenu3();
                        break;

                    case "0":
                        System.out.println("Programmet avslutas!");
                        return;
                    default:
                        System.out.println("Ogiltigt val. Försök igen.");
                }
            }
        } catch (Exception e) {
            System.out.println("Fel i systemet: " + e.getMessage());
            e.printStackTrace();
        }
    }
}