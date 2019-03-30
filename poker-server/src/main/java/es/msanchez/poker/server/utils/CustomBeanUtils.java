package es.msanchez.poker.server.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author msanchez
 * @since 30.03.2019
 */
public final class CustomBeanUtils {

    private CustomBeanUtils() {
        // only static usage
    }

    // TODO: DOC & Testing
    public static <T> List<T> deepCopy(final List<T> src,
                                       final Function<T, T> constructor) {
        return src.stream()
                .map(constructor)
                .collect(Collectors.toList());
    }

}
