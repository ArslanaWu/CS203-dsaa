import java.util.Scanner;

public class Ex3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int m = input.nextInt();

        if(n >= 4){
            System.out.print(0);
        }else if(n == 2 || n == 1){
            System.out.print(n % m);
        }else if(n == 0){
            System.out.print(1 % m);
        }else{
            long result = 2 % m;
            for(int i = 3;i <= 720;i++){
                result = (result * (i % m)) % m;
            }
            System.out.print(result);
        }
    }
}
