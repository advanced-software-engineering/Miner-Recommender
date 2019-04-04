package ch.uzh.ifi.seal.ase19.core.utils;

public class SSTUtils {
    public static String getFullyQualifiedNameWithoutGenerics(String in) {
        if (in.contains("`")) {
            return in.substring(0, in.indexOf("`"));
        } else {
            return in;
        }
    }
}