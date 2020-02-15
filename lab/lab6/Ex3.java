import java.util.Arrays;
import java.util.Scanner;

public class Ex3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();

        int number = 0;

        for(int i = 0;i < n;i++){
            String s = input.next();
            String p = input.next();
            int oneThird = (int)Math.ceil((double)s.length() / 3);
            //System.out.println(oneThird);

            if(KMP(s.substring(0,oneThird),p) != -1){
                number++;
            }
        }

        System.out.print(number);
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

    public static int KMP(String p, String t){
        int[] next = nextArray(p);

        int m = p.length();
        int n = t.length();
        int k = -1;

        for(int i = 0;i < n;i++){
            while(k > -1 && p.charAt(k + 1) != t.charAt(i)){
                k = next[k];
            }
            if(p.charAt(k + 1) == t.charAt(i)){
                k++;
            }

            if(k == m - 1){
                return i - m + 1;
            }
        }
        return -1;
    }


}
