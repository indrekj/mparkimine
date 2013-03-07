package eu.urgas.mparkimine.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.TextView;
import eu.urgas.mparkimine.MyApp;
import eu.urgas.mparkimine.R;
import eu.urgas.mparkimine.adapters.CitiesListAdapter;
import eu.urgas.mparkimine.dialogs.RegistrationNumbersDialog;
import eu.urgas.mparkimine.items.CarRegistrationNumber;

public class MainActivity extends Activity {
    private TextView carNumbersSelectView;
    private MyApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.app = (MyApp) getApplication();

        setContentView(R.layout.main);

        //TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // tm.getNetworkOperatorName() => EE Tele2
        // tm.getNetworkOperator() => 24803
        // tm.getNetworkCountryIso() => ee

        carNumbersSelectView = (TextView) findViewById(R.id.choose_car_registration_number);
        carNumbersSelectView.setOnClickListener(new SelectCarNumberListener());

        selectDefaultCarRegistrationNumber();

        initCitiesList();

        super.onCreate(savedInstanceState);
    }

    private void selectDefaultCarRegistrationNumber() {
        CarRegistrationNumber number = app.getSelectedNumber();
        if (number != null) {
            selectCarRegistrationNumber(number);
        }
    }

    public void selectCarRegistrationNumber(CarRegistrationNumber number) {
        carNumbersSelectView.setText(number.toString());
        app.selectNumber(number);
    }

    private void initCitiesList() {
        CitiesListAdapter citiesListAdapter = new CitiesListAdapter(this);
        ExpandableListView citiesList = (ExpandableListView) findViewById(R.id.cities_list);
        citiesList.setAdapter(citiesListAdapter);
    }

    private class SelectCarNumberListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            Dialog dialog = new RegistrationNumbersDialog(MainActivity.this);
            dialog.show();
        }
    }
}
