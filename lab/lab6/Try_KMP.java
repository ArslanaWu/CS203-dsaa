import java.util.Arrays;
import java.util.Scanner;

public class Try_KMP {
    //static long[] pow;
    //static int[] next;

    public static void main(String[] args) {
        String p = "aabaaabb";//t中找p
        String t = "abababacaba";

        int result = KMP(p,t);

        System.out.print(result);
    }

    public static int[] nextArray(String p){
        int m = p.length();
        int[] next = new int[m];
        int k =-1;
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
        System.out.println(Arrays.toString(next));
        return next;
    }

    public static int KMP(String p, String t){
        int[] next = nextArray(p);

        int m = p.length();
        int n = t.length();
        int k = -1;

        for(int i = 0;i < n;i++){
            while(k != -1 && p.charAt(k + 1) != t.charAt(i)){
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

    public static int KMP1(String p, String t){
        int m = p.length();
        int n = t.length();
        int q = 0;

        int[] next = nextArray(p);

        for(int i = 0;i < n;i++){
            while(q < m && p.charAt(q) == t.charAt(i)){
                q++;
                i++;
                if(i == n - 1 && q < m - 1){
                    return -1;
                }
            }
            if(q == m){
                return i - q;
            }else if(q == 0){
                i++;
            }else{
                i = i + next[q - 1] - q - 1;
                q = 0;
            }
        }
        return -1;

    }

    public static int[] nextArray1(String p){
        int x = 0;
        int y = -1;
        int length = p.length();
        int[] next = new int[length];

        while(x < length){
            if(y == -1 || p.charAt(x) == p.charAt(y)){
                ++x;
                ++y;
                next[x] = y;
            }else{
                y = next[y];
            }
        }

        System.out.println(Arrays.toString(next));
        return next;
    }


}
