package eu.urgas.mparkimine.activities;

import java.util.ArrayList;

import eu.urgas.mparkimine.R;
import eu.urgas.mparkimine.adapters.CitiesListAdapter;
import eu.urgas.mparkimine.items.CarRegistrationNumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class StartParkingActivity extends Activity {
    private static final int CONTEXTMENU_ADDNEW = 1337;

    private TextView mChooseCarRegistrationNumberTextView;
    private ExpandableListView citiesList;
    private ArrayList<CarRegistrationNumber> carRegistrationNumbers = new ArrayList<CarRegistrationNumber>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        fetchExistingCarRegistrationNumbers();
        
        initCitiesList();
        initCarRegistrationNumberDropDownList();
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem aItem) {
        /* Switch on the ID of the item, to get what the user selected. */
        switch (aItem.getItemId()) {
        case CONTEXTMENU_ADDNEW:
            showNewCarRegistrationNumberDialog();
            break;
        default:
            CarRegistrationNumber number = carRegistrationNumbers.get(aItem.getItemId());
            selectCarRegistrationNumber(number);
            break;
        }
        return true;
    }

    private void fetchExistingCarRegistrationNumbers() {
        // TODO
        carRegistrationNumbers.add(new CarRegistrationNumber("666XXX"));
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
                view.showContextMenu();
            }
        });

        mChooseCarRegistrationNumberTextView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
                menu.setHeaderTitle(getString(R.string.choose_registration_number));
                for (int i = 0; i < carRegistrationNumbers.size(); i++) {
                    CarRegistrationNumber number = carRegistrationNumbers.get(i);
                    menu.add(1, i, 0, number.toString());
                }
                menu.add(0, CONTEXTMENU_ADDNEW, 0, getString(R.string.add_new));
            }
        });
    }
    
    private void showNewCarRegistrationNumberDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getString(R.string.new_car));
        alertBuilder.setIcon(R.drawable.icon);
        alertBuilder.setMessage(getString(R.string.registration_number));
        
        // Set an EditText view to get user input 
        final EditText input = new EditText(this);
        alertBuilder.setView(input);

        // Add and cancel listeners
        alertBuilder.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                CarRegistrationNumber number = new CarRegistrationNumber(input.getText().toString());
                carRegistrationNumbers.add(number);
                selectCarRegistrationNumber(number);
            }
        });
        alertBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        
        // Create and show
        alertBuilder.create().show();
    }

    private void selectCarRegistrationNumber(CarRegistrationNumber number) {
        mChooseCarRegistrationNumberTextView.setText(number.toString());
    }
}
