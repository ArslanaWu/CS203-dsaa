import java.util.Scanner;

public class Ex3 {
    static long amount = 0;//满足条件的三个数的数量
    static int[] presentAmount;//存每个数出现的个数

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        long m = input.nextLong();
        presentAmount = new int[n];
        long[] array = new long[n];

        for(int i = 0;i < n;i++){
            array[i] = input.nextLong();
        }

        mergeSort(array,n);

        count(array);//排序

        find(array,m);

        System.out.print(amount);
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
                j++;
            }
        }
        return array;
    }

    public static void find(long[] array,long m){
        int length = array.length;
        long sum;
        int left;
        int right;
        long num;
        int mid;

        for(int i = 0;i < length;i++){
            if(i == 0 || array[i] != array[i-1]){
                if(array[i] * 3 == m && presentAmount[i] > 2){
                    amount = amount + c(presentAmount[i],3);
                    return;
                }
            }//aaa

            sum = m - array[i];

            for(int j = i + 1;j < length;j++){
                if(array[i] != array[j] || array[j] * 3 != m){//排除aaa可能

                    if(array[j] * 2 == sum && presentAmount[j] > 1){
                            amount = amount + c(presentAmount[j],2);
                            break;
                        //abb情况
                    }else{
                        num = sum - array[j];
                        left = j + 1;
                        right = length - 1;

                        while(left <= right){
                            mid = (left + right)/2;

                            if(num == array[mid] && mid != i && mid != j){
                                amount = amount + presentAmount[mid];//abc，加c的数量个
                                break;
                            }

                            if((num > array[mid])){
                                left = mid + 1;
                            }else{
                                right = mid - 1;
                            }
                        }
                    }
                }
            }
        }
    }//aaa abb abc情况

    public static void count(long[] array){
        for(int i = 0;i < array.length;i++){
            for(int j = 0;j < array.length;j++){
                if(array[i] == array[j]){
                    presentAmount[i]++;
                }
            }
        }
    }//计算每个数出现的次数

    public static long c(int down,int up){
        long num = 1;
        for(int i = 0;i < up;i++){
            num = num * (down - i);
        }
        for(int i = 0;i < up;i++){
            num = num / (up - i);
        }
        return num;
    }//计算组合，就CA/2那种
}
