package operation;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class NumberChecker {

    public boolean checkNumber(String string) {
        return StringUtils.isNumeric(string);
    }

    public int check(String message) {
        if (StringUtils.isNumeric(message)) {
            return Integer.valueOf(message);
        }
        return -1;
    }

    public int check(int first, int last, String message) {
        if (StringUtils.isNumeric(message)) {
            int number = Integer.valueOf(message);
            if (number >= first && number <= last) {
                return number;
            }
        }
        return -1;
    }

    public int check(List<Integer> list, String message) {
        if (StringUtils.isNumeric(message)) {
            int toFind = Integer.valueOf(message);
            List<Integer> listFind = list.stream()
                    .filter(number -> number == toFind)
                    .collect(Collectors.toList());
            if (!listFind.isEmpty()) {
                return listFind.get(0);
            }
        }
        return -1;
    }
}
