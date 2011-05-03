package eu.urgas.mparkimine;

import eu.urgas.mparkimine.items.CarRegistrationNumber;
import eu.urgas.mparkimine.items.Region;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import java.util.Date;

public class ParkingManager {
    private static ParkingManager instance = null;

    protected ParkingManager() {
        // Exists only to defeat instantiation.
    }

    public static ParkingManager getInstance() {
        if (instance == null) {
            instance = new ParkingManager();
        }
        return instance;
    }

    private static final class Status {
        private static final int OFFLINE = 0x00;
        private static final int STARTING = 0x10;
        private static final int STARTED = 0x20;
        private static final int FAILED = 0x21;
        private static final int STOPPING = 0x30;
        private static final int STOPPED = 0x40;
    }

    public static final String PARKING_OPERATOR_NUMBER = "1902";
    private int status = Status.OFFLINE;
    private Date startedAt;
    private Date stoppedAt;
    private CarRegistrationNumber carRegistrationNumber;

    private Context mContext;
    private NotificationManager notificationManager;
    private Notification notification;

    public void start(Context context, CarRegistrationNumber nr, Region region) {
        mContext = context;

        setUpNotifications();

        carRegistrationNumber = nr;
        setStarting();

        /*
         * String text = nr.toString() + " " + region.getName(); SmsManager
         * smsManager = SmsManager.getDefault();
         * smsManager.sendTextMessage(PARKING_OPERATOR_NUMBER, null, text, null,
         * null);
         */
    }

    public void reset() {
        status = Status.OFFLINE;
        startedAt = null;
        stoppedAt = null;
        carRegistrationNumber = null;
    }

    public boolean isRunning() {
        return status != Status.OFFLINE;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public Date getStoppedAt() {
        return stoppedAt;
    }

    public CarRegistrationNumber getCarRegistrationNumber() {
        return isRunning() ? this.carRegistrationNumber : null;
    }

    public void setStarting() {
        status = Status.STARTING;
        updateNotification(mContext.getString(R.string.starting));
    }

    public void setStarted() {
        startedAt = new Date();
        status = Status.STARTED;
        updateNotification(mContext.getString(R.string.started));
    }

    public void setFailed() {
        status = Status.FAILED;
        updateNotification(mContext.getString(R.string.failed));
    }

    public void setStopping() {
        status = Status.STOPPING;
        updateNotification(mContext.getString(R.string.stopping));
    }

    public void setStopped() {
        stoppedAt = new Date();
        status = Status.STOPPED;
        updateNotification(mContext.getString(R.string.stopped));
    }

    private void setUpNotifications() {
        notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notification = new Notification();
        notification.icon = R.drawable.icon;
        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
    }

    private void updateNotification(String content) {
        Intent notificationIntent = new Intent();
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setLatestEventInfo(mContext, "M-Parkimine", content, contentIntent);
        notificationManager.notify(1, notification);
    }
}
