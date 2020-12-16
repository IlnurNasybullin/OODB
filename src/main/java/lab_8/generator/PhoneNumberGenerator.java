package lab_8.generator;

import java.util.Random;

public class PhoneNumberGenerator implements RandomInfinityGenerator<String> {
    @Override
    public String next() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer("+79");
        buffer.append(random.nextInt(999_999_999 - 100_000_000) + 100_000_000);
        return buffer.toString();
    }
}
