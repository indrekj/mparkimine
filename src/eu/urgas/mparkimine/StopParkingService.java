package eu.urgas.mparkimine;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.*;
import android.net.Uri;
import android.util.Log;

import java.util.List;

class StopParkingService {
    private final MyApp app;

    public StopParkingService(MyApp app) {
        this.app = app;
    }

    public void execute() {
        app.getParkingManager().setStopping();
        app.updateNotifications();

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + ParkingManager.PARKING_OPERATOR_STOP_NUMBER));


        List<ResolveInfo> resolveInfos = app.getPackageManager().queryIntentActivities
                (callIntent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

        if (resolveInfos.size() > 0) {
            ActivityInfo activityInfo = resolveInfos.get(0).activityInfo;
            String appPackage = activityInfo.applicationInfo.packageName;
            String appName = activityInfo.name;
            callIntent.setComponent((new ComponentName(appPackage, appName)));
        }

        app.startActivity(callIntent);
    }
}
