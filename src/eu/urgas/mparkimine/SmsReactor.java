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
            }

            //StringBuilder sb = handleIntent(intent);

            // Show the Notification containing the Message.
            //Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
            
            // Start the Main-Activity
            //Intent i = new Intent(context, SmsActivity.class);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(i);
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
}
