import java.util.Scanner;

class Node2 {
    Node2 next = null;
    Node2 pre = null;
    int id = -1;

    Node2(int id){
        this.id = id;
    }
}

public class Ex2 {
    static Node2[] address;
    static Node2 first;
    static Node2 last;
    static Node2 current;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n = input.nextInt();
            int m = input.nextInt();

            address = new Node2[n];

            first = last = new Node2(-1);

            for(int j = 0;j < n;j++){
                int id = input.nextInt();

                Node2 tmp = new Node2(id);
                tmp.pre = last;
                last.next = tmp;
                last = tmp;

                address[id] = tmp;
            }

            Node2 tmp = new Node2(999999);
            tmp.pre = last;
            last.next = tmp;
            last = tmp;

            for(int j = 0;j < m;j++){
                int x1 = input.nextInt();
                int y1 = input.nextInt();
                int x2 = input.nextInt();
                int y2 = input.nextInt();

                if(address[x1].pre == null){
                    first = address[x2];
                }

                Node2 swapX = address[x1].pre;
                address[x1].pre.next = address[x2];
                address[x2].pre.next = address[x1];
                address[x1].pre = address[x2].pre;
                address[x2].pre = swapX;

                Node2 swapY = address[y1].next;
                address[y1].next.pre = address[y2];
                address[y2].next.pre = address[y1];
                address[y1].next = address[y2].next;
                address[y2].next = swapY;
            }
            print();
        }
    }

    public static void print(){
        current = first.next;
        while(current.next != null){
            System.out.print(current.id);
            if(current.next.next != null){
                System.out.print(" ");
            }

            current = current.next;
        }
        System.out.println();
    }
}
