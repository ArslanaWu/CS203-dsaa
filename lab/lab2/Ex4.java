import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Ex4 {
    static long[][] found;
    static int foundAmount = 0;
    static long[] array;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        long m = input.nextLong();
        array = new long[n];
        found = new long[2][n];

        for(int i = 0;i < n;i++){
            array[i] = input.nextLong();
        }

        find(m);

        System.out.print(foundAmount);
    }

    public static void find(long m){
        int left;
        int right;
        int length = array.length;
        int mid;

        int amount = find0();

        if(length == 2){
            if(array[0] * array[1] == m){
                foundAmount = 1;
                return;
            }else{
                return;
            }
        }

        if(m == 0){
            if(amount == 0){
                foundAmount = 0;
                return;
            }else{
                Set<Long> set = new TreeSet<>();
                for (long i : array) {
                    set.add(i);
                }

                if(amount == 1){
                    foundAmount = set.size() - 1;
                }else{
                    foundAmount = set.size();
                }
                return;
            }
        }

        for(int i = 0;i < length;i++){
            if(array[i] != 0){
                if(m % array[i] != 0){
                    continue;
                }
            }else{
                continue;
            }

            long num = m / array[i];

            left = 0;
            right = array.length - 1;

            while(left <= right){
                mid = (left + right)/2;

                if(num == array[mid] && mid != i){
                    foundAmount++;
                    found[0][foundAmount - 1] = Math.max(array[mid], array[i]);
                    found[1][foundAmount - 1] = Math.min(array[mid], array[i]);
                    break;
                }

                if(num > array[mid]){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
            }
        }

        delete();
    }

    public static int find0(){
        int length = array.length;
        int amount = 0;

        for(int i = 0;i < length;i++){
            if(array[i] == 0){
                amount++;
                if(i + 1 < length){
                    if(array[i + 1] > 0){
                        break;
                    }
                }
            }
        }
        return amount;
    }

    public static void delete(){
        Set<Long> set = new TreeSet<>();

        for(long i : found[0]){
            if(i != 0){
                set.add(i);
            }
        }
        foundAmount = set.size();
    }
}
