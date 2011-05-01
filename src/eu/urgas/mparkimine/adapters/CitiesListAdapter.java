package eu.urgas.mparkimine.adapters;

import eu.urgas.mparkimine.CitiesManager;
import eu.urgas.mparkimine.R;
import eu.urgas.mparkimine.items.City;
import eu.urgas.mparkimine.items.Region;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class CitiesListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private CitiesManager citiesManager;

    public CitiesListAdapter(Context context) {
        this.mContext = context;
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
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.child_row, null);
        }
        Region region = getChild(groupPosition, childPosition);
        TextView name = (TextView) convertView.findViewById(R.id.child_name);
        name.setText(region.getName());
        TextView desc = (TextView) convertView.findViewById(R.id.child_desc);
        desc.setText(region.getDescription());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return citiesManager.get(groupPosition).getRegionsSize();
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
            LayoutInflater inflater = LayoutInflater.from(mContext);
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
