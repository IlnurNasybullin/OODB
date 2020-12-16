package lab_8.generator;

import airlines.userTypes.AirportICAO;

import java.util.regex.Pattern;

public class IcaoRandomGenerator implements RandomInfinityGenerator<AirportICAO> {

    private RandomInfinityGenerator<String> stringGenerator;

    public IcaoRandomGenerator() {
        this.stringGenerator = createGenerator();
    }

    private RandomInfinityGenerator<String> createGenerator() {
        StringRandomGenerator stringGenerator = new StringIdRandomGenerator(true);
        stringGenerator.setLength(4);
        stringGenerator.addPredicate(Pattern.compile(AirportICAO.REGEX).asMatchPredicate());

        return stringGenerator;
    }

    public RandomInfinityGenerator<String> getStringGenerator() {
        return stringGenerator;
    }

    public void setStringGenerator(RandomInfinityGenerator<String> stringGenerator) {
        this.stringGenerator = stringGenerator;
    }

    @Override
    public AirportICAO next() {
        return AirportICAO.of(stringGenerator.next());
    }
}
