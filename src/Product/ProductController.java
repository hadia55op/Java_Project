package Product;
import java.util.InputMismatchException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {
    ProductService productService = new ProductService();
    ProductRepository productRepository = new ProductRepository();




    public void runMenu2() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Hämta alla produkter");
        System.out.println("2. Hämta en produk efter namn");
        System.out.println("3. Hämta en produk efter category_id");
        System.out.println("4. Updatera pricet på en viss  product");
        System.out.println("5. Updatera  lagar saldo av en viss product");

        System.out.println("6.lägga till ny product");
        System.out.println(("7. Delete a product "));
        String select = scanner.nextLine();
        switch (select) {
            case "1":
                System.out.println("Nu hämter vi alla produkter");
                ArrayList<Product> products = productService.getAllProducts();
                for (Product p : products) {
                    System.out.println("────────────────────────────");
                    System.out.println("ProductId: " + p.getProductId());
                    System.out.println("Namn: " + p.getName());
                    System.out.println("Price:" + p.getPrice());


                }
                break;
            case "2":
                System.out.print("Ange produktnamn: ");
                String namn = scanner.nextLine();
                Product p = productRepository.getProductByName(namn);
                if (p != null) {
                    System.out.println("ProductId: " + p.getProductId());
                    System.out.println("Namn: " + p.getName());
                    System.out.println("Price: " + p.getPrice());
                }

               break;
            case "3":
                System.out.println("Nu hämtar vi produkter baserat på kategori.");
                System.out.print("Ange kategori-ID: ");

                try {
                    int category_id = scanner.nextInt();
                    scanner.nextLine();

                    ArrayList<Product> productS = productRepository.getProductsByCategory(category_id);

                    for (Product pr : productS) {
                        System.out.println("────────────────────────────");
                        System.out.println(" Produkt-ID: " + pr.getProductId());
                        System.out.println("Namn: " + pr.getName());
                        System.out.println("Pris: " + pr.getPrice() + " kr");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("kategori-ID måste vara ett nummer.");
                    scanner.nextLine();
                }
                break;


            case "4":
                System.out.println("Uppdatera produktpris");

                try {
                    System.out.print("Ange ID på produkten som ska uppdateras: ");
                    int productId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Ange nytt pris: ");
                    double newPrice = Double.parseDouble(scanner.nextLine());

                    boolean updated = productService.updateProductPrice(productId, newPrice);

                    if (updated) {
                        System.out.println(" Produkten har uppdaterats.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println(" Ange ett giltigt numeriskt ID och pris.");
                }
                break;

            case "5":
                try {
                    System.out.print("Ange ID på produkten att updatera lagrerr saldo: ");
                    int productId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Ange nytt quantitiy: ");
                    int newQantiy = Integer.parseInt(scanner.nextLine());

                    productService.updateStock(productId, newQantiy);
                    System.out.println("Produkten har uppdaterats.");
                } catch (NumberFormatException e) {
                    System.out.println("Fel inmatning. Ange ett numeriskt ID och pris.");
                }
                break;

            case "6":
                try {
                    System.out.println("Ange namn på ny produkt:");
                    String name = scanner.nextLine();

                    System.out.println("Ange pris:");
                    double price = Double.parseDouble(scanner.nextLine());

                    System.out.println("Ange manufacturer ID:");
                    int manufacturerId = Integer.parseInt(scanner.nextLine());

                    System.out.println("Ange description:");
                    String description = scanner.nextLine();

                    System.out.println("Ange  stock quantity:");
                    int stockQuantity = Integer.parseInt(scanner.nextLine());

                    productService.insertNewProduct(name, price, manufacturerId, description, stockQuantity);

                } catch (NumberFormatException e) {
                    System.out.println(" Vänligen ange giltiga numeriska värden för pris, ID och lager.");
                }
                break;

            case "7":
                System.out.print("Ange ID på produkten som ska tas bort: ");
                int product_id = Integer.parseInt(scanner.nextLine());
                boolean deleteSucess = productRepository.deleteProduct(product_id);

        }


       }

}
