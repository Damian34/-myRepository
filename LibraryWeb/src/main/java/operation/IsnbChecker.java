package operation;

import org.apache.commons.lang3.StringUtils;

public class IsnbChecker {

    public boolean checkIsbn(String isbnCode) {
        return this.checkIsbn13(isbnCode) || this.checkIsbn10(isbnCode);
    }

    public boolean checkIsbn10(String isbnCode) {
        if (isbnCode == null) {
            return false;
        }
        String isbnCodeParse = isbnCode.replaceAll("( |-)", "");
        if (isbnCodeParse.length() != 10 || !StringUtils.isNumeric(isbnCodeParse)) {
            return false;
        }
        int controlSum = isbnCodeParse.charAt(9) - 48;
        int sum = 0;
        for (int i = 1; i < isbnCodeParse.length(); i++) {
            sum = sum + i * (isbnCodeParse.charAt(i - 1) - 48);
        }
        if (sum % 11 == controlSum) {
            return true;
        }
        return false;
    }

    public boolean checkIsbn13(String isbnCode) {
        if (isbnCode == null) {
            return false;
        }
        String isbnCodeParse = isbnCode.replaceAll("( |-)", "");
        if (isbnCodeParse.length() != 13 || !StringUtils.isNumeric(isbnCodeParse)) {
            return false;
        }
        int controlSum = isbnCodeParse.charAt(12) - 48;
        int sum = 0;
        for (int i = 0; i < isbnCodeParse.length() - 1; i++) {
            if (i % 2 == 0) {
                sum = sum + (isbnCodeParse.charAt(i) - 48);
            } else {
                sum = sum + 3 * (isbnCodeParse.charAt(i) - 48);
            }
        }
        int number = sum % 10;
        int result = 0;
        if (number != 0) {
            result = 10 - number;
        }
        if (result == controlSum) {
            return true;
        }
        return false;
    }
}
