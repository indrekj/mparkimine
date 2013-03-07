package eu.urgas.mparkimine.items;

public class Region {
    private final City city;
    private final String name;
    private final String description;

    public Region(City city, String name, String description) {
        this.city = city;
        this.name = name;
        this.description = description;
    }

    public City getCity() {
        return this.city;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
