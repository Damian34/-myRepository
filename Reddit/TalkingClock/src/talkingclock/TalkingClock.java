package talkingclock;

public class TalkingClock {

    public void talk(String time) {
        int hour = Integer.valueOf(time.split(":")[0]);
        int min = Integer.valueOf(time.split(":")[1]);
        if (!(hour >= 0 && hour < 24 && min >= 0 && min < 60)) {
            System.out.println("wrong time");
            return;
        }
        String[] number = {"one ", "two ", "three ", "four ", "five ", "six ", "seven ", "eight ", "nine "};
        String[] number2 = {"eleven ", "twelve ", "thirteen ", "fourteen ", "fifteen ", "sixteen ", "seventeen ", "eighteen ", "nineteen "};
        String[] tens = {"oh ", "ten ", "twenty ", "thirty ", "fourty ", "fifty "};
        int hour2 = 12;
        if (hour != 12) {
            hour2 = hour % 12;
        }
        if (hour2 < 10) {
            System.out.print(number[hour - 1]);
        }
        if (hour2 == 10) {
            System.out.print(tens[1]);
        }
        if (hour2 > 10) {
            System.out.print(number2[hour2 - 11]);
        }
        System.out.print("hour ");
        if (min > 10 && min < 20) {
            System.out.print(number2[min - 11]);
        } else {
            System.out.print(tens[min / 10]);
            System.out.print(number[min % 10 - 1]);
        }
        if (hour < 12) {
            System.out.print("a.m.");
        } else {
            System.out.print("p.m.");
        }
        System.out.println();
    }
}
