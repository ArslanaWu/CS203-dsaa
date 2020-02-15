import java.util.Scanner;//Don't delete this line
public class Lab{
      static class node{
            int co;
            int ex;
            node next;//the address of the next node
           node(int co,int ex){
            this.co=co;
            this.ex=ex;
           }
      }
      public static void main(String[] args) {
            Scanner input = new Scanner(System.in);

            int t = input.nextInt();

            for(int i=0;i<t;i++){
            node head = new node(-1,-1);//make a special head node
            node tail = head;//initial tail==head, new node will be connected after tail

                  int n = input.nextInt();
                  for(int j=0;j<n;j++){
                        node tmp = new node(input.nextInt(),input.nextInt());//make a new node
                        tail.next=tmp;//connect tmp to tail
                        tail = tmp;//now tmp is the new tail
                  }

                  int m = input.nextInt();
                  node current = head;
                  node current1 = head.next;

                  for(int j=0;j<m;j++){
                        node tmp = new node(input.nextInt(),input.nextInt());//make a new node
                        //find the position where tmp should be insert;
                        while(current1!=null && current1.ex<tmp.ex){//find the first position where ex >tmp.ex
                              current=current.next;
                              current1=current1.next;
                        }
                        if(current1!=null && current1.ex==tmp.ex)
                              current1.co+=tmp.co;
                        else {
                              tmp.next = current1;
                              current.next = tmp;//insert tmp;
                              current = tmp;
                        }
                  }
                  int q = input.nextInt();
                  current=head;

                  for(int j=0;j<q;j++){
                        long k = input.nextLong();
                        while(current.next!=null && current.ex<k)
                              current=current.next;
                        if(current.ex==k){
                              System.out.print(current.co);
                              if(j < q - 1){
                                    System.out.print(" ");
                              }
                        }
                        else
                              System.out.print("0 ");
                  }
                  System.out.println();
            }
      }
}
