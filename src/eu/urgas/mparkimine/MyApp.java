package eu.urgas.mparkimine;

import android.app.Application;
import eu.urgas.mparkimine.items.CarRegistrationNumber;
import eu.urgas.mparkimine.items.Region;

public class MyApp extends Application {
    private ParkingManager parkingManager;
    private Notifier notifier;
    private CarRegistrationNumbersManager numbersManager;

    @Override
    public void onCreate() {
        parkingManager = new ParkingManager();
        notifier = new Notifier(this);
        numbersManager = new CarRegistrationNumbersManager(this);
    }

    public ParkingManager getParkingManager() {
        return this.parkingManager;
    }

    public void updateNotifications() {
        notifier.refresh();
    }

    public CarRegistrationNumbersManager getNumbersManager() {
        return numbersManager;
    }

    public CarRegistrationNumber getSelectedNumber() {
        return numbersManager.getDefault();
    }

    public void selectNumber(CarRegistrationNumber nr) {
        numbersManager.setDefault(nr);
    }

    public void startParking(Region region) {
        new SmsSender(this).send(region);
    }
}
