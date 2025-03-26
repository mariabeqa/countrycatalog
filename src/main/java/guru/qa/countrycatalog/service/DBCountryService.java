package guru.qa.countrycatalog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.countrycatalog.data.CountryEntity;
import guru.qa.countrycatalog.data.CountryRepository;
import guru.qa.countrycatalog.exception.CountryNotFoundException;
import guru.qa.countrycatalog.model.CountryJson;
import guru.qa.countrycatalog.model.graphql.CountryGql;
import guru.qa.countrycatalog.model.graphql.CountryInputGql;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBCountryService implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public DBCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public @Nonnull CountryJson addCountry(CountryJson country) {
        CountryEntity ce = new CountryEntity();
        ce.setName(country.name());
        ce.setCode(country.code());
        ce.setArea(country.area());

        CountryEntity createdCountry = countryRepository.save(ce);
        return CountryJson.fromEntity(createdCountry);
    }

    @Override
    public @Nonnull CountryJson updateCountry(CountryJson country) {
        return countryRepository.findByName(country.name()).map(
                countryEntity -> {
                    countryEntity.setName(country.name());
                    countryEntity.setCode(country.code());
                    countryEntity.setArea(country.area());
                    return CountryJson.fromEntity(countryRepository.save(countryEntity));
                }
        ).orElseThrow(() -> new CountryNotFoundException(
                        "Can't find country with given name" + country.name()
                )
        );
    }

    @Override
    public @Nonnull CountryJson updateCountryName(String countryCode, String jsonName) {
       final String countryName;
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(jsonName);
            countryName = jsonNode.get("name").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return countryRepository.findByCode(countryCode).map(
                countryEntity -> {
                    countryEntity.setName(countryName);
                    return CountryJson.fromEntity(countryRepository.save(countryEntity));
                }
        ).orElseThrow(() -> new CountryNotFoundException(
                        "Can't find country by given country code " + countryCode
                )
        );
    }

    @Override
    public CountryGql updateCountryNameGql(String countryCode, String countryName) {
        return countryRepository.findByCode(countryCode).map(
                ce -> {
                    ce.setName(countryName);
                    CountryEntity saved = countryRepository.save(ce);
                    return new CountryGql(
                            saved.getId(),
                            saved.getName(),
                            saved.getCode(),
                            saved.getArea()
                    );
                }
        ).orElseThrow(() -> new CountryNotFoundException(
                        "Can't find country by given country code " + countryCode
                )
        );
    }

    @Override
    public @Nonnull List<CountryJson> getAll() {
        return countryRepository.findAll()
                .stream()
                .map(CountryJson::fromEntity)
                .toList();
    }

    @Override
    public Page<CountryGql> getAllGql(Pageable pageable) {
        return countryRepository.findAll(pageable)
                .map(ce -> new CountryGql(
                        ce.getId(),
                        ce.getName(),
                        ce.getCode(),
                        ce.getArea()
                ));
    }

    @Override
    public CountryGql addGqlCountry(CountryInputGql input) {
        CountryEntity ce = new CountryEntity();
        ce.setName(input.name());
        ce.setCode(input.code());
        ce.setArea(input.area());

        CountryEntity saved = countryRepository.save(ce);

        return new CountryGql(
                saved.getId(),
                saved.getName(),
                saved.getCode(),
                saved.getArea());
    }
}
