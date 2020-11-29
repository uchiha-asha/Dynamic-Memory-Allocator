import java.util.Scanner;

public class test {


	public static void main(String args[]) {

		long startTime = System.nanoTime();
		int size = 10000000000;
		A2DynamicMem obj = new A2DynamicMem(size, 3);
		for (int i=1; i<=size; i++) {
			obj.Allocate(i);
		}
		long stopTime = System.nanoTime();
		System.out.println((stopTime - startTime)/1000000000.0);   
	}
}