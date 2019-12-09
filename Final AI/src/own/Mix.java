package own;

import java.io.FileNotFoundException;

public class Mix {

	public static void main(String args[]) throws FileNotFoundException {
		FaceB b = new FaceB();
		int totalFeatures = FaceB.totalFeatures;
		int maxNumber = FaceB.maxNumber;
		b.baysF();
		Double[][] totalCount = new Double[totalFeatures][maxNumber];
		totalCount = b.build(1);
		
		for(int i = 0; i < totalFeatures; i++) {
			for(int j = 0; j < maxNumber; j++) {
				System.out.print(totalCount[i][j] + " ");
			}
			System.out.println();
		}
		
		
		//p.perceptronF();
		
		
	}
	
	
}
