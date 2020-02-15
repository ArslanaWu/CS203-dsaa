import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

class Node5{
    long number;
    int index;
    Node5 next;
    Node5 pre;

    Node5(long number,int index){
        this.number = number;
        this.index = index;
    }
}

public class Ex5 {
    static Node5 first;
    static Node5 last;

    static Node5 current;

    public static void main(String[] args) throws IOException {
        Reader input =new Reader();
        PrintWriter out=new PrintWriter(System.out);

        //Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n = input.nextInt();
            long[] numbers = new long[n];
            int[][] indexes = new int[2][n];

            for(int j = 0;j < n;j++){
                numbers[j] = input.nextLong();
            }

            findRight(numbers,indexes);
            findLeft(numbers,indexes);

            //printArray
            out.printf("Case %d:\n",i + 1);
            for(int a = 0;a < n;a++){
                out.print(indexes[1][a] + " ");
                out.println(indexes[0][a]);
            }
        }

        out.close();
    }

    public static void findRight(long[] numbers,int[][] indexes){
        int needToAdd = numbers.length;

        first = last = new Node5(50001,-1);
        current = last;

        for(int i = 0;i < needToAdd;i++){
            if(numbers[i] <= last.number){
                Node5 tmp = new Node5(numbers[i],i);

                tmp.pre = last;
                last.next = tmp;
                last = tmp;
                current = last;


                //print();
            }else{
                while(current.number <= numbers[i]){
                    if(current == last){
                        indexes[0][current.index] = 0;
                    }else{
                        indexes[0][current.index] = current.next.index + 1;
                    }
                    current = current.pre;



                    //System.out.println(Arrays.toString(indexes[0]));
                }

                Node5 tmp = new Node5(numbers[i],i);
                tmp.pre = current;
                current.next = tmp;
                last = current = tmp;



                //print();
            }
        }

        while(current.number != 50001){
            if(current.next == null){
                indexes[0][current.index] = 0;
            }else{
                indexes[0][current.index] = current.next.index + 1;
            }
            current = current.pre;

            //System.out.println(Arrays.toString(indexes[0]));
        }

    }

    public static void findLeft(long[] numbers,int[][] indexes){
        int needToAdd = numbers.length;

        first = last = new Node5(50001,-1);
        current = last;

        for(int i = needToAdd - 1;i >= 0;i--){
            if(numbers[i] <= last.number){
                Node5 tmp = new Node5(numbers[i],i);

                tmp.pre = last;
                last.next = tmp;
                last = tmp;
                current = last;


                //print();
            }else{
                while(current.number <= numbers[i]){
                    if(current == last){
                        indexes[1][current.index] = 0;
                    }else{
                        indexes[1][current.index] = current.next.index + 1;
                    }
                    current = current.pre;
                    long m = current.number;



                    //System.out.println(Arrays.toString(indexes[1]));
                }

                Node5 tmp = new Node5(numbers[i],i);
                tmp.pre = current;
                current.next = tmp;
                last = current = tmp;

                //print();
            }
        }

        while(current.number != 50001){
            if(current.next == null){
                indexes[1][current.index] = 0;
            }else{
                indexes[1][current.index] = current.next.index + 1;
            }
            current = current.pre;



            //System.out.println(Arrays.toString(indexes[1]));
        }
    }

    public static void print(){
        Node5 current = first;
        while(current != null){
            System.out.print(current.number + " ");
            current = current.next;
        }
        System.out.println();
    }

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



