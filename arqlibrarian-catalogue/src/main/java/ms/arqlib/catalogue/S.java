package ms.arqlib.catalogue;

/**
 * String utils
 */
public class S {
    public static String $(String s, Object... args) {
        return String.format(s, args);
    }
}
