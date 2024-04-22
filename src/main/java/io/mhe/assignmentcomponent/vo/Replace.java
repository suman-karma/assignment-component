/*
 * Replace.java
 *
 * Created on June 6, 2003, 9:01 AM
 */
package io.mhe.assignmentcomponent.vo;


/**
 *
 * @author  Kameshwar
 */
public class Replace {
    /** Creates a new instance of Replace
     * keep it protected
     */
    protected Replace() {
    }

    /**
     * @param input The string that you want to do the replace on
     * @param find The String that is to be replaced
     * @param replace The String that the new String should be replaced with
     * @return  the String that has been replaced
     */
    public static String replace(String input, String find, String replace) {
    String what = input;
        String output = "";
        int ind;

        while ((ind = what.indexOf(find)) != -1) {
            output += (what.substring(0, ind) + replace);
            what = what.substring(ind + find.length());
        }

        output += what;

        return output;
    }
}
