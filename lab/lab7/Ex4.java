import java.util.ArrayList;
import java.util.Scanner;

public class Ex4 {
    static ArrayList<Long> lengths;
    static long result;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n = input.nextInt();

            result = 0;
            lengths = new ArrayList<>();
            for(int j = 0;j < n;j++){
                lengths.add(input.nextLong());
            }

            cut();

            System.out.println(result);
        }
    }

    public static void cut(){
        if(lengths.size() > 1){
            mergeSort(lengths,lengths.size());
            long add = lengths.get(0) + lengths.get(1);
            result += add;
            lengths.remove(0);
            lengths.remove(0);
            lengths.add(add);
            cut();
        }
    }

    public static void mergeSort(ArrayList<Long> array,int length){
        int p;

        if(length > 1){
            p = length /2;

            ArrayList<Long> arrayA = new ArrayList<>();
            ArrayList<Long> arrayB = new ArrayList<>();

            for(int i = 0;i < p;i++){
                arrayA.add(array.get(i));
            }
            for(int i = 0;i < length - p;i++){
                arrayB.add(array.get(i + p));
            }

            mergeSort(arrayA,p);
            mergeSort(arrayB,length - p);

            ArrayList<Long> newArray = merge(arrayA,p,arrayB,length - p);

            for(int i = 0;i < newArray.size();i++){
                array.set(i,newArray.get(i));
            }
        }
    }

    public static ArrayList<Long> merge(ArrayList<Long> arrayA,int lengthA,ArrayList<Long> arrayB,int lengthB){
        int length = lengthA + lengthB;
        ArrayList<Long> array = new ArrayList<>();
        int i = 0;
        int j = 0;

        for(int k = 0;k < length;k++){
            if(i < lengthA && (j >= lengthB || arrayA.get(i) <= arrayB.get(j))){
                array.add(arrayA.get(i));
                i++;
            }else{
                array.add(arrayB.get(j));
                j++;
            }
        }
        return array;
    }

    public static void print(){
        for(int i = 0;i < lengths.size();i++){
            System.out.print(lengths.get(i) + " ");
        }
        System.out.println();
    }
}
