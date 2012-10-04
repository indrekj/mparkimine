package eu.urgas.mparkimine.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import eu.urgas.mparkimine.CarRegistrationNumbersManager;
import eu.urgas.mparkimine.R;
import eu.urgas.mparkimine.activities.MainActivity;
import eu.urgas.mparkimine.items.CarRegistrationNumber;
import eu.urgas.mparkimine.items.Region;

public class StartParkingDialog extends AlertDialog {
    private CarRegistrationNumbersManager carRegistrationNumbersManager;
    private Region region;

    public StartParkingDialog(MainActivity context) {
        super(context);
        this.carRegistrationNumbersManager = new CarRegistrationNumbersManager(context);
        this.region = context.getRegion();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final CarRegistrationNumber nr = carRegistrationNumbersManager.getDefault();
        String city = region.getCity().toString();

        setTitle(R.string.confirm_start_parking);
        setMessage(nr.toString() + "\n" + city + "\n" + region.getName());

        String confirmText = getContext().getString(R.string.start_parking);
        setButton(BUTTON_POSITIVE, confirmText, new StartParkingListener());

        String cancelText = getContext().getString(R.string.cancel);
        setButton(BUTTON_NEGATIVE, cancelText, new CancelListener());

        super.onCreate(savedInstanceState);
    }

    private class StartParkingListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int id) {
            //ParkingManager.getInstance().start(getApplicationContext(), nr, region);
        }
    }

    private class CancelListener implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int id) {
            dialogInterface.dismiss();
        }
    }
}
