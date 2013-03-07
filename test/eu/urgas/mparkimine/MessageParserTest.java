package eu.urgas.mparkimine;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MessageParserTest {
    private final MessageParser parser = new MessageParser();

    @Test
    public void testParkingStarted() {
        String rawMessage = "123ABC pargiti tsooni C120 alates 14:03 26.09" +
                ".12. Tasu arvestus algab 16:03 Parkimine lõpeb " +
                "10:03 27.09.12 limiidi 2.00eur täitumisel.";
        ParsedMessage parsedMessage = parser.parse(rawMessage);
        assertEquals(ParsedMessage.Type.START_MESSAGE, parsedMessage.type);
    }

    @Test
    public void testParkingStopped() {
        String rawMessage = "123ABC parkimine lõpetatud tsoonis C120 Pargitud" +
                " 14:03 26.09.12 kuni 17:47 26.09.12, maksumus 0.87eur. Teie " +
                "jooksva kuu parkimise saldo on 5.98eur.";
        ParsedMessage parsedMessage = parser.parse(rawMessage);
        assertEquals(ParsedMessage.Type.STOP_MESSAGE, parsedMessage.type);
    }

    @Test
    public void testParkingReminder() {
        String rawMessage = "123ABC parkimine tsoonis C120 lõpeb 16:07 14.02.13." +
                " Parkimise jätkamiseks helistage numbrile 1901. Lisainfo numbril 1903.";
        ParsedMessage parsedMessage = parser.parse(rawMessage);

        // We do not handle reminders yet
        assertEquals(ParsedMessage.Type.UNKNOWN, parsedMessage.type);
    }

    @Test
    public void testUnknownMessage() {
        String rawMessage = "Hey mate, what's up?";
        ParsedMessage parsedMessage = parser.parse(rawMessage);
        assertEquals(ParsedMessage.Type.UNKNOWN, parsedMessage.type);
    }
}