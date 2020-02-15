import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n = input.nextInt();
            int[] array = new int[n];
            for(int j = 0;j < n;j++){
                array[j] = input.nextInt();
            }

            int count = sort(array);

            if(count != 0){
                System.out.println("wa");
            }else{
                System.out.println(array[2]);
            }
        }
    }

    public static int sort(int[] array){
        int length = array.length;
        int count = 0;

        for(int i = 0;i < length - 1;i++){
            int k = i;
            for(int j = i + 1;j < length;j++){
                if(array[k] < array[j]){
                    k = j;
                }
            }
            int swap = array[i];
            array[i] = array[k];
            array[k] = swap;
        }

        for(int i = 0;i < length;i++){
            if(array[2] == array[i] && i != 2){
                count++;
            }
        }
        return count;
    }

}
