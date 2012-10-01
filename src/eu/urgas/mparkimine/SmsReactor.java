package eu.urgas.mparkimine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static eu.urgas.mparkimine.ParsedMessage.Type.START_MESSAGE;
import static eu.urgas.mparkimine.ParsedMessage.Type.STOP_MESSAGE;

public class SmsReactor extends BroadcastReceiver {
    /** TAG used for Debug-Logging */
    private static final String LOG_TAG = "SMSReactor";

    /**
     * The Action fired by the Android-System when a SMS was received. We are
     * using the Default Package-Visibility
     */
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    private ParkingManager parkingManager = ParkingManager.getInstance();
    
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            StringBuilder sb = handleIntent(intent);
            
            // Logger Debug-Output
            Log.i(LOG_TAG, "[SMSApp] onReceive: " + sb);

            // Show the Notification containing the Message.
            Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
            
            // Start the Main-Activity
            //Intent i = new Intent(context, SmsActivity.class);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(i);
        }
    }

    private StringBuilder handleIntent(Intent intent) {
        StringBuilder sb = new StringBuilder();

        // The SMS-Messages are 'hiding' within the extras of the Intent.
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            // Get all messages contained in the Intent
            Object[] pduObj = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pduObj.length];
            for (int i = 0; i < pduObj.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduObj[i]);
            }

            // Feed the StringBuilder with all Messages found.
            for (SmsMessage currentMessage : messages) {
                String senderNumber = currentMessage.getDisplayOriginatingAddress();

                if (senderNumber.equals(ParkingManager.PARKING_OPERATOR_NUMBER)) {
                    reactToParkingMessage(currentMessage.getDisplayMessageBody());
                }
            }
        }
        return sb;
    }

    private void reactToParkingMessage(String msgBody) {
        ParsedMessage parsedMessage = MessageParser.parse(msgBody);

        switch (parsedMessage.type) {
            case START_MESSAGE:
                parkingManager.setStarted();
                break;
            case STOP_MESSAGE:
                parkingManager.setStopped();
                break;
        }
    }
}
