import java.util.Scanner;

class Node2{
    char bracket;
    Node2 next;
    Node2 pre;

    Node2(char bracket){
        this.bracket = bracket;
    }
}

public class Ex2 {
    static Node2 first;
    static Node2 last;
    static Node2 current;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();


        for(int i = 0;i < t;i++){
            boolean isMatched = true;

            first = last = new Node2('0');

            int n = input.nextInt();
            input.nextLine();
            char[] brackets = input.nextLine().toCharArray();

            for(int j = 0;j < n;j++){
                if(brackets[j] == '(' || brackets[j] == '[' || brackets[j] == '{'){
                    Node2 tmp = new Node2(brackets[j]);

                    tmp.pre = last;
                    last.next = tmp;
                    last = tmp;
                }else{
                    if(first.bracket == last.bracket){
                        isMatched = false;
                        break;
                    }

                    char match = '1';
                    if(brackets[j] == ')'){
                        match = '(';
                    }else if(brackets[j] == ']'){
                        match = '[';
                    }else if(brackets[j] == '}'){
                        match = '{';
                    }

                    if(match == last.bracket){
                        last = last.pre;
                        last.next = null;

                    }else{
                        isMatched = false;
                        break;
                    }
                }
            }
            if(first.bracket != last.bracket){
                isMatched = false;
            }
            if(isMatched){
                System.out.println("YES");
            }else{
                System.out.println("NO");
            }
        }
    }

    public static void print(){
        current = first;
        while(current != null){
            System.out.print(current.bracket);
            current = current.next;
        }
    }
}
