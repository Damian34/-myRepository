package operation;

public class StringChecker {

    public boolean cheackString(String str) {
        for (int i = 0; i < str.length(); i++) {
            int mark = str.charAt(i);
            boolean z1 = false, z2 = false, z3 = false;
            for (int j = 48; j <= 57; j++) {
                if (mark == j) {
                    z1 = true;
                    break;
                }
            }
            if (!z1) {
                for (int j = 65; j <= 90; j++) {
                    if (mark == j) {
                        z2 = true;
                        break;
                    }
                }
            }
            if (!z2) {
                for (int j = 97; j <= 122; j++) {
                    if (mark == j) {
                        z3 = true;
                        break;
                    }
                }
            }
            if (!(z1 || z2 || z3)) {
                return false;
            }
        }
        return true;
    }
}
