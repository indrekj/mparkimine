
package eu.urgas.mparkimine.adapters;

import eu.urgas.mparkimine.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class CitiesListAdapter extends BaseExpandableListAdapter {
    private Context mContext;

    // Sample data set. children[i] contains the children (String[]) for
    // groups[i].
    private String[] groups = {
            "Tartu", "Tallinn", "etc"
    };
    private String[][] children = {
            {
                    "Barry", "Chuck", "David"
            },
            {
                    "Ace", "Bandit", "Cha-Cha", "Deuce"
            },
            {
                    "Fluffy", "Snuggles"
            }
    };

    public CitiesListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children[groupPosition][childPosition];
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
        TextView name = (TextView) convertView.findViewById(R.id.child_name);
        name.setText(getChild(groupPosition, childPosition).toString());
        TextView desc = (TextView) convertView.findViewById(R.id.child_desc);
        desc.setText("Piirkond X, Y minutit tasuta");
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return groups.length;
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
