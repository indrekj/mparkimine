package eu.urgas.mparkimine;

public class ParsedMessage {
    public final Type type;

    public static enum Type {
        UNKNOWN, STOP_MESSAGE, START_MESSAGE
    }

    public ParsedMessage(Type type) {
        this.type = type;
    }
}
