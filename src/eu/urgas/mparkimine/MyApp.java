package eu.urgas.mparkimine;

import android.app.Application;
import eu.urgas.mparkimine.items.CarRegistrationNumber;
import eu.urgas.mparkimine.items.Region;

import java.util.ArrayList;

public class MyApp extends Application {
    private ParkingManager parkingManager;
    private Notifier notifier;
    private CarRegistrationNumbersManager numbersManager;
    private CarRegistrationNumbersManagerRepository numbersRepository;

    @Override
    public void onCreate() {
        parkingManager = new ParkingManager();
        notifier = new Notifier(this);
        numbersRepository = new CarRegistrationNumbersManagerRepository(this);
        numbersManager = numbersRepository.get();
    }

    public ParkingManager getParkingManager() {
        return this.parkingManager;
    }

    public void updateNotifications() {
        notifier.refresh();
    }

    public CarRegistrationNumber getSelectedNumber() {
        return numbersManager.getDefault();
    }

    public void selectNumber(CarRegistrationNumber nr) {
        numbersManager.setDefault(nr);
        numbersRepository.save(numbersManager);
    }

    public void startParking(Region region) {
        new SmsSender(this).send(region);
    }

    public void stopParking() {
        new StopParkingService(this).execute();
    }

    public void addCarRegistrationNumber(CarRegistrationNumber nr) {
        numbersManager.add(nr);
        numbersRepository.save(numbersManager);
    }

    public boolean hasAnyCarRegistrationNumbers() {
        return numbersManager.hasAny();
    }

    public ArrayList<CarRegistrationNumber> carRegistrationNumbers() {
        return numbersManager.getAll();
    }
}
