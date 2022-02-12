package guru.springframework.bootstrap;

import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomerBootstrap implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public CustomerBootstrap(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Customer jenny = new Customer();
        jenny.setFirstName("Jenny");
        jenny.setLastName("Doe");

        Customer john = new Customer();
        john.setFirstName("John");
        john.setLastName("Doe");

        Customer dora = new Customer();
        dora.setFirstName("Dora");
        dora.setLastName("Doe");

        customerRepository.save(jenny);
        customerRepository.save(john);
        customerRepository.save(dora);

        System.out.println("Customers Loaded: " + customerRepository.count());
    }
}
