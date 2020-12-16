package lab_8.generator;

public class NameStringRandomGenerator extends StringRandomGenerator {

    public NameStringRandomGenerator() {
        this(getRandomCharacterGenerator());
        setMinLength(5);
        setMaxLength(15);
    }

    private static RandomInfinityGenerator<Character> getRandomCharacterGenerator() {
        CharacterRandomGenerator randomGenerator = new CharacterRandomGenerator();
        randomGenerator.setStart('a');
        randomGenerator.setEnd('z');

        return randomGenerator;
    }

    public NameStringRandomGenerator(RandomInfinityGenerator<Character> alphabetGenerator) {
        super(alphabetGenerator);
    }

    @Override
    public String next() {
        String next = super.next();
        char[] chars = next.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);

        return new String(chars);
    }
}
