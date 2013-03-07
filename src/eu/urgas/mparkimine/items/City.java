package eu.urgas.mparkimine.items;

import java.util.ArrayList;

public class City {
    private final String name;
    private final ArrayList<Region> regions = new ArrayList<Region>();

    public City(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public Region getRegion(int index) {
        return this.regions.get(index);
    }

    public void addRegion(String name, String description) {
        regions.add(new Region(this, name, description));
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }
}
