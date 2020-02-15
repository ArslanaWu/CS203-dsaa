import java.util.Scanner;
import java.util.Arrays;

public class Ex4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();

        for(int i = 0;i < n;i++){
            String string = input.next();

            int[] next = nextArray(string);
            //System.out.println(Arrays.toString(next));

            int len = string.length();
            int d = next[len - 1];

            if(d == -1){
                System.out.println(len);
            }else if(len % (len - d - 1) == 0){
                System.out.println(0);
            }else{
                int cycle = len - d - 1;
                System.out.println(cycle - (d + 1) % cycle);
            }
        }
    }

    public static int[] nextArray(String p){
        int m = p.length();
        int[] next = new int[m];
        int k = -1;
        next[0] = -1;

        for(int i = 1;i < m;i++){
            while(k > -1 && p.charAt(k + 1) != p.charAt(i)){
                k = next[k];
            }
            if(p.charAt(k + 1) == p.charAt(i)){
                k = k + 1;
            }
            next[i] = k;
        }
        //System.out.println(Arrays.toString(next));
        return next;
    }
}
