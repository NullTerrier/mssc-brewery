package guru.springframework.msscbrewery.web.mapper;

import guru.springframework.msscbrewery.domain.Beer;
import guru.springframework.msscbrewery.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    BeerDto mapBeerToBeerDto(Beer beer);

    Beer mapBeerDtoToBeer(BeerDto beerDto);

}
