package guru.springframework.msscbrewery.web.controller.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.v2.BeerServiceV2;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import guru.springframework.msscbrewery.web.model.v2.BeerStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.nullTerrier", uriPort = 80) //needed for spring rest docs
@WebMvcTest(BeerControllerV2.class)
class BeerControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BeerServiceV2 beerServiceV2;

    private final BeerDtoV2 exampleBeerDto = BeerDtoV2.builder()
                                                      .beerName("Beer Name")
                                                      .beerStyle(BeerStyle.APA)
                                                      .upc(100L)
                                                      .price(BigDecimal.ONE)
                                                      .build();

    @Test
    public void testGet() throws Exception {
        when(beerServiceV2.getBeerById(any(UUID.class))).thenReturn(new BeerDtoV2(UUID.randomUUID(), "A", BeerStyle.APA, 1L, BigDecimal.ONE, null, null));

        mockMvc.perform(get("/api/v2/beer/{beerId}", UUID.randomUUID())
                                .param("iscold", "yes"))
               .andExpect(status().isOk())
               .andDo(document("/v1/beer",
                               pathParameters(
                                       parameterWithName("beerId").description("UUID of desired beer to get.")
                               ),
                               requestParameters(
                                       parameterWithName("iscold").description("Is Beer Cold Query param")
                               ),
                               responseFields(
                                       fieldWithPath("id").description("Id of Beer"),
                                       fieldWithPath("beerName").description("Name of Beer"),
                                       fieldWithPath("beerStyle").description("Style of Beer"),
                                       fieldWithPath("price").description("Price of Beer"),
                                       fieldWithPath("upc").description("UPC of Beer"),
                                       fieldWithPath("creationDate").description("Creation date of Beer"),
                                       fieldWithPath("modificationDate").description("Modification date of Beer")
                               )));
    }

    @Test
    public void testCorrectDto() throws Exception {
        when(beerServiceV2.createBeer(any(BeerDtoV2.class))).thenReturn(new BeerDtoV2(UUID.randomUUID(), "A", BeerStyle.APA, 1L, BigDecimal.ONE, null, null));

        ConstrainedFields fields = new ConstrainedFields(BeerDtoV2.class);

        String beerDtoJson = mapper.writeValueAsString(exampleBeerDto);
        mockMvc.perform(post("/api/v2/beer/").content(beerDtoJson).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(header().exists("Location"))
               .andDo(document("v1/beer",
                               requestFields(
                                       fields.withPath("id").ignored(),
                                       fields.withPath("beerName").description("Name of Beer"),
                                       fields.withPath("beerStyle").description("Style of Beer"),
                                       fields.withPath("upc").description("Beer UPC").attributes(),
                                       fields.withPath("price").description("Beer price").attributes(),
                                       fields.withPath("creationDate").ignored(),
                                       fields.withPath("modificationDate").ignored()
                               )));
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
                new BeerDtoV2(UUID.randomUUID(), "Some name", BeerStyle.APA, 100L, BigDecimal.ONE, null, null),
                new BeerDtoV2(null, null, BeerStyle.APA, 100L, BigDecimal.ONE, null, null),
                new BeerDtoV2(null, "Some Name", null, 100L, BigDecimal.ONE, null, null),
                new BeerDtoV2(null, "Some Name", BeerStyle.APA, -1L, BigDecimal.ONE, null, null)
        );
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(
                    key("constraints")
                            .value(StringUtils.collectionToDelimitedString(this.constraintDescriptions
                                                                                .descriptionsForProperty(path), ". ")));
        }
    }
}