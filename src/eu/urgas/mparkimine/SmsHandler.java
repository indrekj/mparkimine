package eu.urgas.mparkimine;

import android.telephony.SmsMessage;

public class SmsHandler {
    private MessageParser messageParser;

    public SmsHandler() {
        this.messageParser = new MessageParser();
    }

    public SmsHandler(MessageParser parser) {
        this.messageParser = parser;
    }

    public void execute(SmsMessage message) {
        String senderNumber = message.getDisplayOriginatingAddress();

        if (senderNumber.equals(ParkingManager.PARKING_OPERATOR_NUMBER)) {
            updateParkingManager(message.getDisplayMessageBody());
        }
    }

    private void updateParkingManager(String rawMessage) {
        ParsedMessage parsedMessage = messageParser.parse(rawMessage);
        ParkingManager parkingManager = ParkingManager.getInstance();

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