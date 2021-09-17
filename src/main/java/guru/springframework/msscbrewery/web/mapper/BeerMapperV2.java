package guru.springframework.msscbrewery.web.mapper;

import guru.springframework.msscbrewery.domain.Beer;
import guru.springframework.msscbrewery.web.mapper.helper.DateMapper;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerMapperV2 {

    BeerDtoV2 mapBeerToBeerDto(Beer beer);

    Beer mapBeerDtoToBeer(BeerDtoV2 beerDtoV2);
}
