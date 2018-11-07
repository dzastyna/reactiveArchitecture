package ms.strings;

/**
 * String utils
 */
public class S {
    public static String $(String s, Object... args) {
        return String.format(s, args);
    }
    
    public static String from(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
