package eu.urgas.mparkimine.items;

public class Region {
    private String name;
    private String description;

    public Region(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
