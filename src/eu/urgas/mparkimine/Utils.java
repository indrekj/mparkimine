
package eu.urgas.mparkimine;

import java.util.ArrayList;

public class Utils {
    public static String join(String[] strings, String separator) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            if (i != 0)
                sb.append(separator);
            sb.append(strings[i]);
        }
        return sb.toString();
    }

    public static String join(ArrayList<?> values, String separator) {
        return Utils.join(toStringArray(values), separator);
    }

    public static String[] toStringArray(ArrayList<?> values) {
        String[] stringValues = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            stringValues[i] = values.get(i).toString();
        }
        return stringValues;
    }
}
