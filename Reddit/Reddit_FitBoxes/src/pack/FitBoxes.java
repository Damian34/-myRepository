package pack;

import java.util.ArrayList;
import java.util.List;

public class FitBoxes {

    public FitBoxes() {
    }
    
    public int fit(int X, int Y, int x, int y) {
        if (X <= 0 || Y <= 0 || x <= 0 || y <= 0) {
            return 0;
        }
        if (X < x || Y < y) {
            return 0;
        }
        return (int) (X / x) * (int) (Y / y);
    }

    public int fit2(int X, int Y, int x, int y) {
        if (X <= 0 || Y <= 0 || x <= 0 || y <= 0) {
            return 0;
        }
        if (Math.min(X, Y) < Math.min(x, y) || Math.max(X, Y) < Math.max(x, y)) {
            return 0;
        }
        int[] tab = {X % x, X % y, Y % x, Y % y};
        int min = tab[0], min_i = 0;
        for (int i = 1; i < tab.length; i++) {
            if (tab[i] < min) {
                min = tab[i];
                min_i = i;
            }
        }
        switch (min_i) {
            case 0:
                return (int) (X / x) * (int) (Y / y);
            case 1:
                return (int) (X / y) * (int) (Y / x);
            case 2:
                return (int) (X / y) * (int) (Y / x);
            case 3:
                return (int) (X / x) * (int) (Y / y);
        }
        return -1;
    }

    public int fit3(int[] Box, int x[]) {

        if (Box.length != x.length || Box.length <= 1 || x.length <= 1) {
            return 0;
        }
        for(int i=0;i<Box.length;i++){
            if(Box[i]<=0 || x[i]<=0){
                return 0;
            }
        }
        int[] Boxp = new int[Box.length];
        int[] xp = new int[Box.length];
        for (int i = 0; i < Boxp.length; i++) {
            Boxp[i] = Box[i];
            xp[i] = x[i];
        }
        sort(Boxp);
        sort(xp);
        for (int i = 0; i < Boxp.length; i++) {
            if (xp[i] > Boxp[i]) {
                return 0;
            }
        }
        int[] x2 = new int[x.length];
        List<Integer> list = toList(x);
        for (int i = 0; i < Box.length; i++) {
            int min = Box[i] % list.get(0), min_j = 0;
            for (int j = 1; j < list.size(); j++) {
                if (Box[i] % list.get(j) < min) {
                    min = Box[i] % list.get(j);
                    min_j = j;
                }
            }
            x2[i] = list.get(min_j);
            list.remove(min_j);
        }

        int sum = 1;
        for (int i = 0; i < Box.length; i++) {
            sum *= (int)(Box[i] / x2[i]);
        }
        return sum;
    }

    private void sort(int[] tab) {
        for (int i = 0; i < tab.length - 1; i++) {
            for (int j = 0; j < tab.length - i - 1; j++) {
                if (tab[j] < tab[j + 1]) {
                    int pom = tab[j];
                    tab[j] = tab[j + 1];
                    tab[j + 1] = pom;
                }
            }
        }
    }
    
    private List<Integer> toList(int[] tab){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < tab.length; i++) {
            list.add(tab[i]);
        }
        return list;
    }

}
