package a3;

import java.util.Scanner;
import java.util.Arrays;

public class p6 {
	static int left = 0;
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int t = scanner.nextInt();
		int n;
		long ab[][];
		long power;
		long answer[] = new long[t];
        long ableft[][];
        long abright[][];
		long red;
        long perpower;
		
		for(int i = 0;i<t;i++){
			left = 0;
			n = scanner.nextInt();
            ab = new long[n][2];
            power = 0;
            for(int j = 0;j<n;j++) {
            	ab[j][0] = scanner.nextLong();
            	ab[j][1] = scanner.nextLong();
            }
            ab = firstSort(ab);
            ableft = Arrays.copyOfRange(ab,0,left); 
            abright = Arrays.copyOfRange(ab,left,ab.length); 
            quickSortLeft(ableft,0,ableft.length-1);
            quickSortRight(abright,0,abright.length-1);
            red = 0;
            
            if(ableft != null) {
            	if(ableft.length == 1) {
            		red = ableft[0][1];
            	}else {
            		if(ableft.length!=0) {
            		red = ableft[0][1];}
            		for(int j = 0;j<ableft.length-1;j++) {
            	        power = power + Math.min(red, ableft[j+1][0]);
                        if(red<ableft[j+1][0]) {
                        	red = ableft[j+1][1];
                        }else {
                        	red = red - ableft[j+1][0] + ableft[j+1][1];
                        }            	        
            		}
            	}
            }           	
            if(abright != null) {
            	if(abright.length == 1) {
            		power = power + Math.min(red, abright[0][0]);
                    red = red - abright[0][0] + abright[0][1];
            	}else {
            		for(int j = 0;j<abright.length;j++) {
            	        power = power + Math.min(red, abright[j][0]);
                        if(red<abright[j][0]) {
                        	red = abright[j][1];
                        }else {
                        	red = red - abright[j][0] + abright[j][1];
                        }           	        
            		}
            	 }
            }           
            answer[i] = power;		
		}
            for(long out:answer) {
        	    System.out.println(out);
            }
	    
	}
            
            
            	
	public static void quickSortLeft(long[][] a, int start, int end) {  //sort left
		if (start >= end || a == null || a.length <= 1) {
			return;
			} 
		int i = start, j = end;
		long p = a[(start + end) / 2][0]; 
		while (i <= j) {
			while (a[i][0] < p) {
				++i; 
				} 
			while (a[j][0] > p) {
				--j; 
				} if (i < j) {
					long t = a[i][0];
					a[i][0] = a[j][0]; 
					a[j][0] = t; 
					t = a[i][1];
					a[i][1] = a[j][1]; 
					a[j][1] = t;
					++i;
					--j;
					} else if (i == j) {
						++i; 
						}
				} 
		quickSortLeft(a, start, j); 
		quickSortLeft(a, i, end); 
		}
	
	public static void quickSortRight(long[][] a, int start, int end) {  //sort right
		if (start >= end || a == null || a.length <= 1) {
			return;
			} 
		int i = start, j = end;
		long p = a[(start + end) / 2][1]; 
		while (i <= j) {
			while (a[i][1] > p) {
				++i; 
				} 
			while (a[j][1] < p) {
				--j; 
				} if (i < j) {
					long t = a[i][1];
					a[i][1] = a[j][1]; 
					a[j][1] = t; 
					t = a[i][0];
					a[i][0] = a[j][0]; 
					a[j][0] = t; 
					++i;
					--j;
					} else if (i == j) {
						++i; 
						}
				} 
		quickSortRight(a, start, j); 
		quickSortRight(a, i, end); 
		}
	
	
	public static long[][] firstSort(long a[][]) {    //to divide two directions
		long b[] = new long[a.length];
		long na[][] = new long[a.length][2];
		int right = a.length-1;
		
		for (int i = 0;i<a.length;i++) {
			b[i] = a[i][0] - a[i][1];
		}
		for (int i = 0;i<a.length;i++) {
			if(b[i]<0) {
				na[left] = a[i];				
			    left = left + 1;
			}else if(b[i]>0){
				na[right] = a[i];
				right = right - 1;
			}			
		}
		for (int i = 0;i<a.length;i++) {
			if(b[i]==0) {
				na[right] = a[i];				
			    right = right - 1;
			}
		}
		return na;
	}	
}


