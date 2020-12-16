package lab_8.generator;

import java.util.Random;

public class PassportIdGenerator implements RandomInfinityGenerator<String> {

    @Override
    public String next() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        buffer.append(random.nextInt(9999 - 1000) + 1000);
        buffer.append(' ');
        buffer.append(random.nextInt(999_999 - 100_000) + 100_000);

        return buffer.toString();
    }
}
