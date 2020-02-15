import java.util.Scanner;

public class Ex2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int T = input.nextInt();

        for(int i = 0;i < T;i++){
            int n = input.nextInt();
            int m = input.nextInt();

            input.nextLine();
            String s = input.nextLine();
            String t = input.nextLine();

            boolean isMatch = ifContain(s,t);

            if(isMatch){
                System.out.print("YES");
            }else{
                System.out.print("NO");
            }
            if(i < T - 1){
                System.out.println();
            }
        }
    }

    public static boolean ifContain(String s,String t){
        boolean isMatch = false;

        if(!s.contains("*")){
            if(s.equals(t)){
                isMatch = true;
            }
        }else if(s.length() <= t.length() + 1){
            int position = s.indexOf("*");

            String prefixs = s.substring(0,position);
            String postfixs = s.substring(position + 1);
            String prefixt = t.substring(0,position);
            String postfixt = t.substring(t.length() - (s.length() - position) + 1);

            //System.out.println(prefixs + " " + postfixs + " " + prefixt + " " + postfixt + " ");

            if(prefixs.equals(prefixt) && postfixs.equals(postfixt)){
                isMatch = true;
            }
        }
        return isMatch;
    }
}
