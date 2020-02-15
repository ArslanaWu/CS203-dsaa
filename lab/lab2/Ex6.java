import java.util.Scanner;

public class Ex6 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();
        long[] numbers = new long[t];

        for(int i = 0;i < t;i++){
            numbers[i] = input.nextLong();
        }
        for(int i = 0;i < t;i++){
            System.out.println(calculate(numbers[i]));
        }
    }

    public static long calculate(long numbers){
        long number = 0;

        while(numbers >= 5){
            numbers = numbers / 5;
            number = number + numbers;
        }
        return number;
    }
}
