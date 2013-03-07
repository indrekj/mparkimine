package eu.urgas.mparkimine.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import eu.urgas.mparkimine.MyApp;
import eu.urgas.mparkimine.R;
import eu.urgas.mparkimine.activities.MainActivity;
import eu.urgas.mparkimine.items.CarRegistrationNumber;

import java.util.ArrayList;

public class RegistrationNumbersDialog extends AlertDialog {
    private final ArrayList<CarRegistrationNumber> numbers;
    private final MainActivity activity;

    public RegistrationNumbersDialog(MainActivity context) {
        super(context);
        this.activity = context;

        MyApp app = (MyApp) context.getApplication();
        this.numbers = app.carRegistrationNumbers();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initializeList();
        setInverseBackgroundForced(true);

        String chooseNumberText = getContext().getString(R.string.choose_registration_number);
        setTitle(chooseNumberText);

        String addText = getContext().getString(R.string.add_new);
        setButton(BUTTON_POSITIVE, addText, new AddNewCarRegistrationNumberListener());

        super.onCreate(savedInstanceState);
    }

    private void initializeList() {
        ListView listView = new ListView(getContext());
        listView.setId(android.R.id.list);

        ListAdapter adapter = new ArrayAdapter<CarRegistrationNumber>(getContext(),
                android.R.layout.select_dialog_item, numbers);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new SelectCarRegistrationNumberListener());

        setView(listView);
    }

    private class AddNewCarRegistrationNumberListener implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int id) {
            Dialog dialog = new CarRegistrationDialog(activity);
            dialog.show();
        }
    }

    private class SelectCarRegistrationNumberListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            CarRegistrationNumber nr = numbers.get(i);
            activity.selectCarRegistrationNumber(nr);
            dismiss();
        }
    }
}
