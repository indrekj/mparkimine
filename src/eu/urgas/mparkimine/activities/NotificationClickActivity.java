package eu.urgas.mparkimine.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import eu.urgas.mparkimine.MyApp;
import eu.urgas.mparkimine.ParkingManager;

public class NotificationClickActivity extends Activity {
    private MyApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.app = (MyApp) getApplication();

        if (isParkingStopped()) {
            showMainMenu();
        } else if (isParkingStarted()) {
            app.stopParking();
        }

        finish();
    }

    private void showMainMenu() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private boolean isParkingStarted() {
        return app.getParkingManager().getStatus() == ParkingManager.Status.STARTED;
    }

    private boolean isParkingStopped() {
        ParkingManager.Status status = app.getParkingManager().getStatus();
        return status == ParkingManager.Status.STOPPED || status == ParkingManager.Status.OFFLINE;
    }
}
