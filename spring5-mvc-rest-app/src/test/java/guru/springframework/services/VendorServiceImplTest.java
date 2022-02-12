package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static guru.springframework.controllers.v1.VendorController.BASE_URL;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;
    VendorMapper vendorMapper = VendorMapper.instance;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    public void getAllVendors() {
        // given
        Vendor v1 = new Vendor();
        v1.setId(1L);
        v1.setName("Fruit Shop 1");

        Vendor v2 = new Vendor();
        v2.setId(2L);
        v2.setName("Fruit Shop 2");

        List<Vendor> vendors = Arrays.asList(v1, v2);

        when(vendorRepository.findAll()).thenReturn(vendors);

        // when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        assertEquals(2, vendorDTOS.size());
    }

    @Test
    public void getVendorById() {
        // given
        Long ID = 2L;
        String NAME = "Fruit Shop 2";
        String VENDOR_URL = BASE_URL + "/2";

        Vendor vendorFromRepository = new Vendor();
        vendorFromRepository.setId(ID);
        vendorFromRepository.setName(NAME);
        Optional<Vendor> vendorOptional = Optional.of(vendorFromRepository);

        when(vendorRepository.findById(ID)).thenReturn(vendorOptional);

        // when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        // then
        assertEquals(NAME, vendorDTO.getName());
        assertEquals(VENDOR_URL, vendorDTO.getVendorUrl());
    }

    @Test
    public void createNewVendor() {
        Long ID = 3L;
        String VENDOR_NAME = "Local Fruit Shop";
        String VENDOR_URL = BASE_URL + "/3";

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(VENDOR_NAME);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        // when
        VendorDTO returnedDTO = vendorService.createNewVendor(vendorDTO);

        // then
        assertEquals(VENDOR_NAME, returnedDTO.getName());
        assertEquals(VENDOR_URL, returnedDTO.getVendorUrl());
    }

    @Test
    public void saveVendorByDTO() {
        Long ID = 3L;
        String VENDOR_NAME = "Local Fruit Shop";
        String VENDOR_URL = BASE_URL + "/3";

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(VENDOR_NAME);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        // when
        VendorDTO returnedDTO = vendorService.saveVendorByDTO(ID, vendorDTO);

        // then
        assertEquals(VENDOR_NAME, returnedDTO.getName());
        assertEquals(VENDOR_URL, returnedDTO.getVendorUrl());
    }

    @Test
    public void deleteVendorById() {
        Long id = 1L;
        vendorService.deleteVendorById(id);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}