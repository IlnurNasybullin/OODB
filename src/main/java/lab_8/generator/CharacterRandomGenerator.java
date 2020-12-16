package lab_8.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CharacterRandomGenerator implements RandomInfinityGenerator<Character> {

    private Character start;
    private Character end;

    private List<Character> symbols;

    public CharacterRandomGenerator() {
        this.symbols = new ArrayList<>();
        this.start = Character.MIN_VALUE;
        this.end = Character.MAX_VALUE;
    }

    public Character getStart() {
        return start;
    }

    public void setStart(char start) {
        checkRange(start, end);
        this.start = start;
    }

    private void checkRange(char start, Character end) {
        if (start >= end) {
            throw new IllegalArgumentException(String.format("start symbol code must be less, than end symbol code"));
        }
    }

    public Character getEnd() {
        return end;
    }

    public void setEnd(char end) {
        checkRange(start, end);
        this.end = end;
    }

    public boolean addSymbol(char symbol) {
        return this.symbols.add(symbol);
    }

    public boolean addSymbols(Collection<Character> symbols) {
        return this.symbols.addAll(List.copyOf(symbols));
    }

    @Override
    public Character next() {
        char range = (char)( end - start);
        char size = (char) (range + symbols.size());
        char value = (char) new Random().nextInt(size);
        return value < range ? (char) (start + value) : symbols.get(value - range);
    }
}
