package eu.urgas.mparkimine.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.TextView;
import eu.urgas.mparkimine.CarRegistrationNumbersManager;
import eu.urgas.mparkimine.R;
import eu.urgas.mparkimine.adapters.CitiesListAdapter;
import eu.urgas.mparkimine.dialogs.RegistrationNumbersDialog;
import eu.urgas.mparkimine.items.CarRegistrationNumber;

public class MainActivity extends Activity {
    public static final int CAR_REGISTRATION_NUMBERS_DIALOG_ID = 1;

    private TextView mChooseCarRegistrationNumberTextView;

    private CarRegistrationNumbersManager carRegistrationNumberManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main);

        //TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // tm.getNetworkOperatorName() => EE Tele2
        // tm.getNetworkOperator() => 24803
        // tm.getNetworkCountryIso() => ee

        carRegistrationNumberManager = new CarRegistrationNumbersManager(this);
        mChooseCarRegistrationNumberTextView = (TextView) findViewById(R.id.choose_car_registration_number);


        selectDefaultCarRegistrationNumber();

        initCitiesList();
        initCarRegistrationNumberDropDownList();

        super.onCreate(savedInstanceState);
    }

    public void selectDefaultCarRegistrationNumber() {
        CarRegistrationNumber number = carRegistrationNumberManager.getDefault();
        if (number != null) {
            selectCarRegistrationNumber(number);
        }
    }

    public void selectCarRegistrationNumber(CarRegistrationNumber number) {
        mChooseCarRegistrationNumberTextView.setText(number.toString());
        carRegistrationNumberManager.setDefault(number);
    }

    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        switch (id) {
            case CAR_REGISTRATION_NUMBERS_DIALOG_ID:
                return new RegistrationNumbersDialog(this);
            default:
                return super.onCreateDialog(id, args);
        }
    }

    private void initCitiesList() {
        CitiesListAdapter citiesListAdapter = new CitiesListAdapter(this);
        ExpandableListView citiesList = (ExpandableListView) findViewById(R.id.cities_list);
        citiesList.setAdapter(citiesListAdapter);
    }

    private void initCarRegistrationNumberDropDownList() {
        mChooseCarRegistrationNumberTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CAR_REGISTRATION_NUMBERS_DIALOG_ID);
            }
        });
    }
}
