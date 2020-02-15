import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int num = input.nextInt();
        String[] center = new String[num];

        for(int i = 0;i < num;i++){
            input.nextLine();
            input.nextLine();
            center[i] = input.nextLine().substring(2,3);
            input.nextLine();
        }

        System.out.println();

        for(int i = 0;i<center.length;i++){

            System.out.println(center[i]);
        }
    }
}
