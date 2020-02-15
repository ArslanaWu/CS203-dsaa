import java.util.Scanner;

public class Ex4 {
    static int[] magic = {10,9,90,90,900,900,9000,9000,90000,90000,900000};

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        int num = input.nextInt();
        long[] numbers = new long[num];

        for(int i = 0;i < num;i++){
            numbers[i] = input.nextLong();
        }

        for(int i = 0;i < num;i++){
            System.out.println(find(numbers[i]));
        }
    }

    public static long find(long number){
        int times = 0;
        long magicAmount = 0;

        while(number / Math.pow(10,times) >= 1){
            times++;
        }
        int[] splitNumber = new int[times];
        long numberCopy = number;
        int timesCopy = times;
        for(int i = 0;i < splitNumber.length;i++){
            splitNumber[i] = (int)(numberCopy / Math.pow(10,timesCopy - 1));
            numberCopy = numberCopy - (long)(splitNumber[i] * Math.pow(10,timesCopy - 1));
            timesCopy--;
        }

        if(times % 2 == 0){
            for(int i = 0;i < times / 2;i++){
                if(splitNumber[0] > 1 && i == 0){
                    magicAmount = magicAmount + (long)((splitNumber[i] - 1) * Math.pow(10,(times / 2) - i - 1));
                }else if(i > 0){
                    if(splitNumber[i] != 0){
                        magicAmount = magicAmount + (long)(splitNumber[i] * Math.pow(10,(times / 2) - i - 1));
                    }
                }
            }
            if(number != 0){
                for(int i = 0;i < times - 1;i++){
                    magicAmount = magicAmount + magic[i];
                }
            }

            long maxMagic = 0;
            for(int i = 0;i < times;i++){
                if(i >= times / 2){
                    maxMagic = maxMagic + (long)(splitNumber[times - 1 - i] * Math.pow(10,times - i - 1));
                }else{
                    maxMagic = maxMagic + (long)(splitNumber[i] * Math.pow(10,times - i - 1));
                }
            }
            if(maxMagic <= number){
                magicAmount++;
            }
        }else{
            for(int i = 0;i < (times - 1) / 2;i++){
                if(splitNumber[0] > 1 && i == 0){
                    magicAmount = magicAmount + 10 * (long)((splitNumber[i] - 1) * Math.pow(10,((times - 1) / 2) - i - 1));
                }else if(i > 0){
                    if(splitNumber[i] != 0){
                        magicAmount = magicAmount + 10 * (long)(splitNumber[i] * Math.pow(10,((times - 1) / 2) - i - 1));
                    }
                }
            }
            if(number > 10){
                magicAmount = magicAmount + splitNumber[(times - 1) / 2];
            }


            if(number < 10){
                magicAmount = magicAmount + number;
            }else{
                for(int i = 0;i < times - 1;i++){
                    magicAmount = magicAmount + magic[i];
                }
            }

            long maxMagic = 0;
            for(int i = 0;i < times;i++){
                if(i > (times - 1) / 2){
                    maxMagic = maxMagic + (long)(splitNumber[times - 1 - i] * Math.pow(10,times - i - 1));
                }else if(i <= (times - 1) / 2){
                    maxMagic = maxMagic + (long)(splitNumber[i] * Math.pow(10,times - i - 1));
                }
            }
            if(maxMagic <= number){
                magicAmount++;
            }
        }
        return magicAmount;
    }
}
