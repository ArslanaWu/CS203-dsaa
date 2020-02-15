import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Ex5_KMP {
    static long[] pow;

    public static void main(String[] args) {
        InputStream inputStream = System.in;// new FileInputStream("C:\\Users\\wavator\\Downloads\\test.in");
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);



        String s1 = in.next();
        String s2 = in.next();
        String slong;
        String sshort;

        if(s1.length() > s2.length()){
            slong = s1;
            sshort = s2;
        }else{
            slong = s2;
            sshort = s1;
        }//判断长短

        setPow(slong.length());

        int result = 0;//最后结果

        int left = 0;
        int right = sshort.length() - 1;
        int mid;

        //二分，找合适的子串长度
        while(left <= right){
            mid = (left + right)/2;
            boolean isFound = false;//有没有找到一样的子串

            for(int i = 0;i < sshort.length() - mid - 1;i++){
                String sub_Of_Short = sshort.substring(i,i + mid + 1);

                if(KMP1(sub_Of_Short,slong) != -1){
                    isFound = true;
                    result = sub_Of_Short.length();
                    break;
                }
            }

            if(isFound){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }

        out.print(result);


        out.close();
    }

    public static int[] nextArray(String p){
        int m = p.length();
        int[] next = new int[m];
        int k = -1;
        next[0] = -1;

        for(int i = 1;i < m;i++){
            while(k > -1 && p.charAt(k + 1) != p.charAt(i)){
                k = next[k];
            }
            if(p.charAt(k + 1) == p.charAt(i)){
                k = k + 1;
            }
            next[i] = k;
        }
        return next;
    }

    public static int KMP1(String p, String t){
        int[] next = nextArray(p);

        int m = p.length();
        int n = t.length();
        int k = -1;

        for(int i = 0;i < n;i++){
            while(k > -1 && p.charAt(k + 1) != t.charAt(i)){
                k = next[k];
            }
            if(p.charAt(k + 1) == t.charAt(i)){
                k++;
            }
            if(k == m - 1){
                return i - m + 1;
            }
        }
        return -1;
    }

    public static void setPow(int max){
        pow = new long[max];
        pow[0] = 1;
        for(int i = 1;i < max;i++){
            if(i == 1){
                pow[i] = 131;
            }else{
                pow[i] = pow[i - 1] * 131;
            }
        }//math.pow好像会出问题，所以新建存131的i次方的数组
    }


    static class Task {

        public void solve(InputReader in, PrintWriter out) {




        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] nextCharArray() {
            return next().toCharArray();
        }

        //         public boolean hasNext() {
//             try {
//                 return reader.ready();
//             } catch(IOException e) {
//                 throw new RuntimeException(e);
//             }
//         }
        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }

        public BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        public BigDecimal nextBigDecinal() {
            return new BigDecimal(next());
        }
    }

}
