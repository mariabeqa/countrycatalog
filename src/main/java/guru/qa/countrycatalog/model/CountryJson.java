package guru.qa.countrycatalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.countrycatalog.data.CountryEntity;
import jakarta.annotation.Nonnull;

public record CountryJson(
        @JsonProperty("name")
        String name,
        @JsonProperty("code")
        String code,
        @JsonProperty("area")
        Integer area
) {

    public static @Nonnull CountryJson fromEntity(@Nonnull CountryEntity entity) {
        return new CountryJson(
                entity.getName(),
                entity.getCode(),
                entity.getArea()
        );
    }
}
