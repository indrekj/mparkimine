package eu.urgas.mparkimine.activities;

import android.app.Activity;
import android.os.Bundle;
import eu.urgas.mparkimine.MyApp;
import eu.urgas.mparkimine.ParkingManager;

public class NotificationClickActivity extends Activity {
    private MyApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.app = (MyApp) getApplication();

        if (isParkingStarted()) {
            app.stopParking();
        }

        finish();
    }

    private boolean isParkingStarted() {
        return app.getParkingManager().getStatus() == ParkingManager.Status.STARTED;
    }
}
