
package eu.urgas.mparkimine;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

import eu.urgas.mparkimine.items.CarRegistrationNumber;

public class CarRegistrationNumbersManager {
    private static final String PREFS_NAME = "mparkingPrefs";
    private static final String DEFAULT_KEY = "default_car_registration_number";
    private static final String LIST_KEY = "car_registration_numbers";
    private static final String LIST_SEPARATOR = ",";

    private ArrayList<CarRegistrationNumber> carRegistrationNumbers;
    SharedPreferences settings;

    public CarRegistrationNumbersManager(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public CarRegistrationNumber add(String sNumber) {
        CarRegistrationNumber number = new CarRegistrationNumber(sNumber);
        carRegistrationNumbers.add(number);
        updatePrefs();
        return number;
    }

    public ArrayList<CarRegistrationNumber> getAll() {
        if (carRegistrationNumbers == null) {
            carRegistrationNumbers = new ArrayList<CarRegistrationNumber>();

            String rawString = settings.getString(LIST_KEY, null);
            if (rawString != null) {
                String[] elements = rawString.split(LIST_SEPARATOR);
                for (int i = 0; i < elements.length; i++) {
                    carRegistrationNumbers.add(new CarRegistrationNumber(elements[i]));
                }
            }
        }

        return carRegistrationNumbers;
    }

    public CarRegistrationNumber getDefault() {
        String number = settings.getString(DEFAULT_KEY, null);
        if (number == null) {
            return null;
        } else {
            return new CarRegistrationNumber(number);
        }
    }

    public void setDefault(CarRegistrationNumber number) {
        settings.edit().putString(DEFAULT_KEY, number.toString()).commit();
    }

    private void updatePrefs() {
        String newValue = Utils.join(carRegistrationNumbers, LIST_SEPARATOR);
        settings.edit().putString(LIST_KEY, newValue).commit();
    }
}
