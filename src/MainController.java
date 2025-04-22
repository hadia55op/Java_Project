import java.sql.SQLException;
import java.util.Scanner;

import Order.OrderController;
import Product.ProductController;
/*public class MainController {
//    private Integer loggedInCustomerId = null;
//    private String loggedInCustomerName = null;
    CustomerController customerController = new CustomerController();
    ProductController productController = new ProductController();
    //CustomerService customerService= new CustomerService();
    OrderController orderController = new OrderController();


    Scanner scanner = new Scanner(System.in);

    public MainController() {
        try {
            while (true) {
                System.out.println("Main Menu");
                System.out.println("1. Customer Menu");
                System.out.println("2. Product Menu");
                System.out.println("3. Order Menu");
                System.out.println("0. Avsluta ");
                if (customerController.getLoggedInCustomerId() != null) {
                    System.out.println(" Inloggad som: " + customerController.getLoggedInCustomerName() );
                } else {
                    System.out.println("Ingen kund är inloggad i menu.");
                }
                String select = scanner.nextLine();
                switch (select) {
                    case "1":
                        customerController.runMenu();
                        break;
                    case "2":
                        if (customerController.getLoggedInCustomerId() == null) {
                            System.out.println(" Du måste logga in i customermenyn som kund, tryck  1 för att komma åt producktmenyn.");
                        } else {
                            productController.runMenu2();
                        }
                        break;

                    case "3" :
                        if (customerController.getLoggedInCustomerId() == null) {
                            System.out.println(" Du måste logga in customermenyn som kund för att komma åt ordermenyn, tryck 1.");
                        } else {
                            orderController.runMenu3();
                        }
                        break;

                    case "0":
                        System.out.println(" nu programmit avsluter");
                        return;


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}*/

 /* public class MainController {
      private final Scanner scanner = new Scanner(System.in);
      private final CustomerController customerController;
      private final ProductController productController;
      private final OrderController orderController;
      CustomerService customerService= new CustomerService();

      public MainController(CustomerController customerController,
                            ProductController productController,
                            OrderController orderController) {
          this.customerController = customerController;
          this.productController = productController;
          this.orderController = orderController;

          run();   // Start the program here
      }

      private void run() {
          try {
              // 🔐 LOGIN before main menu
              while (customerController.getLoggedInCustomerId() == null) {
                  System.out.println("== Logga in för att använda systemet ==");
                  System.out.print("E-post: ");
                  String email = scanner.nextLine();

                  System.out.print("Lösenord: ");
                  String password = scanner.nextLine();

                  Inlogning loggedInCustomer = customerService.loginCustomer(email, password);

                  if (loggedInCustomer != null) {
                      customerController.setLoggedInCustomer(loggedInCustomer);
                      System.out.println("Välkommen, " + loggedInCustomer.getName() + "!");
                  } else {
                      System.out.println("Felaktig e-post eller lösenord. Försök igen.");
                  }
              }

              // ✅ Show main menu after login
              while (true) {
                  System.out.println("\n== HUVUDMENY ==");
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
                          System.out.println("Programmet avslutas. Hej då!");
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
*/

import java.sql.SQLException;
import java.util.Scanner;

public class MainController {
    private final Scanner scanner = new Scanner(System.in);
    private final CustomerController customerController;
    private final ProductController productController;
    private final OrderController orderController;
    private final CustomerService customerService = new CustomerService();

    // constructor for Main.java
    public MainController() {
        this.customerController = new CustomerController();
        this.productController = new ProductController();
        this.orderController = new OrderController();
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