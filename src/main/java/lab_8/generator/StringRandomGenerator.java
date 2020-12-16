package lab_8.generator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

public class StringRandomGenerator implements RandomInfinityGenerator<String> {
    private final RandomInfinityGenerator<Character> alphabetGenerator;

    private int maxLength;
    private int minLength;
    private Integer length;

    private Set<Predicate<String>> predicates;

    public StringRandomGenerator(RandomInfinityGenerator<Character> alphabetGenerator) {
        this.alphabetGenerator = alphabetGenerator;
        this.maxLength = 15;
        this.minLength = 0;
        this.predicates = new HashSet<>();
    }

    public RandomInfinityGenerator<Character> getAlphabetGenerator() {
        return alphabetGenerator;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        checkLength(minLength, maxLength);
        this.maxLength = maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        checkLength(minLength, maxLength);
        this.minLength = minLength;
    }

    private void checkLength(int minLength, int maxLength) {
        if (maxLength <= minLength) {
            throw new IllegalArgumentException("maxLength must be more than minLength!");
        }
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        if (length != null && length < 0) {
            throw new IllegalArgumentException("length can't be negative number!");
        }
        this.length = length;
    }

    public boolean addPredicate(Predicate<String> predicate) {
        if (predicate != null) {
            return predicates.add(predicate);
        }

        return false;
    }

    @Override
    public String next() {
        Random random = new Random();
        String word;
        do {
            int length = this.length == null ? minLength + random.nextInt(maxLength - minLength) : this.length;
            StringBuffer buffer = new StringBuffer();
            for (Character character: alphabetGenerator.next(length)) {
                buffer.append(character);
            }
            word = buffer.toString();
        } while (!isMatching(word));

        return word;
    }

    private boolean isMatching(String word) {
        Iterator<Predicate<String>> iterator = predicates.iterator();
        boolean isMatching = true;
        while (isMatching && iterator.hasNext()) {
            isMatching = iterator.next().test(word);
        }

        return isMatching;
    }
}
