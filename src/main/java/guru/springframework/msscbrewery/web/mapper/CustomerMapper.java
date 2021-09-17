package guru.springframework.msscbrewery.web.mapper;

import guru.springframework.msscbrewery.domain.Customer;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDto mapCustomerToCustomerDto(Customer customer);

    Customer mapCustomerDtoToCustomer(CustomerDto customerDto);
}
