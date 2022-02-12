package guru.springframework.bootstrap;

import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VendorBootstrap implements CommandLineRunner {
    private final VendorRepository vendorRepository;

    public VendorBootstrap(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Vendor fruitShop = new Vendor();
        fruitShop.setName("Fruit Shop");

        Vendor vegetableShop = new Vendor();
        vegetableShop.setName("Vegetable Shop");

        Vendor carPartShop = new Vendor();
        carPartShop.setName("Car Part Shop");

        vendorRepository.save(fruitShop);
        vendorRepository.save(vegetableShop);
        vendorRepository.save(carPartShop);

        System.out.println("Customers Loaded: " + vendorRepository.count());
    }
}
