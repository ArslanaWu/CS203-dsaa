import java.util.ArrayList;
import java.util.Scanner;

public class Ex2 {
    static ArrayList<int[]> place = new ArrayList<>();
    static int[] sum = new int[9];
    static String[][] sudoku = new String[9][9];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        ArrayList<int[]> places = new ArrayList<>();

        for(int i = 0;i < 9; i++){
            for(int j = 0; j < 9;j++){
                String inputs = input.next();
                if(inputs.equals("x")){
                    int[] array = {i,j};
                    places.add(array);
                }
                if(inputs.equals("|")){
                    String inputss = input.next();
                    if(inputss.equals("x")){
                        int[] array = {i,j};
                        places.add(array);
                    }
                    sudoku[i][j] = inputss;
                }else{
                    sudoku[i][j] = inputs;
                }
            }
        }

        fill(places);
    }
    public static void fill(ArrayList<int[]> places){
        ArrayList<int[]> lineCommon;
        int size;

        //改值
        lineCommon = findLineCommon(places);
        size = lineCommon.size();
        if(size != 0){
            for(int i = 0;i < size;i++){
                int row = lineCommon.get(i)[0];
                int column = lineCommon.get(i)[1];
                int kind = lineCommon.get(i)[2];
                int sum = 0;

                if(kind == 0){
                    for(int j = 0;j < 9;j++){
                        if(j != column){
                            sum = sum + Integer.parseInt(sudoku[row][j]);
                        }
                    }
                    sudoku[row][column] = String.valueOf(45 - sum);
                }else if(kind == 1){
                    for(int j = 0;j < 9;j++){
                        if(j != row){
                            sum = sum + Integer.parseInt(sudoku[j][column]);
                        }
                    }
                    sudoku[row][column] = String.valueOf(45 - sum);
                }else if(kind == 2){
                    sudoku[row][column] = String.valueOf(45 - lineCommon.get(i)[3]);
                }
            }
        }else{
            System.out.print("The test data is incorrect!");
            return;
        }

        ArrayList<int[]> placess = new ArrayList<>();
        for(int i = 0;i < 9; i++){
            for(int j = 0; j < 9;j++){
                if(sudoku[i][j].equals("x")){
                    int[] array = {i,j};
                    placess.add(array);
                }
            }
        }
        if(placess.size() != 0 && placess.size() != places.size()){
            fill(placess);
        }else if(placess.size() != 0 && placess.size() == places.size()){
            System.out.print("The test data is incorrect!");
            return;
        }else{
            for(int i = 0;i < 9; i++){
                for(int j = 0; j <9;j++){
                    System.out.print(sudoku[i][j]);
                    System.out.print(" ");
                    if(j == 2 || j == 5){
                        System.out.print("| ");
                    }else if(j == 8){
                        System.out.println("|");
                        if(i == 2 || i == 5){
                            System.out.println();
                        }
                    }
                }
            }
        }
    }

    public static ArrayList<int[]> findLineCommon(ArrayList<int[]> places){
        int length = places.size();
        int[][] times = new int[2][length];
        int[] timesBlock = new int[9];

        //line
        for(int i = 0;i < length;i++){
            for(int j = 0;j < length;j++){
                if(places.get(i)[0] == places.get(j)[0]){
                    times[0][i]++;
                }
                if(places.get(i)[1] == places.get(j)[1]){
                    times[1][i]++;
                }
            }
        }
        for(int i = 0;i < length;i++){
            if(times[0][i] == 1){
                int[] array = {places.get(i)[0],places.get(i)[1],0};
                place.add(array);
            }
            if(times[1][i] == 1){
                int[] array = {places.get(i)[0],places.get(i)[1],1};
                place.add(array);
            }
        }

        //block
        for(int i = 0;i < length;i++){
            int row = places.get(i)[0];
            int column = places.get(i)[1];

            if(row >= 0 &&  row <= 2){
                if(column >= 0 &&  column <= 2){
                    timesBlock[0]++;
                }else if(column >= 3 && column <= 5){
                    timesBlock[1]++;
                }else if(column >= 6 && column <= 8){
                    timesBlock[2]++;
                }
            }else if(row >= 3 &&  row <= 5){
                if(column >= 0 &&  column <= 2){
                    timesBlock[3]++;
                }else if(column >= 3 && column <= 5){
                    timesBlock[4]++;
                }else if(column >= 6 && column <= 8){
                    timesBlock[5]++;
                }
            }else if(row >= 6 &&  row <= 8){
                if(column >= 0 &&  column <= 2){
                    timesBlock[6]++;
                }else if(column >= 3 && column <= 5){
                    timesBlock[7]++;
                }else if(column >= 6 && column <= 8){
                    timesBlock[8]++;
                }
            }
        }
        for(int i = 0;i < 9;i++){
            if(timesBlock[i] == 1){
                if(i == 0){
                    place.add(find(0,0,places));
                }else if(i == 1){
                    place.add(find(0,3,places));
                }else if(i == 2){
                    place.add(find(0,6,places));
                }else if(i == 3){
                    place.add(find(3,0,places));
                }else if(i == 4){
                    place.add(find(3,3,places));
                }else if(i == 5){
                    place.add(find(3,6,places));
                }else if(i == 6){
                    place.add(find(6,0,places));
                }else if(i == 7){
                    place.add(find(6,3,places));
                }else{
                    place.add(find(6,6,places));
                }
            }
        }
        return place;
    }

    public static int[] find(int row,int column,ArrayList<int[]> places){
        int length = places.size();
        int[] array = new int[4];
        for(int i = 0;i <= length;i++){
            int r = places.get(i)[0];
            int c = places.get(i)[1];

            if(r >= row && r <= row + 2){
                if(c >= column && c <= column + 2){
                    array[0] = r;
                    array[1] = c;
                    array[2] = 2;
                    break;
                }
            }
        }
        int sum = 0;
        for(int i = row;i <= row + 2;i++){
            for(int j = column;j <= column + 2;j++){
                if(!sudoku[i][j].equals("x")){
                    sum = sum + Integer.parseInt(sudoku[i][j]);
                }
            }
        }
        array[3] = sum;
        return array;
    }
}
