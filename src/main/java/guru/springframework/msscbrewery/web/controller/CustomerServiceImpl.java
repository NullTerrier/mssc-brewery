package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public CustomerDto getCustomer(UUID userId) {
        return CustomerDto.builder()
                          .uuid(userId)
                          .name("Marecki")
                          .build();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        return CustomerDto.builder()
                          .uuid(UUID.randomUUID())
                          .name("Marecki")
                          .build();
    }

    @Override
    public void updateCustomer(UUID userId, CustomerDto customerDto) {
        log.info("User with uuid: {}, updated with: {}", userId, customerDto);
    }

    @Override
    public void deleteCustomer(UUID userId) {
        log.info("User with uuid: {} deleted.", userId);
    }
}
