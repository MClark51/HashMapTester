import java.util.Comparator;

public class StringComparator implements Comparator<String>{

    public int compare(String s1, String s2) {
        String s1Sub = s1.substring(0, 3);
        String s2Sub = s2.substring(0, 3);

        return s1Sub.compareTo(s2Sub);
    }
}
