package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.bootstrap.VendorBootstrap;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceImplIT {
    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        System.out.println("Loading Vendors Data");
        System.out.println(vendorRepository.findAll().size());

        VendorBootstrap vendorBootstrap = new VendorBootstrap(vendorRepository);
        vendorBootstrap.run();

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.instance);
    }

    @Test
    public void patchVendor() {
        String updatedName = "updatedName";
        Long id = getVendorIdValue();

        Vendor originalVendor = vendorRepository.getOne(id);
        assertNotNull(originalVendor);

        String originalName = originalVendor.getName();

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(updatedName);

        vendorService.patchVendor(id, vendorDTO);

        Vendor updatedVendor = vendorRepository.findById(id).get();
        assertNotNull(updatedVendor);

        assertEquals(updatedName, updatedVendor.getName());
        assertThat(originalName, not(equalTo(updatedVendor.getName())));
    }

    private Long getVendorIdValue() {
        List<Vendor> vendors = vendorRepository.findAll();
        System.out.println("Vendors Found: " + vendors.size());
        return vendors.get(0).getId();
    }
}
