import java.util.Scanner;

public class Ex2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int days = input.nextInt();
        int kinds = input.nextInt();
        int[] money = new int[days];
        int[] price = new int[kinds];

        for(int i = 0;i < days;i++){
            money[i] = input.nextInt();
        }
        for(int i = 0;i < kinds;i++){
            price[i] = input.nextInt();
        }

        for(int i = 0;i < days;i++){
            int buy = find(money[i],price);
            if(buy == money[i]){
                System.out.println("Meow");
            }else if(buy < money[i]){
                System.out.println(money[i] - buy);
            }else{
                System.out.println(money[i]);
            }
        }

    }
    public static int find(int money,int[] price){
        int left = 0;
        int right = price.length - 1;
        int mid;

        while(left <= right){
            mid = (left + right)/2;
            if(left != price.length - 1 && price[left] < money && price[left + 1] > money){
                return price[left];
            }else if(left != 0 && price[left] > money && price[left - 1] < money){
                return price[left - 1];
            }else if(left == right){
                return price[left];
            }

            if(price[mid] == money){
                return price[mid];
            }

            if(price[mid] > money){
                if(mid == 0){
                    right = 0;
                }else{
                    right = mid - 1;
                }
            }else{
                if(mid == price.length - 1){
                    left = price.length - 1;
                }else{
                    left = mid + 1;
                }
            }
        }
        return 0;
    }
}
