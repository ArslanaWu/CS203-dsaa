import java.util.Scanner;

class Node3{
    int number;
    int order;
    Node3 pre;
    Node3 next;

    Node3(int number,int order){
        this.number = number;
        this.order = order;
    }
}

public class Ex3 {
    static Node3 first;
    static Node3 last;
    static Node3 current;

    static Node3 resultFirst;
    static Node3 resultLast;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int magicNumber = input.nextInt();

        first = last = new Node3(1000000001,0);
        resultFirst = resultLast = new Node3(-101,0);
        current = first;
        int order = 1;

        while(true){
            int number = input.nextInt();

            if(number == -1){
                break;
            }

            if(number <= last.number){
                Node3 tmp = new Node3(number,order);

                tmp.pre = last;
                last.next = tmp;
                last = tmp;
                //print();
            }else{
                while(number > last.number){
                    last = last.pre;
                    last.next = null;
                    //print();
                }

                Node3 tmp = new Node3(number,order);
                tmp.pre = last;
                last.next = tmp;
                last = tmp;
                //print();
            }
            order++;

            if(last.order - first.next.order > magicNumber - 1){
                first.next.next.pre = first;
                first.next = first.next.next;
            }
            //print();

            if(last.order > magicNumber - 1){
                Node3 tmp = new Node3(first.next.number,0);
                resultLast.next = tmp;
                resultLast = tmp;
            }
            //print2();
        }

        System.out.print(calculateXOR());
    }

    public static void print(){
        current = first;
        while(current != null){
            System.out.print(current.number + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static int calculateXOR(){
        int result = resultFirst.next.number;
        current = resultFirst.next.next;

        while(current != null){
            result = result ^ current.number;
            current = current.next;
        }
        return result;
    }

    public static void print2(){
        current = resultFirst.next;
        while(current != null){
            System.out.print(current.number + " ");
            current = current.next;
        }
        System.out.println();
    }
}
