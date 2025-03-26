package guru.qa.countrycatalog.service;

import guru.qa.countrycatalog.model.CountryJson;
import guru.qa.countrycatalog.model.graphql.CountryGql;
import guru.qa.countrycatalog.model.graphql.CountryInputGql;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CountryService {

    CountryJson addCountry(CountryJson country);

    CountryJson updateCountry(CountryJson country);

    CountryJson updateCountryName(String countryCode, String countryName);

    List<CountryJson> getAll();

    Slice<CountryGql> getAllGql(Pageable pageable);

    CountryGql addGqlCountry(CountryInputGql input);

    CountryGql updateCountryNameGql(String countryCode, String countryName);
}
