package rabbits;

public class Rabbits {

    public static void main(String[] args) {
        try {
            Population population = new Population();
            for (int i = 0; i < 36; i++) {
                System.out.print((i + 1) + ". month | ");
                population.TimeIsGoingByMonth();
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError");
        }
    }
}
