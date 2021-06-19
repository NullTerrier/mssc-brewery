package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private CustomerService service;

    @GetMapping("/{userId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(service.getCustomer(userId));
    }
}

