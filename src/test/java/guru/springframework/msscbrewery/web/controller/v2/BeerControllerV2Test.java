package guru.springframework.msscbrewery.web.controller.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.v2.BeerServiceV2;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import guru.springframework.msscbrewery.web.model.v2.BeerStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerControllerV2.class)
class BeerControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BeerServiceV2 beerServiceV2;

    private final BeerDtoV2 exampleBeerDto = BeerDtoV2.builder()
                                                      .id(UUID.randomUUID())
                                                      .beerName(null)
                                                      .beerStyle(null)
                                                      .upc(-1L)
                                                      .build();

    @Test
    public void testGet() throws Exception {
        when(beerServiceV2.getBeerById(any(UUID.class))).thenReturn(new BeerDtoV2(UUID.randomUUID(), "A", BeerStyle.APA, 1L));

        mockMvc.perform(get("/api/v2/beer/" + UUID.randomUUID()))
               .andExpect(status().isOk());
    }

    @Test
    public void testCorrectDto() throws Exception {
        when(beerServiceV2.createBeer(any(BeerDtoV2.class))).thenReturn(new BeerDtoV2(UUID.randomUUID(), "A", BeerStyle.APA, 1L));

        String beerDtoJson = mapper.writeValueAsString(exampleBeerDto);
        mockMvc.perform(post("/api/v2/beer/").content(beerDtoJson).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(header().exists("Location"));
    }

    @ParameterizedTest
    @MethodSource("incorrectDtos")
    public void testIncorrectDtoShouldReturn400(BeerDtoV2 wrongDto) throws Exception {

        String beerDtoJson = mapper.writeValueAsString(wrongDto);

        mockMvc.perform(post("/api/v2/beer/").content(beerDtoJson).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    private static Stream<BeerDtoV2> incorrectDtos() {
        return Stream.of(
                new BeerDtoV2(UUID.randomUUID(), "Some name", BeerStyle.APA, 100L),
                new BeerDtoV2(null, null, BeerStyle.APA, 100L),
                new BeerDtoV2(null, "Some Name", null, 100L),
                new BeerDtoV2(null, "Some Name", BeerStyle.APA, -1L)
        );
    }
}