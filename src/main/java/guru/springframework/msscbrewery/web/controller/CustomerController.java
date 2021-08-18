package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/{userId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(service.getCustomer(userId));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto createdUser = service.createCustomer(customerDto);
        HttpHeaders header = new HttpHeaders();
        header.add("Location", "/api/v1/beer/" + createdUser.getUuid());
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable UUID userId, @Valid @RequestBody CustomerDto customerDto) {
        service.updateCustomer(userId, customerDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable UUID userId) {
        service.deleteCustomer(userId);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleValidationErrors(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());

        e.getConstraintViolations().forEach(violation -> errors.add(violation.getPropertyPath() + ":" + violation.getMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}

