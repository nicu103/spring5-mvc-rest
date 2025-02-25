package guru.springframework.api.v1.mapper;

import guru.springframework.model.CustomerDTO;
import guru.springframework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper instance = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDtoToCustomer(CustomerDTO customerDTO);
}
