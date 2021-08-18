package guru.springframework.msscbrewery.web.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.UUID;

@Data
@Builder
public class CustomerDto {

    @Null
    private UUID uuid;

    @NotNull
    @Length(min = 3, max = 100)
    private String name;
}
