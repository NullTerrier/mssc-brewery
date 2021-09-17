package guru.springframework.msscbrewery.web.mapper;

import guru.springframework.msscbrewery.domain.Beer;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapperV2 {

    BeerDtoV2 mapBeerToBeerDto(Beer beer);

    Beer mapBeerDtoToBeer(BeerDtoV2 beerDtoV2);
}