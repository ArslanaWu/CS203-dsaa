import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            long n = input.nextLong();
            int k = input.nextInt();
            long aboveAmount = 0;
            int aboveHeight = 0;

            while(aboveAmount + (int)Math.pow(k,aboveHeight) < n){
                aboveAmount += (int)Math.pow(k,aboveHeight);
                aboveHeight++;
            }

            double a = ((double)(n - aboveAmount))/(double)k;
            a = Math.ceil(a);

            long result = n - aboveAmount + (int)Math.pow(k,aboveHeight - 1) - (long)a;

            System.out.println(result);
        }
    }
}
