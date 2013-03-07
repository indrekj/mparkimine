package eu.urgas.mparkimine;

import android.telephony.SmsManager;
import eu.urgas.mparkimine.items.Region;

class SmsSender {
    private final MyApp app;
    private final ParkingManager parkingManager;

    public SmsSender(MyApp app) {
        this.app = app;
        this.parkingManager = app.getParkingManager();
    }

    public void send(Region region) {
        setStateToStarting();

        String phoneNumber = ParkingManager.PARKING_OPERATOR_NUMBER;
        String message = prepareMessage(region);

        sendText(phoneNumber, message);
    }

    private void setStateToStarting() {
        parkingManager.setStarting();
        app.updateNotifications();
    }

    private String prepareMessage(Region region) {
        String carNumber = app.getSelectedNumber().toString();
        String regionCode = region.getName();

        return carNumber + " " + regionCode;
    }

    private void sendText(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
