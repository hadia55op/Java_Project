import java.sql.SQLException;

import java.util.Scanner;

public class CustomerController {


    private Integer loggedInCustomerId = null;
    private String loggedInCustomerName = null;




    public CustomerController() {
        this.loggedInCustomerId = null;
        this.loggedInCustomerName = null;
    }
    public void setLoggedInCustomer(Inlogning loggedInCustomer) {
        this.loggedInCustomerId = loggedInCustomer.getId();
        this.loggedInCustomerName = loggedInCustomer.getName();
    }
//    public void logout() {
//        this.loggedInCustomerId = null;
//        this.loggedInCustomerName = null;
//    }



    public Integer getLoggedInCustomerId() {
        return loggedInCustomerId;
    }

    public String getLoggedInCustomerName() {
        return loggedInCustomerName;
    }

    CustomerService customerService = new CustomerService();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);



        System.out.println("1. lägga  en kund efter ");
        System.out.println("2. Update customer email");
        System.out.println("0. Avsluta programmet");
        String select = scanner.nextLine();


        switch (select) {
            case "1":

                System.out.println("Ange ett namn att lägga en ny kund:");
                String name = scanner.nextLine();

                System.out.println("Ange en email:");
                String email = scanner.nextLine();

                System.out.println("Ange telefonnummer:");
                String phone = scanner.nextLine();

                System.out.println("Ange adress:");
                String address = scanner.nextLine();

                System.out.println("Ange ett lösenord:");
                String password = scanner.nextLine();

                if (isValidInput(name, email, phone, address, password)) {
                    try {
                        customerService.insertUser(name, email, phone, address, password);
                       System.out.println(" En ny kund har  skapades !");
                 } catch (SQLException e) {
                       System.out.println(" Ett fel inträffade vid skapande av en ny kund:");
                      if (e.getMessage().contains("UNIQUE")) {
                           System.out.println(" E-postadressen är redan registrerad.");

                      } else {
                          System.out.println("Det har upstod ett Tekniskt fel: " + e.getMessage());
                      }
                    }
                }
                break;
            case "2":
                try {
                    System.out.println("Ange kund ID för att uppdatera email:");
                    int customerId = Integer.parseInt(scanner.nextLine().trim());

                    System.out.println("Ange ny email:");
                    String newEmail = scanner.nextLine().trim();
                      // isValidEmail function is att  end of the file below
                    if (!isValidEmail(newEmail)) {
                        System.out.println(" Ogiltig email. Måste innehålla '@' och '.'");
                        break;
                    }
//                  updateEmail below here att the end
                    customerService.updateEmail(customerId, newEmail);
                } catch (NumberFormatException e) {
                    System.out.println("  Kunden ID måste vara ett nummer.");
                }
                break;
            case "0":
                System.out.println("Du lämnar kundmenyn.");
                return;


        }
    }
    public static boolean isValidInput(String name, String email, String phone, String address, String password) {
        boolean isValid = true;

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Namn får inte vara tomt.");
            isValid = false;
        }

        if (email == null || !email.contains("@") || !email.contains(".")) {
            System.out.println("Ogiltig e-postadress. Måste innehålla '@' och '.'");
            isValid = false;
        }

        if (phone == null || !phone.matches("\\d{7,14}")) {
            System.out.println("Telefonnummer måste bara innehålla siffror och vara mellan 7 och 15 siffror långt.");
            isValid = false;
        }

        if (address == null || address.trim().isEmpty()) {
            System.out.println(" Adress får inte vara tom.");
            isValid = false;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println(" Lösenord får inte vara tom.");
            isValid = false;
        }

        return isValid;
    }
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }


}