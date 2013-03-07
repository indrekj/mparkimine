package eu.urgas.mparkimine;

import java.util.ArrayList;

class Utils {
    @SuppressWarnings("WeakerAccess")
    public static String join(String[] strings, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (i != 0)
                sb.append(separator);
            sb.append(strings[i]);
        }
        return sb.toString();
    }

    @SuppressWarnings("SameParameterValue")
    public static String join(ArrayList<?> values, String separator) {
        return Utils.join(toStringArray(values), separator);
    }

    @SuppressWarnings("WeakerAccess")
    public static String[] toStringArray(ArrayList<?> values) {
        String[] stringValues = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            stringValues[i] = values.get(i).toString();
        }
        return stringValues;
    }
}
