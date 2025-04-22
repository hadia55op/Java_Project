package Order;
//import java.security.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderController {

OrderRepository orderRepository = new OrderRepository();
    OrderService orderService = new OrderService();

    public void runMenu3() throws SQLException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Se order historik för en kund");
        System.out.println("2.Lägga ny en order");
        System.out.println("3.delete en order");
        System.out.println("4.läg till en order med flera produkter");
        System.out.println("5.delete order med fler productor");
        System.out.println("0. Avsluta");

        String select = scanner.nextLine();
        switch (select) {
            case "1":
                System.out.println("Nu hämtar vi orderhistorik för en kund ");
                System.out.print("Ange kundens ID: ");
                String customerIdStr = scanner.nextLine();

                try {
                    int customerId = Integer.parseInt(customerIdStr);
                    ArrayList<Order> orders = orderService.getOrderHistory(customerId);

                    if (orders.isEmpty()) {
                        System.out.println("Ingen orderhistorik hittades för kunden.");
                    } else {
                        for (Order order : orders) {
                            System.out.println("---------------------------");
                            System.out.println(order);// calls toStrig method
                        }

                    }

                } catch (NumberFormatException e) {
                    System.out.println("Felaktigt kund-ID format.");
                } catch (SQLException e) {
                    System.out.println("Fel vid hämtning av data: " + e.getMessage());
                }
                break;
            case "2":
                System.out.print("Ange kund-ID: ");
                int customerId = scanner.nextInt();
                try {
                    orderService.insertNewOrder(customerId);
                } catch (SQLException e) {
                    System.out.println("Fel vid skapande av order: " + e.getMessage());
                }
                break;
            case "3":

                System.out.print("Ange order-ID att ta bort: ");
                int deleteId = scanner.nextInt();
                try {
                    orderService.deleteOrder(deleteId);
                } catch (SQLException e) {
                    System.out.println("Fel vid borttagning av order: " + e.getMessage());
                }
                break;

            case "4":
                try {
                    System.out.print("Ange Customer ID: ");
                    int customer_id = Integer.parseInt(scanner.nextLine());

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
                        System.out.println("Ingen giltig produkt lades till i ordern. Ordern har inte skapats.");
                        break;
                    }

                    int orderId = orderService.insertNewProductOrder(customer_id);
                    System.out.println("Ny order skapad med ID: " + orderId);

                    orderService.insertOrderProducts(orderId, products);

                    double total = 0;
                    for (Order o : products) {
                        total += o.getQuantity() * o.getUnitPrice();
                    }

                    System.out.printf("Totalt pris för ordern: %.2f kr\n", total);

                } catch (SQLException e) {
                    System.out.println("Fel vid databasoperation: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("Fel: Ogiltig siffra inmatad. Vänligen försök igen.");
                }
                break;

            case "5":

                System.out.print("Ange order-ID att ta bort från orders_products: ");
                int deleteID = scanner.nextInt();
                try {
                    orderService.deleteOrderWithProducts(deleteID);
                } catch (SQLException e) {
                    System.out.println("Fel vid borttagning av order: " + e.getMessage());
                }
                break;
        }
    }
}



// måste göra inser i orders för att vi behöver använda order_id när vi gör inlagg i orders_products
// behövs customer_id for insert i orders
// när en insert har gjörts hämta order_id från nyskapade orderen
// med vår order_id - gör insert i orders_products
//scn fofr p_id
// scn för quantity(här vi måste validera på psq,så den är alltid storre än quantity)
// hämts prod baserat på product_id från scanner - ta price and sätt på unit_price i products_categories
// Loops detta steg tills kunden är klar med beställningen
// efter alla produkter lagts till update product stock_quantity
// stock_quantity minus quantity
//
//
// logga in: authenticate, email