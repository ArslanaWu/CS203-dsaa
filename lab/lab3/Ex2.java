import java.util.Scanner;

public class Ex2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int k = input.nextInt();
        int[] array = new int[n];

        for(int i = 0;i < n;i++){
            array[i] = input.nextInt();
        }

        mergeSort(array,n);

        System.out.print(array[k - 1]);
    }

    public static void mergeSort(int[] array,int length){
        int p;

        if(length > 1){
            p = length /2;

            int[] arrayA = new int[p];
            int[] arrayB = new int[length - p];

            for(int i = 0;i < p;i++){
                arrayA[i] = array[i];
            }
            for(int i = 0;i < length - p;i++){
                arrayB[i] = array[i + p];
            }

            mergeSort(arrayA,p);
            mergeSort(arrayB,length - p);

            int[] newArray = merge(arrayA,p,arrayB,length - p);

            for(int i = 0;i < newArray.length;i++){
                array[i] = newArray[i];
            }
        }
    }

    public static int[] merge(int[] arrayA,int lengthA,int[] arrayB,int lengthB){
        int length = lengthA + lengthB;
        int[] array = new int[length];
        int i = 0;
        int j = 0;

        for(int k = 0;k < length;k++){
            if(i < lengthA && (j >= lengthB || arrayA[i] <= arrayB[j])){
                array[k] = arrayA[i];
                i++;
            }else{
                array[k] = arrayB[j];
                j++;
            }
        }
        return array;
    }
}
