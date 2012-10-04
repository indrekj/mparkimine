package eu.urgas.mparkimine.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import eu.urgas.mparkimine.CarRegistrationNumbersManager;
import eu.urgas.mparkimine.R;
import eu.urgas.mparkimine.activities.MainActivity;
import eu.urgas.mparkimine.items.CarRegistrationNumber;

public class CarRegistrationDialog extends AlertDialog {
    private CarRegistrationNumbersManager carRegistrationNumbersManager;
    private MainActivity activity;

    public CarRegistrationDialog(MainActivity context) {
        super(context);
        this.activity = context;
        this.carRegistrationNumbersManager = new CarRegistrationNumbersManager(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String newCarText = getContext().getString(R.string.new_car);
        setTitle(newCarText);

        String regNrText = getContext().getString(R.string.registration_number);
        setMessage(regNrText);

        // Set EditText to context. This is for getting user input.
        final EditText input = new EditText(getContext());
        setView(input);

        String addText = getContext().getString(R.string.add);
        setButton(BUTTON_POSITIVE, addText, new AddCarRegistrationNumberListener(input));

        String cancelText = getContext().getString(R.string.cancel);
        setButton(BUTTON_NEGATIVE, cancelText, new CancelListener());

        super.onCreate(savedInstanceState);
    }

    private class AddCarRegistrationNumberListener implements OnClickListener {
        private EditText input;

        public AddCarRegistrationNumberListener(EditText input) {
            this.input = input;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int id) {
            String newNr = input.getText().toString();

            CarRegistrationNumber nr = new CarRegistrationNumber(newNr);
            carRegistrationNumbersManager.add(nr);

            activity.selectCarRegistrationNumber(nr);

            dialogInterface.dismiss();
        }
    }

    private class CancelListener implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int id) {
            dialogInterface.dismiss();
        }
    }
}