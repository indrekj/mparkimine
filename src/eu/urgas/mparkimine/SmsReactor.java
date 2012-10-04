package eu.urgas.mparkimine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReactor extends BroadcastReceiver {
    /**
     * The Action fired by the Android-System when a SMS was received. We are
     * using the Default Package-Visibility
     */
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            // The SMS-Messages are 'hiding' within the extras of the Intent.
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SmsMessage[] messages = getMessagesFromBundle(bundle);
                processMessages(messages);

                Notifier notifier = new Notifier(context);
                notifier.refresh();
            }
        }
    }

    private SmsMessage[] getMessagesFromBundle(Bundle bundle) {
        Object[] pduObj = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pduObj.length];
        for (int i = 0; i < pduObj.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[]) pduObj[i]);
        }
        return messages;
    }

    private void processMessages(SmsMessage[] messages) {
        SmsHandler handler = new SmsHandler();
        for (SmsMessage message : messages) {
            handler.execute(message);
        }
    }

    /*
    * String text = nr.toString() + " " + region.getName(); SmsManager
    * smsManager = SmsManager.getDefault();
    * smsManager.sendTextMessage(PARKING_OPERATOR_NUMBER, null, text, null,
    * null);
    */

    //updateNotification(mContext.getString(R.string.starting));
    //updateNotification(mContext.getString(R.string.started));
    //updateNotification(mContext.getString(R.string.failed));
    //updateNotification(mContext.getString(R.string.stopping));
    //updateNotification(mContext.getString(R.string.stopped));
}
