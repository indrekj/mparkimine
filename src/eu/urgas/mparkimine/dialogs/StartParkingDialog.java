package eu.urgas.mparkimine.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import eu.urgas.mparkimine.MyApp;
import eu.urgas.mparkimine.R;
import eu.urgas.mparkimine.items.CarRegistrationNumber;
import eu.urgas.mparkimine.items.Region;

public class StartParkingDialog extends AlertDialog {
    private Region region;
    private MyApp app;

    public StartParkingDialog(Context context, Region region) {
        super(context);
        this.app = (MyApp) context.getApplicationContext();
        this.region = region;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final CarRegistrationNumber nr = app.getSelectedNumber();
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
            app.startParking(region);
        }
    }

    private class CancelListener implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int id) {
            dialogInterface.dismiss();
        }
    }
}
