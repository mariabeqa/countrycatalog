package guru.qa.countrycatalog.service;

import guru.qa.countrycatalog.domain.Country;

import java.util.List;

public interface CountryService {

    Country addCountry(Country country);

    Country updateCountry(Country country);

    Country updateCountryName(String countryCode, String countryName);

    List<Country> getAll();

}
