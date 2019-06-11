package rabbits;

import java.util.ArrayList;
import java.util.List;

public class Population {

    List<Rabbit> male = new ArrayList<>();
    List<Rabbit> female = new ArrayList<>();
    public long deathMale = 0;
    public long deathFemale = 0;

    public Population() {
        male.add(new Rabbit(1));
        female.add(new Rabbit(1));
    }

    public void TimeIsGoingByMonth() {
        long produce = 0;
        if (!(male.isEmpty() && female.isEmpty())) {
            for (int i = female.size() - 1; i >= 0; i--) {
                female.get(i).increaseMonths();
                if (female.get(i).getMonths() >= 4) {
                    produce += female.get(i).getNumber();
                }
                if (female.get(i).getMonths() >= 96) {
                    this.deathFemale += female.get(i).getNumber();
                    female.remove(i);
                }
            }
            for (int i = male.size() - 1; i >= 0; i--) {
                male.get(i).increaseMonths();
                if (male.get(i).getMonths() >= 96) {
                    this.deathMale += male.get(i).getNumber();
                    male.remove(i);
                }
            }
            male.add(new Rabbit(produce * 5));
            female.add(new Rabbit(produce * 9));
            System.out.println("rabbits: female = " + getNumber(female) + " deathFemale = " + deathFemale + " | male = " + getNumber(male) + " deathMale = " + deathMale);
        }
    }

    private long getNumber(List<Rabbit> list) {
        long number = 0;
        for (int i = 0; i < list.size(); i++) {
            number += list.get(i).getNumber();
        }
        return number;
    }
}
