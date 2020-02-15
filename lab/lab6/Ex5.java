import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Ex5 {
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
        int max = slong.length();

        pow = new long[max];
        pow[0] = 1;
        for(int i = 1;i < max;i++){
            if(i == 1){
                pow[i] = 131;
            }else{
                pow[i] = pow[i - 1] * 131;
            }
        }//math.pow好像会出问题，所以新建存131的i次方的数组

        int result = 0;//最后结果

        int left = 0;
        int right = sshort.length() - 1;
        int mid;

        //一次二分，找合适的子串长度
        while(left <= right){
            mid = (left + right)/2;
            boolean isFound = false;//有没有找到一样的子串

            long hash = calculate_Hash(slong.substring(0,mid + 1));//长串的第一个子串的hash
            long[] hashes = hash_Array_Of_Sub(sshort,mid);//短串所有子串的hash

            for(int i = 0;i < slong.length() - mid + 1;i++){
                if(i > 0 && i < slong.length() - mid){
                    int asc = slong.charAt(i + mid);
                    hash = ( hash - pow[mid] * slong.charAt(i - 1) )* 131 + asc;
                }//长串的下一个子串的hash

                //二次二分，找有没有一样的子串，逐一比较长串子串的hash和短串的hash数组
                if(binary_Search_Hash(hashes,hash)){
                    result = mid + 1;
                    isFound = true;
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

    public static long calculate_Hash(String s){
        long hash = 0;
        int times = s.length() - 1;

        for(int i = 0;i < s.length();i++){
            int asc = s.charAt(i);
            hash = hash + asc * pow[times];
            times--;
        }

        return hash;
    }

    public static long[] hash_Array_Of_Sub(String s, int mid){
        int hashNumber = s.length() - mid;//一共有多少个子串
        long[] hash = new long[hashNumber];

        hash[0] = calculate_Hash(s.substring(0,mid + 1));//第一个子串的hash

        for(int i = 1;i < hashNumber;i++){
            int asc = s.charAt(i + mid);
            hash[i] = ( hash[i - 1] - pow[mid] * s.charAt(i - 1) )* 131 + asc;
        }//之后的子串的hash

        mergeSort(hash,hash.length);

        return hash;
    }//计算一个串的所有子串并存入数组，排序的方法

    public static boolean binary_Search_Hash(long[] hashes,long hash){
        int left = 0;
        int right = hashes.length - 1;
        int mid;
        boolean isFound = false;

        while(left <= right){
            mid = (left + right)/2;

            if(hash == hashes[mid]){
                isFound = true;
                break;
            }

            if(hash > hashes[mid]){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }

        return isFound;
    }//第二个二分

    public static void mergeSort(long[] array,int length){
        int p;

        if(length > 1){
            p = length /2;

            long[] arrayA = new long[p];
            long[] arrayB = new long[length - p];

            for(int i = 0;i < p;i++){
                arrayA[i] = array[i];
            }
            for(int i = 0;i < length - p;i++){
                arrayB[i] = array[i + p];
            }

            mergeSort(arrayA,p);
            mergeSort(arrayB,length - p);

            long[] newArray = merge(arrayA,p,arrayB,length - p);

            for(int i = 0;i < newArray.length;i++){
                array[i] = newArray[i];
            }
        }
    }

    public static long[] merge(long[] arrayA,int lengthA,long[] arrayB,int lengthB){
        int length = lengthA + lengthB;
        long[] array = new long[length];
        int i = 0;
        int j = 0;

        for(int k = 0;k < length;k++){
            if(i < lengthA && (j >= lengthB || arrayA[i] <= arrayB[j])){
                array[k] = arrayA[i];
                i++;
            }else{
                array[k] = arrayB[j];
                j++;

            }
        }
        return array;
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


