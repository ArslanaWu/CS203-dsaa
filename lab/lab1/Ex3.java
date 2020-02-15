import java.util.ArrayList;
import java.util.Scanner;

public class Ex3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int num = input.nextInt();
        input.nextLine();
        String[] line = new String[num];

        for(int i = 0;i < num;i++){
            line[i] = input.nextLine();
        }

        for(int i = 0;i < num;i++){
            String[] array = sort(line[i]);
            for(int j = 0;j < array.length;j++){
                System.out.print(array[j]);
                if(j != array.length - 1){
                    System.out.print(" ");
                }
            }
            if(i != num - 1){
                System.out.println();
            }
        }
    }

    public static String[] sort(String line){
        String[] symbols = line.split(" ");
        int length = symbols.length;
        String[] sorteds = new String[length];


        ArrayList<String> w = new ArrayList<>();
        ArrayList<String> t = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();
        int[] times = new int[7];


        for(String symbol : symbols){
            if(symbol.charAt(0) == 'W' && symbol.length() != 1){
                w.add(symbol);
            }else if(symbol.toLowerCase().charAt(0) == 't'){
                t.add(symbol);
            }else if(symbol.toLowerCase().charAt(0) == 'y'){
                y.add(symbol);
            }
        }
        for(String symbol : symbols){
            if(symbol.equals("E")){
                times[0]++;
            }else if(symbol.equals("S")){
                times[1]++;
            }else if(symbol.equals("W")){
                times[2]++;
            }else if(symbol.equals("N")){
                times[3]++;
            }else if(symbol.equals("B")){
                times[4]++;
            }else if(symbol.equals("F")){
                times[5]++;
            }else if(symbol.equals("Z")){
                times[6]++;
            }
        }

        for(int i = 0;i < w.size();i++){
            sorteds[i] = sortNumber(w)[i];
        }
        for(int i = 0;i < t.size();i++){
            sorteds[i + w.size()] = sortNumber(t)[i];
        }
        for(int i = 0;i < y.size();i++){
            sorteds[i + w.size() + t.size()] = sortNumber(y)[i];
        }

        int k = 0;
        for(int i = 0;i < 7;i++){
            for(int j = 0;j < times[i];j++){
                if(i == 0){
                    sorteds[k + w.size() + t.size() + y.size()] = "E";
                }else if(i == 1){
                    sorteds[k + w.size() + t.size() + y.size()] = "S";
                }else if(i == 2){
                    sorteds[k + w.size() + t.size() + y.size()] = "W";
                }else if(i == 3){
                    sorteds[k + w.size() + t.size() + y.size()] = "N";
                }else if(i == 4){
                    sorteds[k + w.size() + t.size() + y.size()] = "B";
                }else if(i == 5){
                    sorteds[k + w.size() + t.size() + y.size()] = "F";
                }else{
                    sorteds[k + w.size() + t.size() + y.size()] = "Z";
                }
                k++;
            }
        }

        return sorteds;

    }

    public static String[] sortNumber(ArrayList<String> symbols){
        int length = symbols.size();
        String[] symbol = new String[length];
        String change;

        for(int i = 0;i < length;i++){
            symbol[i] = symbols.get(i);
        }

        if(length == 1){
            return symbol;
        }

        for(int i = 0;i < length;i++){
            int k = i;
            for(int j = i + 1;j < length;j++){
                if(symbol[k].charAt(1) > symbol[j].charAt(1)){
                    k = j;
                }
            }

            change = symbol[i];
            symbol[i] = symbol[k];
            symbol[k] = change;
        }
        return symbol;
    }
}
