import java.util.Scanner;

class Node1{
    int number;
    Node1 next;
    Node1 pre;

    Node1(int number){
        this.number = number;
    }
}

public class Ex1 {
    static Node1 first;
    static Node1 last;
    static Node1 current;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();

        first = last = new Node1(0);

        for(int i = 0;i < n;i++){
            String type = input.next();
            if(type.equals("E")){
                int number = input.nextInt();

                Node1 tmp = new Node1(number);
                tmp.pre = last;
                last.next = tmp;
                last = tmp;
            }else if(type.equals("D")){
                if(last != first){
                    if(first.next == last){
                        first = last = new Node1(0);
                    }else{
                        first.next.next.pre = first;
                        first.next = first.next.next;
                    }
                }
            }else if(type.equals("A")){
                if(first.next != null){
                    System.out.println(first.next.number);
                }else{
                    System.out.println();
                }
            }
        }
        print();
    }

    public static void print(){
        current = first.next;
        while(current != null){
            System.out.print(current.number);
            if(current.next != null){
                System.out.print(" ");
            }
            current = current.next;
        }
    }
}
