package eu.urgas.mparkimine;

public class MessageParser {
    /**
     * At the moment I'm avoiding using umlauts. I'm afraid that they're are
     * not used correct ones.
     */
    public ParsedMessage parse(String rawMessage) {
        ParsedMessage.Type type;

        if (rawMessage.contains("pargiti tsooni")) {
            type = ParsedMessage.Type.START_MESSAGE;
        } else if (rawMessage.contains("parkimine l")) {
            type = ParsedMessage.Type.STOP_MESSAGE;
        } else {
            type = ParsedMessage.Type.UNKNOWN;
        }

        return new ParsedMessage(type);
    }
}