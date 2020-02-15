import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class Node4{
    int number;
    Node4 pre;
    Node4 next;

    Node4(int number){
        this.number = number;
    }
}

public class Ex4 {
    static Node4 first;
    static Node4 last;
    static Node4 current;

    static Node4 resultFirst;
    static Node4 resultLast;

    public static void main(String[] args) throws IOException {
        Reader input =new Reader();
        PrintWriter out=new PrintWriter(System.out);

        //Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n = input.nextInt();

            boolean[] inStack = new boolean[n];
            boolean foundOne = false;

            int toFind = 2;

            int remainInStack = 0;
            int addedInResult = 0;

            first = last = new Node4(-1);
            resultFirst = resultLast = new Node4(1);

            while(addedInResult != n){
                int number = -2;

                if(!foundOne){
                    number = input.nextInt();
                }

                if(number == 1){
                    foundOne = true;
                    addedInResult++;
                    continue;
                }

                if(!foundOne){
                    Node4 tmp = new Node4(number);
                    tmp.pre = last;
                    last.next = tmp;
                    last = tmp;
                    inStack[number - 1] = true;
                    remainInStack++;

                    //System.out.println(Arrays.toString(inStack));
                    //print2();
                }
                else{
                    if(n - addedInResult == remainInStack){
                        while(last.number != -1){
                            Node4 tmp = new Node4(last.number);
                            resultLast.next = tmp;
                            resultLast = tmp;
                            addedInResult++;
                            last = last.pre;
                            remainInStack--;

                            //print();
                            //print2();
                        }
                        break;
                    }

                    while(inStack[toFind - 1] && last.number != toFind){
                        toFind++;
                    }

                    if(last.number != -1 && last.number <= toFind){
                        while(last.number != -1 && last.number <= toFind){
                            if(last.number == toFind){
                                toFind++;
                            }
                            Node4 tmp = new Node4(last.number);
                            resultLast.next = tmp;
                            resultLast = tmp;
                            addedInResult++;

                            last = last.pre;
                            last.next = null;
                            remainInStack--;
                        }
                    }else{
                        int number1 = input.nextInt();

                        while(number1 != toFind){
                            Node4 tmp = new Node4(number1);
                            tmp.pre = last;
                            last.next = tmp;
                            last = tmp;
                            remainInStack++;
                            inStack[number1 - 1] = true;

                            //System.out.println(Arrays.toString(inStack));
                            //print2();

                            number1 = input.nextInt();
                        }
                        Node4 tmp = new Node4(number1);
                        resultLast.next = tmp;
                        resultLast = tmp;
                        addedInResult++;

                        toFind++;
                    }
                    //print();
                }
            }

            //print();
            current = resultFirst;
            while(current != null){
                out.print(current.number);
                if(current.next != null){
                    out.print(" ");
                }
                current = current.next;
            }
            out.println();
        }

        out.close();
    }

    public static void print(){}//print resultFirst

    public static void print2(){
        current = first;
        while(current != null){
            System.out.print(current.number);
            if(current.next != null){
                System.out.print(" ");
            }
            current = current.next;
        }
        System.out.println();
    }//print first

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }
}





