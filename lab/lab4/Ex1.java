import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

class Node1 {
    Node1 next = null;
    long coefficient;
    long exponent;

    Node1(long coefficient, long exponent){
        this.coefficient = coefficient;
        this.exponent = exponent;
    }
}

public class Ex1 {
    static Node1 first;
    static Node1 last;
    static Node1 current;

    public static void main(String[] args) throws IOException {
        Reader input=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            first = new Node1(-1,-1);
            last = first;

            int n = input.nextInt();
            for(int j = 0;j < n;j++){
                Node1 tmp = new Node1(input.nextLong(),input.nextLong());
                last.next = tmp;
                last = tmp;
            }

            current = first;
            int m = input.nextInt();
            for(int j = 0;j < m;j++){
                long coefficient = input.nextLong();
                long exponent = input.nextLong();

                while(current.next != null && exponent > current.next.exponent){
                    current = current.next;
                }
                if(current.next != null && current.next.exponent == exponent){
                    current.next.coefficient += coefficient;
                }else{
                    Node1 tmp = new Node1(coefficient,exponent);

                    tmp.next = current.next;
                    current.next = tmp;
                    current = tmp;
                }
            }

            int q = input.nextInt();
            long[] coefficients = new long[q];
            current = first;

            for(int j = 0;j < q;j++){
                long exponent = input.nextLong();
                coefficients[j] = findValue(exponent);
            }

            for(int j = 0;j < q;j++){
                out.print(coefficients[j]);
                if(j < q - 1){
                    out.print(" ");
                }
            }
            if(i < t - 1){
                out.println();
            }
        }
        out.close();
    }

    public static long findValue(long exponent){
        while(current.next != null && exponent > current.exponent){
            current = current.next;
        }
        if(exponent == current.exponent){
            return current.coefficient;
        }else{
            return 0;
        }
    }

    static class Reader{
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



