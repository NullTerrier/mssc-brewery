package guru.springframework.msscbrewery.web.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CustomerDto {
    private UUID uuid;
    private String name;
}
