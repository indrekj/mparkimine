package eu.urgas.mparkimine;

import android.telephony.SmsMessage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmsHandlerTest {
    private MyApp context = mock(MyApp.class);
    private SmsMessage message = mock(SmsMessage.class);
    private MessageParser parser = mock(MessageParser.class);
    private SmsHandler handler = new SmsHandler(context, parser);

    @Before
    public void setUp() {
        when(context.getParkingManager()).thenReturn(new ParkingManager());

        when(message.getDisplayOriginatingAddress()).
                thenReturn(ParkingManager.PARKING_OPERATOR_NUMBER);
        when(message.getDisplayMessageBody()).thenReturn("");
    }

    @Test
    public void testStartParking() {
        when(parser.parse("")).
                thenReturn(new ParsedMessage(ParsedMessage.Type.START_MESSAGE));

        handler.execute(message);
        assertEquals(ParkingManager.Status.STARTED, currentParkingManagerStatus());
    }

    @Test
    public void testStopParking() {
        when(parser.parse("")).
                thenReturn(new ParsedMessage(ParsedMessage.Type.STOP_MESSAGE));

        handler.execute(message);
        assertEquals(ParkingManager.Status.STOPPED, currentParkingManagerStatus());
    }

    @Test
    public void testUnknownMessage() {
        when(parser.parse("")).
                thenReturn(new ParsedMessage(ParsedMessage.Type.UNKNOWN));

        handler.execute(message);
        assertEquals(ParkingManager.Status.OFFLINE, currentParkingManagerStatus());
    }

    @Test
    public void testInvalidSenderIsIgnored() {
        when(message.getDisplayOriginatingAddress()).thenReturn("invalid");

        handler.execute(message);
        assertEquals(ParkingManager.Status.OFFLINE, currentParkingManagerStatus());
    }

    private ParkingManager.Status currentParkingManagerStatus() {
        return context.getParkingManager().getStatus();
    }
}