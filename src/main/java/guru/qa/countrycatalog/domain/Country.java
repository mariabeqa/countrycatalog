package guru.qa.countrycatalog.domain;

import guru.qa.countrycatalog.data.CountryEntity;
import jakarta.annotation.Nonnull;

public record Country(
        String name,
        String code,
        Integer area
) {

    public static @Nonnull Country fromEntity(@Nonnull CountryEntity entity) {
        return new Country(
                entity.getName(),
                entity.getCode(),
                entity.getArea()
        );
    }
}
