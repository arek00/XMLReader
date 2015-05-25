package helpers;

import java.util.Iterator;

public class IteratorHelper {

    public static int length(Iterator<?> iterator) {
        int length = 0;

        while (iterator.hasNext()) {
            iterator.next();
            length++;
        }

        return length;
    }

}
