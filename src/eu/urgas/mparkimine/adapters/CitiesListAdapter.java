package eu.urgas.mparkimine.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import eu.urgas.mparkimine.CitiesManager;
import eu.urgas.mparkimine.MyApp;
import eu.urgas.mparkimine.R;
import eu.urgas.mparkimine.dialogs.StartParkingDialog;
import eu.urgas.mparkimine.items.City;
import eu.urgas.mparkimine.items.Region;

public class CitiesListAdapter extends BaseExpandableListAdapter {
    private final Activity activity;
    private final MyApp app;
    private final CitiesManager citiesManager;

    public CitiesListAdapter(Activity activity) {
        this.activity = activity;
        this.app = (MyApp) activity.getApplicationContext();
        this.citiesManager = new CitiesManager();
    }

    @Override
    public Region getChild(int groupPosition, int childPosition) {
        return this.citiesManager.get(groupPosition).getRegion(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView,
            ViewGroup parent) {
        final Region region = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView = inflater.inflate(R.layout.child_row, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.child_name);
        name.setText(region.getName());
        TextView desc = (TextView) convertView.findViewById(R.id.child_desc);
        desc.setText(region.getDescription());

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (app.hasAnyCarRegistrationNumbers()) {
                    Dialog dialog = new StartParkingDialog(activity, region);
                    dialog.show();
                } else {
                    // REFACTOR
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Tähelepanu");
                    builder.setMessage("Enne parkmist lisa auto registreerimisnumber");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return citiesManager.get(groupPosition).getRegions().size();
    }

    @Override
    public City getGroup(int groupPosition) {
        return citiesManager.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return citiesManager.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(app);
            convertView = inflater.inflate(R.layout.group_row, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.child_name);
        name.setText(getGroup(groupPosition).toString());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
