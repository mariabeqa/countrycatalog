package guru.qa.countrycatalog.model.graphql;


public record CountryInputGql(
        String name,
        String code,
        Integer area
) {
}
