package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.instance, customerRepository);
    }

    @Test
    public void getAllCustomers() {
        // given
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        // when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        // then
        assertEquals(2, customerDTOS.size());
    }

    @Test
    public void getCustomerById() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Optional<Customer> customerOptional = Optional.of(customer);

        when(customerRepository.findById(anyLong())).thenReturn(customerOptional);

        // when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        // then
        assertEquals("John", customerDTO.getFirstName());
        assertEquals("Doe", customerDTO.getLastName());
    }

    @Test
    public void createNewCustomer() {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // when
        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

        // then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals(customerDTO.getLastName(), savedDto.getLastName());
        assertEquals("/api/v1/customer/1", savedDto.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDTO() {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jim");
        customerDTO.setLastName("Morrison");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // when
        CustomerDTO savedDTO = customerService.saveCustomerByDTO(1L, customerDTO);

        assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
        assertEquals(customerDTO.getLastName(), savedDTO.getLastName());
        assertEquals("/api/v1/customer/1", savedDTO.getCustomerUrl());
    }

    @Test
    public void deleteCustomerById() {
        Long id = 1L;
        customerService.deleteCustomerById(id);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}