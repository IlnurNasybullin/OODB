import java.util.Formatter;

public class Test {
    public static void main(String[] args) {
        Formatter formatter = new Formatter();

        String str ="%s.%s";

        String a = String.format(str, "b", "c");
        System.out.println(a);
    }
}
