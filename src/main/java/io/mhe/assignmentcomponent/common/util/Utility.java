package io.mhe.assignmentcomponent.common.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Utility {
    public static String[] getArrayFromString(String a, String delimiter) {
        if (isBlankString(a)) {
            return (new String[0]);
        }
        StringTokenizer st = new StringTokenizer(a, delimiter);
        ArrayList<String> b = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            b.add(st.nextToken());
        }
        return b.toArray(new String[0]);
    }

    public static boolean isBlankString(String str) {
        return str == null || "".equals(str.trim()) || str.equals("null");
    }
}
