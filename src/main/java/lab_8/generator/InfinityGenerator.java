package lab_8.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@FunctionalInterface
public interface InfinityGenerator<T> extends Generator<T> {

    @Override
    default boolean hasNext() {
        return true;
    }

    default List<T> next(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count is negative number!");
        }

        if (count == 0) {
            return Collections.emptyList();
        }

        List<T> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(next());
        }

        return list;
    }
}
