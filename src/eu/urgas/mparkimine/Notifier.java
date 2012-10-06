package eu.urgas.mparkimine;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import eu.urgas.mparkimine.activities.NotificationClickActivity;

public class Notifier {
    private MyApp app;
    private final static int NOTIFICATION_ID = 1;

    public Notifier(MyApp app) {
        this.app = app;
    }

    public void refresh() {
        ParkingManager.Status status = app.getParkingManager().getStatus();

        PendingIntent pendingIntent = buildPendingIntent();
        Notification notification = buildNotification(pendingIntent, status);

        NotificationManager notificationManager = getNotificationManager();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * FLAG_ACTIVITY_CLEAR_TOP - replaces old activity instead of creating a new one
     * FLAG_ACTIVITY_SINGLE_TOP - does not activate activity if already running
     *
     * Only the first flag should be enough, but the second one is also needed to prevent
     * the activity from being created again.
     */
    private PendingIntent buildPendingIntent() {
        Intent intent = new Intent(app, NotificationClickActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(app, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * FLAG_ONGOING_EVENT - mark event as ongoing
     * FLAG_NO_CLEAR - do not let user to clear this notification
     */
    private Notification buildNotification(PendingIntent intent, ParkingManager.Status status) {
        String appName = app.getString(R.string.app_name);
        String text = getStatusText(status);

        Notification notification = new Notification();
        notification.icon = R.drawable.icon;
        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        notification.setLatestEventInfo(app, appName, text, intent);
        return notification;
    }

    private String getStatusText(ParkingManager.Status status) {
        String packageName = app.getPackageName();
        String formattedStatus = status.name().toLowerCase();

        int id = app.getResources().getIdentifier(formattedStatus, "string", packageName);
        return app.getString(id);
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}