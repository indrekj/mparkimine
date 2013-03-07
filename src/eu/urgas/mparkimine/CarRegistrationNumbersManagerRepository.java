package eu.urgas.mparkimine;

import android.content.Context;
import android.content.SharedPreferences;
import eu.urgas.mparkimine.items.CarRegistrationNumber;

class CarRegistrationNumbersManagerRepository {
    private static final String PREFS_NAME = "mparkingPrefs";
    private static final String DEFAULT_KEY = "default_car_registration_number";
    private static final String LIST_KEY = "car_registration_numbers";
    private static final String LIST_SEPARATOR = ",";

    private final SharedPreferences settings;

    public CarRegistrationNumbersManagerRepository(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public CarRegistrationNumbersManager get() {
        CarRegistrationNumbersManager manager = new CarRegistrationNumbersManager();

        fetchAll(manager);
        fetchDefault(manager);

        return manager;
    }

    public void save(CarRegistrationNumbersManager manager) {
        saveAll(manager);
        saveDefault(manager);
    }

    private void fetchAll(CarRegistrationNumbersManager manager) {
        String rawString = settings.getString(LIST_KEY, null);
        if (rawString != null) {
            String[] elements = rawString.split(LIST_SEPARATOR);
            for (String element : elements) {
                manager.add(new CarRegistrationNumber(element));
            }
        }
    }

    private void fetchDefault(CarRegistrationNumbersManager manager) {
        String number = settings.getString(DEFAULT_KEY, null);
        if (number != null) {
            manager.setDefault(new CarRegistrationNumber(number));
        }
    }

    private void saveAll(CarRegistrationNumbersManager manager) {
        String newValue = Utils.join(manager.getAll(), LIST_SEPARATOR);
        settings.edit().putString(LIST_KEY, newValue).commit();
    }

    private void saveDefault(CarRegistrationNumbersManager manager) {
        CarRegistrationNumber defaultNumber = manager.getDefault();
        if (defaultNumber != null) {
            settings.edit().putString(DEFAULT_KEY, defaultNumber.toString()).commit();
        }
    }
}
