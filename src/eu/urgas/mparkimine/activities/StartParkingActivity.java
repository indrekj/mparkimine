package eu.urgas.mparkimine.activities;

import java.util.ArrayList;

import eu.urgas.mparkimine.R;
import eu.urgas.mparkimine.adapters.CitiesListAdapter;
import eu.urgas.mparkimine.items.CarRegistrationNumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class StartParkingActivity extends Activity {
    private static final int NEW_CAR_REGISTRATION_DIALOG_ID = 0;
    private static final int CAR_REGISTRATION_NUMBERS_DIALOG_ID = 1;

    private TextView mChooseCarRegistrationNumberTextView;
    private ExpandableListView citiesList;
    private ArrayList<CarRegistrationNumber> carRegistrationNumbers = new ArrayList<CarRegistrationNumber>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        
        Log.v("wha", "111: " + tm.getNetworkOperatorName()); // EMT, TELE2? RADIOLINJA? ELISA?
        Log.v("wha", "222: " + tm.getNetworkOperator()); // 24801, 
        Log.v("wha", "333: " + tm.getNetworkCountryIso()); // EE, 
        
        fetchExistingCarRegistrationNumbers();
        
        initCitiesList();
        initCarRegistrationNumberDropDownList();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case NEW_CAR_REGISTRATION_DIALOG_ID:
            return createNewCarRegistrationNumberDialog();
        case CAR_REGISTRATION_NUMBERS_DIALOG_ID:
            return createCarRegistrationNumbersDialog();
        default:
            return super.onCreateDialog(id);
        }
    }

    private void initCitiesList() {
        this.citiesList = (ExpandableListView) findViewById(R.id.cities_list);
        
        CitiesListAdapter citiesListAdapter = new CitiesListAdapter(this);
        this.citiesList.setAdapter(citiesListAdapter);
    }
    
    private void initCarRegistrationNumberDropDownList() {
        this.mChooseCarRegistrationNumberTextView = (TextView) this.findViewById(R.id.choose_car_registration_number);
        this.mChooseCarRegistrationNumberTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(CAR_REGISTRATION_NUMBERS_DIALOG_ID);
            }
        });
    }
    
    private void fetchExistingCarRegistrationNumbers() {
        // TODO
        carRegistrationNumbers.add(new CarRegistrationNumber("666XXX"));
    }
    
    private void selectCarRegistrationNumber(CarRegistrationNumber number) {
        mChooseCarRegistrationNumberTextView.setText(number.toString());
    }
    
    private Dialog createCarRegistrationNumbersDialog() {
        final CharSequence[] items = new CharSequence[this.carRegistrationNumbers.size()];
        for (int i = 0; i < carRegistrationNumbers.size(); i++) {
            items[i] = carRegistrationNumbers.get(i).toString();
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.choose_registration_number));
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectCarRegistrationNumber(carRegistrationNumbers.get(which));
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(getString(R.string.add_new), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                showDialog(NEW_CAR_REGISTRATION_DIALOG_ID);
            }
        });
        
        return builder.create();
    }
    
    private Dialog createNewCarRegistrationNumberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.new_car));
        builder.setMessage(getString(R.string.registration_number));
        
        // Set an EditText view to get user input 
        final EditText input = new EditText(this);
        builder.setView(input);

        // Add and cancel listeners
        builder.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                CarRegistrationNumber number = new CarRegistrationNumber(input.getText().toString());
                carRegistrationNumbers.add(number);
                selectCarRegistrationNumber(number);
                
                // not nice, but this way it creates a new dialog with new items next time
                removeDialog(CAR_REGISTRATION_NUMBERS_DIALOG_ID);
                removeDialog(NEW_CAR_REGISTRATION_DIALOG_ID);
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                removeDialog(NEW_CAR_REGISTRATION_DIALOG_ID);
            }
        });

        return builder.create();
    }
}
