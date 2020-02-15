import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

class Node4 {
    Node4 next = null;
    Node4 pre = null;
    int note;
    int index;

    Node4(int note,int index){
        this.note = note;
        this.index = index;
    }
}

public class Ex4 {
    static Node4 first;
    static Node4 last;
    static Node4 pointer;

    public static void main(String[] args) throws IOException {
        Reader input =new Reader();
        PrintWriter out=new PrintWriter(System.out);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n = input.nextInt();
            int m = input.nextInt();

            first = last = new Node4(input.nextInt(),1);
            pointer = first;
            for(int j = 1;j < n;j++){
                Node4 tmp = new Node4(input.nextInt(),j + 1);

                last.next = tmp;
                tmp.pre = last;
                last = tmp;
            }

            last.next = first;
            first.pre = last;

            while(n > 1){
                int count = 1;
                m = m % n;

                if(m == 0){
                    pointer = pointer.pre;
                }else{
                    while(count < m){
                        pointer = pointer.next;
                        count++;
                    }
                }

                m = pointer.note;
                pointer.pre.next = pointer.next;
                pointer.next.pre = pointer.pre;
                pointer = pointer.next;
                n--;
            }

            out.println(pointer.index);
        }

        out.close();
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





