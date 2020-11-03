package lab_6;

import java.awt.*;

public class Test {
    public static void main(String[] args) {
        Color green = new Color(16, 16, 16);
        System.out.println(Integer.toHexString(green.getRGB()).substring(2));
    }
}
