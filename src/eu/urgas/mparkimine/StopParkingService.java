package eu.urgas.mparkimine;

import android.content.Intent;
import android.net.Uri;

public class StopParkingService {
    private MyApp app;

    public StopParkingService(MyApp app) {
        this.app = app;
    }

    public void execute() {
        app.getParkingManager().setStopping();
        app.updateNotifications();

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + ParkingManager.PARKING_OPERATOR_STOP_NUMBER));
        app.startActivity(callIntent);
    }
}
