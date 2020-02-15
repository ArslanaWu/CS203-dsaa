import java.util.Scanner;

public class Ex5 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();
        int digit;
        long number;

        long max;
        long pointerFast;
        long pointerSlow;
        long count;

        for(int i = 0;i < t;i++){
            digit = input.nextInt();
            count = 0;
            max = number = pointerFast = pointerSlow = input.nextInt();

            number = square(digit,number);
            max = Math.max(max, number);

            pointerFast = square(digit,pointerFast);

            while(pointerFast != pointerSlow){
                number = square(digit,number);
                max = Math.max(max, number);

                pointerFast = square(digit,number);

                if(count % 2 == 0){
                    pointerSlow = square(digit,pointerSlow);
                    count++;
                }else{
                    count++;
                }
            }
        System.out.println(max);
        }
    }

    public static long square(int digit, long number){
        if(String.valueOf((long)Math.pow(number,2)).length() < digit){
            number = Integer.parseInt(String.valueOf((long)Math.pow(number,2)));
        }else{
            number = Integer.parseInt(String.valueOf((long)Math.pow(number,2)).substring(0,digit));
        }
        return number;
    }
}
