import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Node5{
    int num;
    int color;
    ArrayList<Node5> children = new ArrayList<>();
    boolean isChecked = false;
}

public class Ex5 {
    static Node5[] location;
    static int[][] colorAmount;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n = input.nextInt();

            location = new Node5[n];
            colorAmount = new int[2][n];

            int[] color = new int[2];
            int result = 0;

            createTree(input,n,color);

            countRed(location[0]);

            for(int j = 0;j < n;j++){
                boolean red = colorAmount[0][j] == color[0] && colorAmount[1][j] == 0;
                boolean blue = colorAmount[1][j] ==color[1] && colorAmount[0][j] == 0;
                if(red || blue){
                    result++;
                }

            }
            //System.out.print(Arrays.toString(color));
            //System.out.print(Arrays.toString(colorAmount[0]) + Arrays.toString(colorAmount[1]));
            System.out.println(result);
        }
    }

    public static void createTree(Scanner input,int n,int[] color){
        for(int i = 0;i < n - 1;i++){
            int number1 = input.nextInt();
            int number2 = input.nextInt();
            Node5 tmp1,tmp2;

            if(location[number1 - 1] == null){
                tmp1 = new Node5();
                tmp1.num = number1;
                location[number1 - 1] = tmp1;
            }

            if(location[number2 - 1] == null){
                tmp2 = new Node5();
                tmp2.num = number2;
                location[number2 - 1] = tmp2;
            }

            location[number1 - 1].children.add(location[number2 - 1]);
            location[number2 - 1].children.add(location[number1 - 1]);
        }

        for(int i = 0;i < n;i++){
            location[i].color = input.nextInt();
            if(location[i].color == 1){
                color[0]++;
            }else if(location[i].color == 2){
                color[1]++;
            }
        }
    }

    public static void countRed(Node5 root){
        if(!root.isChecked){
            root.isChecked = true;

            if(root.children.size() != 0){
                for(int i = 0;i < root.children.size();i++){
                    countRed(root.children.get(i));
                }
            }

            if(root.color == 1){
                colorAmount[0][root.num - 1]++;
            }else if(root.color == 2){
                colorAmount[1][root.num - 1]++;
            }
            if(root.children.size() != 0){
                for(Node5 tmp : root.children){
                    colorAmount[0][root.num - 1] += colorAmount[0][tmp.num - 1];
                    colorAmount[1][root.num - 1] += colorAmount[1][tmp.num - 1];
                }
            }
        }
    }
}
