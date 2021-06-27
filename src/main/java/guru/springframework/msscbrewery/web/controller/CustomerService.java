package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto getCustomer(UUID userId);

    CustomerDto createCustomer(CustomerDto customerDto);

    void updateCustomer(UUID userId, CustomerDto customerDto);

    void deleteCustomer(UUID userId);
}
