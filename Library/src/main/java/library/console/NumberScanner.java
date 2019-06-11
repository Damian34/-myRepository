package library.console;

import java.util.List;
import java.util.Scanner;
import operation.NumberChecker;

public class NumberScanner {

    private NumberChecker checkNumber = new NumberChecker();
    private final Scanner scanner = new Scanner(System.in);

    public int check() {
        int number;
        while (true) {
            number = this.checkNumber.check(scanner.nextLine());
            if (number != -1) {
                break;
            }
        }
        return number;
    }

    public int check(int first, int last) {
        int number;
        while (true) {
            number = this.checkNumber.check(first, last, scanner.nextLine());
            if (number != -1) {
                break;
            }
        }
        return number;
    }

    public int check(List<Integer> list) {
        int number;
        while (true) {
            number = this.checkNumber.check(list, scanner.nextLine());
            if (number != -1) {
                break;
            }
        }
        return number;
    }
}
