import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();
        input.nextLine();

        for(int i = 0;i < t;i++){
            String a = input.nextLine();
            int length = a.length();
            System.out.println((length * (length + 1))/2);
        }
    }
}
