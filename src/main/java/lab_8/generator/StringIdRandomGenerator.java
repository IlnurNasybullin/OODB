package lab_8.generator;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringIdRandomGenerator extends StringRandomGenerator {

    public StringIdRandomGenerator(boolean onlyUpperCase) {
        this(getAlphabet(onlyUpperCase));
    }

    private static RandomInfinityGenerator<Character> getAlphabet(boolean onlyUpperCase) {
        CharacterRandomGenerator symbolsGenerator = new CharacterRandomGenerator();
        symbolsGenerator.setStart('A');
        symbolsGenerator.setEnd((char) ('Z' + 1));

        if (!onlyUpperCase) {
            symbolsGenerator.addSymbols(Stream.iterate('a', symbol -> symbol <= 'z' ,
                    symbol ->  (char)(symbol + 1)).collect(Collectors.toUnmodifiableList()));
        }

        symbolsGenerator.addSymbols(Stream.iterate('0', symbol -> symbol <= '9' ,
                symbol ->  (char)(symbol + 1)).collect(Collectors.toUnmodifiableList()));

        return symbolsGenerator;
    }

    public StringIdRandomGenerator(RandomInfinityGenerator<Character> alphabetGenerator) {
        super(alphabetGenerator);
    }
}
