package eu.urgas.mparkimine;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Notifier {
    private Context context;

    public Notifier(Context context) {
        this.context = context;
    }

    public void refresh() {
        ParkingManager.Status status = ParkingManager.getInstance().getStatus();

        PendingIntent pendingIntent = buildPendingIntent();
        Notification notification = buildNotification(pendingIntent, status);

        NotificationManager notificationManager = getNotificationManager();
        notificationManager.notify(1, notification);
    }

    /**
     * FLAG_ACTIVITY_CLEAR_TOP - replaces old activity instead of creating a new one
     * FLAG_ACTIVITY_SINGLE_TOP - does not activate activity if already running
     *
     * Only the first flag should be enough, but the second one is also needed to prevent
     * the activity from being created again.
     */
    private PendingIntent buildPendingIntent() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * FLAG_ONGOING_EVENT - mark event as ongoing
     * FLAG_NO_CLEAR - do not let user to clear this notification
     */
    private Notification buildNotification(PendingIntent intent, ParkingManager.Status status) {
        String appName = context.getString(R.string.app_name);
        String text = getStatusText(status);

        Notification notification = new Notification();
        notification.icon = R.drawable.icon;
        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        notification.setLatestEventInfo(context, appName, text, intent);
        return notification;
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private String getStatusText(ParkingManager.Status status) {
        String packageName = context.getPackageName();
        String formattedStatus = status.name().toLowerCase();

        int id = context.getResources().getIdentifier(formattedStatus, "string", packageName);
        return context.getString(id);
    }
}