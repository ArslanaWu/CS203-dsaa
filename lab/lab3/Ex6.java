
import java.util.Scanner;

public class Ex6 {
    static int[][] equal;
    static int[][] aSmaller;
    static int[][] aBigger;
    static int equ;
    static int aSm;
    static int aBi;

    static int unit;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int j = 0;j < t;j++){
            int n = input.nextInt();

            equal = new int[2][n];
            aSmaller = new int[2][n];
            aBigger = new int[2][n];
            equ = 0;
            aSm = 0;
            aBi = 0;
            unit = 0;

            for(int i = 0;i < n;i++){
                int a = input.nextInt();
                int b = input.nextInt();

                if(a == b){
                    equal[0][equ] = a;
                    equal[1][equ] = b;
                    equ++;
                }else if(a > b){
                    aBigger[0][aBi] = a;
                    aBigger[1][aBi] = b;
                    aBi++;
                }else{
                    aSmaller[0][aSm] = a;
                    aSmaller[1][aSm] = b;
                    aSm++;
                }
            }

            //print();

            mergeSort(aSmaller,aSm,1);
            mergeSort(aBigger,aBi,-1);

            //print();

            eliminate();

            System.out.println(unit);
        }
    }

    public static void mergeSort(int[][] array,int length,int direction){
        int p;

        if(length > 1){
            p = length /2;

            int[][] arrayA = new int[2][p];
            int[][] arrayB = new int[2][length - p];

            for(int i = 0;i < p;i++){
                arrayA[0][i] = array[0][i];
                arrayA[1][i] = array[1][i];
            }
            for(int i = 0;i < length - p;i++){
                arrayB[0][i] = array[0][i + p];
                arrayB[1][i] = array[1][i + p];
            }

            mergeSort(arrayA,p,direction);
            mergeSort(arrayB,length - p,direction);

            int[][] newArray = merge(arrayA,p,arrayB,length - p,direction);

            if(direction == 1){
                for(int i = 0;i < newArray[0].length;i++){
                    array[0][i] = newArray[0][i];
                    array[1][i] = newArray[1][i];
                }
            }else{
                for(int i = 0;i < newArray[1].length;i++){
                    array[0][i] = newArray[0][i];
                    array[1][i] = newArray[1][i];
                }
            }


        }
    }

    public static int[][] merge(int[][] arrayA,int lengthA,int[][] arrayB,int lengthB,int direction){
        int length = lengthA + lengthB;
        int[][] array = new int[2][length];
        int i = 0;
        int j = 0;

        if(direction == 1){
            for(int k = 0;k < length;k++){
                if(i < lengthA && (j >= lengthB || arrayA[0][i] <= arrayB[0][j])){
                    array[0][k] = arrayA[0][i];
                    array[1][k] = arrayA[1][i];
                    i++;
                }else{
                    array[0][k] = arrayB[0][j];
                    array[1][k] = arrayB[1][j];
                    j++;
                }
            }
        }else{
            for(int k = 0;k < length;k++){
                if(i < lengthA && (j >= lengthB || arrayA[1][i] >= arrayB[1][j])){
                    array[1][k] = arrayA[1][i];
                    array[0][k] = arrayA[0][i];
                    i++;
                }else{
                    array[1][k] = arrayB[1][j];
                    array[0][k] = arrayB[0][j];
                    j++;
                }
            }
        }

        return array;
    }

    public static void eliminate(){
        int a = 0;//blue
        int b = 0;//red
        int length = aSm+aBi+equ;

        int[][] array = new int[2][length];
        for(int i = 0;i < aSm;i++){
            array[0][i] = aSmaller[0][i];
            array[1][i] = aSmaller[1][i];
        }
        for(int i = 0;i < equ;i++){
            array[0][i + aSm] = equal[0][i];
            array[1][i + aSm] = equal[1][i];
        }
        for(int i = 0;i < aBi;i++){
            array[0][i + aSm + equ] = aBigger[0][i];
            array[1][i + aSm + equ] = aBigger[1][i];
        }



        if(length > 1){
            a = array[0][0];
            b = array[1][0];

            for(int i = 1;i < length;i++){
                if(b > array[0][i]){
                    unit = unit + array[0][i];
                    b = b - array[0][i] + array[1][i];

                }else{
                    unit = unit + b;
                    a = a + array[0][i] - b;
                    b = array[1][i];
                }
            }
        }



    }

}
