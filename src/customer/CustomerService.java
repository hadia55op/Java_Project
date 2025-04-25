package customer;

import java.sql.SQLException;

public class CustomerService {

    CustomerRepository customerRepository = new CustomerRepository();

public Inlogning loginCustomer(String email, String password) throws SQLException {
    return customerRepository.login(email, password);
}

    public void insertUser(String name,String email,String phone, String address,String password)throws SQLException{
        System.out.println("service lagar skicker videra name,email, phone,address and password");
        System.out.println(("ny kunden har laggts till."));
        customerRepository.insertCustomer(name,email,phone,address,password);
    }
    public boolean updateEmail(int customer_id, String email) throws SQLException{
        System.out.println("server skicker vidare update");
        return customerRepository.updateCustomer(customer_id, email);
    }

}