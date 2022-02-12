package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractControllerTest {

    public static final String VENDOR_URL_WITH_ID = VendorController.BASE_URL + "/1";
    private final String FIRST_VENDOR_NAME = "Car Part Shop";
    private final String SECOND_VENDOR_NAME = "Vegetables Shop";

    @InjectMocks
    VendorController vendorController;

    @Mock
    VendorService vendorService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllVendors() throws Exception {
        // given
        VendorDTO v1 = new VendorDTO();
        v1.setName(FIRST_VENDOR_NAME);

        VendorDTO v2 = new VendorDTO();
        v2.setName(SECOND_VENDOR_NAME);

        List<VendorDTO> vendors = Arrays.asList(v1, v2);

        when(vendorService.getAllVendors()).thenReturn(vendors);

        // when
        mockMvc.perform(get(VendorController.BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {
        // given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(FIRST_VENDOR_NAME);
        vendor.setVendorUrl(VENDOR_URL_WITH_ID);
        when(vendorService.getVendorById(anyLong())).thenReturn(vendor);

        //when
        mockMvc.perform(get(VENDOR_URL_WITH_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(FIRST_VENDOR_NAME)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VENDOR_URL_WITH_ID)));
    }

    @Test
    public void createNewVendor() throws Exception {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(FIRST_VENDOR_NAME);
        vendorDTO.setVendorUrl(VENDOR_URL_WITH_ID);

        VendorDTO returnDto = new VendorDTO();
        returnDto.setName(vendorDTO.getName());
        returnDto.setVendorUrl(vendorDTO.getVendorUrl());

        when(vendorService.createNewVendor(vendorDTO)).thenReturn(returnDto);

        // when
        mockMvc.perform(post(VendorController.BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(FIRST_VENDOR_NAME)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VENDOR_URL_WITH_ID)));
    }

    @Test
    public void updateExistingVendor() throws Exception {
        // given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(FIRST_VENDOR_NAME);
        vendor.setVendorUrl(VENDOR_URL_WITH_ID);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl(vendor.getVendorUrl());

        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        // when
        mockMvc.perform(put(VENDOR_URL_WITH_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(FIRST_VENDOR_NAME)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VENDOR_URL_WITH_ID)));

    }

    @Test
    public void patchExistingVendor() throws Exception {
        // given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(FIRST_VENDOR_NAME);
        vendor.setVendorUrl(VENDOR_URL_WITH_ID);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl(vendor.getVendorUrl());

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        // when
        mockMvc.perform(patch(VENDOR_URL_WITH_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(FIRST_VENDOR_NAME)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VENDOR_URL_WITH_ID)));
    }

    @Test
    public void deleteExistingVendor() throws Exception {
        mockMvc.perform(delete(VENDOR_URL_WITH_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(vendorService).deleteVendorById(anyLong());
    }
}