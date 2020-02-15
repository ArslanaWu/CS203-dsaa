import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        long[] numbers = new long[t];

        for(int i = 0;i < t;i++){
            numbers[i] = input.nextLong();
        }

        for(long num : numbers){
            System.out.println((num*num*num +3*num*num+2*num) / 6);
        }


    }
}
