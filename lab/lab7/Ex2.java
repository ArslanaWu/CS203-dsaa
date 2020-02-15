import java.util.ArrayList;
import java.util.Scanner;

class Node2 {
    int num;
    ArrayList<Node2> children = new ArrayList<>();
    boolean isChecked = false;
}

public class Ex2 {
    static Node2[] location;
    static int[] depths;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n = input.nextInt();

            location = new Node2[n];
            depths = new int[n];

            createTree(input,n);

            findDepth(location[0],0);

            for(int j = 0;j < n;j++){
                System.out.print(depths[j]);
                if(j < n - 1){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static void createTree(Scanner input,int n){
        for(int i = 0;i < n - 1;i++){
            int number1 = input.nextInt();
            int number2 = input.nextInt();
            Node2 tmp1,tmp2;

            if(location[number1 - 1] == null){
                tmp1 = new Node2();
                tmp1.num = number1;
                location[number1 - 1] = tmp1;
            }

            if(location[number2 - 1] == null){
                tmp2 = new Node2();
                tmp2.num = number2;
                location[number2 - 1] = tmp2;
            }

            location[number1 - 1].children.add(location[number2 - 1]);
            location[number2 - 1].children.add(location[number1 - 1]);
        }
    }

    public static void findDepth(Node2 root, int depth){
        if(!root.isChecked){
            depths[root.num - 1] = depth;
            root.isChecked = true;

            if(root.children.size() != 0){
                for(int i = 0;i < root.children.size();i++){
                    depth++;
                    findDepth(root.children.get(i),depth);
                    depth--;
                }
            }
        }
    }
}
