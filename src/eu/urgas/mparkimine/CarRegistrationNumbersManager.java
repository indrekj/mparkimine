package eu.urgas.mparkimine;

import eu.urgas.mparkimine.items.CarRegistrationNumber;

import java.util.ArrayList;

public class CarRegistrationNumbersManager {
    private final ArrayList<CarRegistrationNumber> carRegistrationNumbers = new ArrayList<CarRegistrationNumber>();
    private CarRegistrationNumber defaultNumber;

    public void add(CarRegistrationNumber nr) {
        carRegistrationNumbers.add(nr);
    }

    public ArrayList<CarRegistrationNumber> getAll() {
        return carRegistrationNumbers;
    }

    public CarRegistrationNumber getDefault() {
        return defaultNumber;
    }

    public void setDefault(CarRegistrationNumber number) {
        this.defaultNumber = number;
    }

    public boolean hasAny() {
        return carRegistrationNumbers.size() > 0;
    }
}
