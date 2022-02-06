package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.CustomerService;
import guru.springfamework.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractControllerTest{

    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllCustomers() throws Exception {

        // given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("John");
        customer1.setLastName("Doe");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstName("Jenny");
        customer2.setLastName("Doe");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        // when
        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception {
        // given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Jenny");
        customer.setLastName("Doe");

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        // when
        mockMvc.perform(get(CustomerController.BASE_URL+ "/1")
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Jenny")))
                .andExpect(jsonPath("$.lastName", equalTo("Doe")));
    }

    @Test
    public void createNewCustomer() throws Exception {
        // given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Yuyu");
        customer.setLastName("Hakusho");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Yuyu")))
                .andExpect(jsonPath("$.lastName", equalTo("Hakusho")))
                .andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        // given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Mariah");
        customer.setLastName("Carey");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL+ "/1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(CustomerController.BASE_URL+ "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Mariah")))
                .andExpect(jsonPath("$.lastName", equalTo("Carey")))
                .andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void patchCustomer() throws Exception {
        // given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName("Flintstone");
        returnDTO.setCustomerUrl(CustomerController.BASE_URL+ "/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(CustomerController.BASE_URL+ "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.lastName", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL+ "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
    public void testGetByNameNotFound() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CategoryController.BASE_URL + "/222")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}