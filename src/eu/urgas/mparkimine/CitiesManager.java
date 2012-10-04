package eu.urgas.mparkimine;

import eu.urgas.mparkimine.items.City;

import java.util.ArrayList;

public class CitiesManager {
    private ArrayList<City> cities = new ArrayList<City>();

    public CitiesManager() {
        City tartu = new City("Tartu");
        tartu.addRegion("A15", "Piirkond A15, 15 minutit tasuta.");
        tartu.addRegion("B60", "Piirkond B60, 60 minutit tasuta.");
        tartu.addRegion("C120", "Piirkond C120, 120 minutit tasuta.");
        cities.add(tartu);

        City tallinn = new City("Tallinn");
        tallinn.addRegion("KESKLINN15", "Kesklinn, 15 minutit tasuta");
        tallinn.addRegion("SUDALINN15", "Südalinn, 15 minutit tasuta.");
        tallinn.addRegion("VANALINN15", "Vanalinn, 15 minutit tasuta.");
        tallinn.addRegion("LINNATEATER", "Linnateater.");
        cities.add(tallinn);
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public int size() {
        return this.cities.size();
    }

    public City get(int index) {
        return this.cities.get(index);
    }
}
