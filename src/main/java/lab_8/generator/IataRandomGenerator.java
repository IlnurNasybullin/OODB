package lab_8.generator;

import airlines.userTypes.IATA;

import java.util.regex.Pattern;

public class IataRandomGenerator implements RandomInfinityGenerator<IATA> {

    private RandomInfinityGenerator<String> stringGenerator;

    public IataRandomGenerator() {
        this.stringGenerator = createGenerator();
    }

    private RandomInfinityGenerator<String> createGenerator() {
        StringRandomGenerator stringGenerator = new StringIdRandomGenerator(true);
        stringGenerator.setLength(3);
        stringGenerator.addPredicate(Pattern.compile(IATA.REGEX).asMatchPredicate());

        return stringGenerator;
    }

    public RandomInfinityGenerator<String> getStringGenerator() {
        return stringGenerator;
    }

    public void setStringGenerator(RandomInfinityGenerator<String> stringGenerator) {
        this.stringGenerator = stringGenerator;
    }

    @Override
    public IATA next() {
        return IATA.of(stringGenerator.next());
    }
}
