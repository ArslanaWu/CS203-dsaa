import java.util.Scanner;

public class Ex4 {
    static long cost = 0;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        long[] array = new long[n];

        for(int i = 0;i < n;i++){
            array[i] = input.nextLong();
        }

        mergeSort(array,n);

        System.out.print(cost);

    }
    public static void mergeSort(long[] array,int length){
        int p;

        if(length > 1){
            p = length /2;

            long[] arrayA = new long[p];
            long[] arrayB = new long[length - p];

            for(int i = 0;i < p;i++){
                arrayA[i] = array[i];
            }
            for(int i = 0;i < length - p;i++){
                arrayB[i] = array[i + p];
            }

            mergeSort(arrayA,p);
            mergeSort(arrayB,length - p);

            long[] newArray = merge(arrayA,p,arrayB,length - p);

            for(int i = 0;i < newArray.length;i++){
                array[i] = newArray[i];
            }
        }
    }

    public static long[] merge(long[] arrayA,int lengthA,long[] arrayB,int lengthB){
        int length = lengthA + lengthB;
        long[] array = new long[length];
        int i = 0;
        int j = 0;

        for(int k = 0;k < length;k++){
            if(i < lengthA && (j >= lengthB || arrayA[i] <= arrayB[j])){
                array[k] = arrayA[i];
                i++;
            }else{
                array[k] = arrayB[j];
                cost = cost + (lengthA - i)*arrayB[j];
                for(int a = i;a < lengthA;a++){
                    cost = cost + arrayA[a];
                }
                j++;

            }
        }
        return array;
    }
}
