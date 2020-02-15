import java.util.Scanner;

class Node3 {
    Node3 next = null;
    Node3 pre = null;
    char character;

    Node3(char character){
        this.character = character;
    }
}

public class Ex3 {
    static Node3 first;
    static Node3 last;
    static Node3 pointer;
    static char[] characters;
    static Node3 current;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int length = input.nextInt();
            input.nextLine();
            characters = input.next().toCharArray();

            edit();

            print();
        }
    }

    public static void edit(){
        int count = 0;
        int length = characters.length;

        first = last = new Node3('a');

        Node3 eol = new Node3('E');
        first.next = eol;
        eol.pre = first;
        pointer = first.next;

        while(count < length){
            if(Character.isDigit(characters[count])){
                Node3 tmp = new Node3(characters[count]);

                tmp.pre = pointer.pre;
                pointer.pre.next = tmp;
                tmp.next = pointer;
                pointer.pre = tmp;

                count++;

                //print();
            }else{
                if(characters[count] == 'H' && pointer.pre.character != 'a'){
                    pointer = pointer.pre;

                    //print();
                }else if(characters[count] == 'L' && pointer.next != null){
                    pointer = pointer.next;

                    //print();
                }else if(characters[count] == 'I'){
                    pointer = first.next;

                    //print();
                }else if(characters[count] == 'r'){
                    if(count == length - 1){
                        break;
                    }
                    if(pointer.character == 'E'){
                        count++;
                        Node3 tmp = new Node3(characters[count]);

                        tmp.pre = pointer.pre;
                        pointer.pre.next = tmp;
                        tmp.next = pointer;
                        pointer.pre = tmp;
                        pointer = pointer.pre;
                    }else{
                        count++;
                        pointer.character = characters[count];
                    }

                    //print();
                }else if(characters[count] == 'x'){
                    if(pointer.character != 'E'){
                        pointer.pre.next = pointer.next;
                        pointer.next.pre = pointer.pre;
                        pointer = pointer.next;
                    }

                    //print();
                }
                count++;
            }
        }
    }

    public static void print(){
        current = first.next;
        while(current.character != 'E'){
            System.out.print(current.character);
            current = current.next;
        }
        System.out.println();
    }
}
