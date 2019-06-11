package rabbits;

public class Rabbit {

    private long months = 0;
    private long number = 0;

    public Rabbit(long number) {
        this.number = number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getNumber() {
        return number;
    }

    public long getMonths() {
        return months;
    }

    public void increaseMonths() {
        this.months++;
    }

}
