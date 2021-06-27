package guru.springframework.msscbrewery.services.v2;

import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import guru.springframework.msscbrewery.web.model.v2.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceV2Impl implements BeerServiceV2 {

    @Override
    public BeerDtoV2 getBeerById(UUID beerId) {
        return BeerDtoV2.builder().id(UUID.randomUUID())
                        .beerName("Galaxy Cat")
                        .beerStyle(BeerStyle.LAGER)
                        .build();
    }

    @Override
    public BeerDtoV2 createBeer(BeerDtoV2 beerDto) {
        return BeerDtoV2.builder()
                        .beerName("New beer")
                        .id(UUID.randomUUID())
                        .beerStyle(BeerStyle.IPA)
                        .build();
    }

    @Override
    public void update(UUID beerId, BeerDtoV2 dto) {
        log.debug("Element updated with uuid: {} and value: {}", beerId, dto);
    }

    @Override
    public void deleteBeer(UUID beerId) {
        log.debug("Element deleted with uuid: {}", beerId);
    }
}
