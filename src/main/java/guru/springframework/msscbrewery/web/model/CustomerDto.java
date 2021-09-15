package guru.springframework.msscbrewery.web.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
public class CustomerDto {

    @Null
    private UUID uuid;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;
}
