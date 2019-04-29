package ch.uzh.ifi.seal.ase19.core.utils;

public class SSTUtils {
    public static String getFullyQualifiedNameWithoutGenerics(String in) {
        if (in.contains("`")) {
            in = in.substring(0, in.indexOf("`"));
        }

        if (in.contains("?")) {
            in = in.substring(0, in.indexOf("?"));
        }

        return in;
    }
}