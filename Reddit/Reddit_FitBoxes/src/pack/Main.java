package pack;

// https://www.reddit.com/r/dailyprogrammer/comments/bazy5j/20190408_challenge_377_easy_axisaligned_crate/

public class Main {

    public static void main(String[] args) {
        FitBoxes box = new FitBoxes();
        
        int[] X = {3, 4};
        int[] Y = {1, 2};
        System.out.println(box.fit3(X,Y));
        
        int[] X2 = {123, 456, 789};
        int[] Y2 = {10, 11, 12};
        System.out.println(box.fit3(X2,Y2));
        
        int[] X3 = {123, 456, 789, 1011, 1213, 1415};
        int[] Y3 = {16, 17, 18, 19, 20, 21};
        System.out.println(box.fit3(X3,Y3));
        
        int[] X4 = {180598, 125683, 146932, 158296, 171997, 204683, 193694, 216231, 177673, 169317, 216456, 220003, 165939, 205613, 152779, 177216, 128838, 126894, 210076, 148407};
        int[] Y4 = {1984, 2122, 1760, 2059, 1278, 2017, 1443, 2223, 2169, 1502, 1274, 1740, 1740, 1768, 1295, 1916, 2249, 2036, 1886, 2010};
        System.out.println(box.fit3(X4,Y4));//to large for int format , BigDecimal class is needed here
    }
}
