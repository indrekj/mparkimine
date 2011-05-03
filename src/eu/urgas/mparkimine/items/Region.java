package eu.urgas.mparkimine.items;

public class Region {
    private City city;
    private String name;
    private String description;

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
