package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID())
                      .beerName("Galaxy Cat")
                      .beerStyle("Pale Ale")
                      .build();
    }

    @Override
    public BeerDto createBeer(BeerDto beerDto) {
        return BeerDto.builder()
                      .beerName("New beer")
                      .id(UUID.randomUUID())
                      .beerStyle("IPA")
                      .build();
    }

    @Override
    public void update(UUID beerId, BeerDto dto) {
        log.debug("Element updated with uuid: {} and value: {}", beerId, dto);
    }

    @Override
    public void deleteBeer(UUID beerId) {
        log.debug("Element deleted with uuid: {}", beerId);
    }
}
