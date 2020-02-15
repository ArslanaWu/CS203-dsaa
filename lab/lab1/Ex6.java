import java.util.Scanner;

public class Ex6 {
    static String returnValue = " ";
    static double times = 0;
    static double[] array = new double[2];


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int num = input.nextInt();
        int[] people = new int[num];
        String[] result = new String[num];

        for(int i = 0;i < num;i++){
            people[i] = input.nextInt();
        }

        for(int i = 0;i < num;i++){ //每一个样例分别套用方法
            times = 0;
            double[] array = simplify(people[i]);
            result[i] = calculate(array[0]);
        }

        for(int i = 0;i < num;i++){
            System.out.println(result[i]);
        }
    }

    public static double[] simplify(double people){ //化简为2的n次方和奇数的乘积
        double a = people / Math.pow(2,times);
        if(a % 2 == 1){
            array[0] = a;
            array[1] = times - 1;
            return array;
        }else{
            times++;
            simplify(people);
        }
        return array;
    }

    public static String calculate(double people){ //返回自然数个数
        if(people == 1){
            returnValue = "impossible";
            return returnValue;
        }else{
            return String.valueOf(find());
        }
    }

    public static int find(){ //找奇数的最小奇因数的值=找最小的自然数个数
        int min;
        for(min = 3;min < array[0];min = min + 2){
            if(array[0] % min == 0){
                break;
            }
        }
        if(min > Math.pow(2,times + 1)){
            return (int)Math.pow(2,times + 1);
        }else{
            return min;
        }
    }
}
