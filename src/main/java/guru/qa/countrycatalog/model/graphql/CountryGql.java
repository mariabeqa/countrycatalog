package guru.qa.countrycatalog.model.graphql;

import java.util.UUID;

public record CountryGql(
        UUID id,
        String name,
        String code,
        Integer area
) {
}
