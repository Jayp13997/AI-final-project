package knn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Digits{
	
	static ArrayList<Integer[][]> images = new ArrayList<Integer[][]>();
	static ArrayList<Integer[][]> tempImages = new ArrayList<Integer[][]>();
	static ArrayList<Integer> indices = new ArrayList<Integer>();
	static int rows = 28; 
	static int columns = 28;
	static int splitRows = 14;
	static int splitColumns = 14;
	static ArrayList<ArrayList<Integer>> weights = new ArrayList<ArrayList<Integer>> ();
	static ArrayList<ArrayList<Integer>> feature =  new ArrayList<ArrayList<Integer>>();//number of pixels
	static ArrayList<ArrayList<Integer>> oldWeights = new ArrayList<ArrayList<Integer>>();
	static ArrayList<Integer> finalFeature = new ArrayList<Integer>();
	static ArrayList<Integer> finalWeights = new ArrayList<Integer>();
	static ArrayList<Integer> fy = new ArrayList<Integer>();
	static ArrayList<Integer> fy1 = new ArrayList<Integer>();
	static ArrayList<Integer> fy2 = new ArrayList<Integer>();
	static ArrayList<Integer> trainLabels = new ArrayList<Integer>();
	
	
	static ArrayList<Integer[][]> images2 = new ArrayList<Integer[][]>();
	static ArrayList<ArrayList<Integer>> feature2 =  new ArrayList<ArrayList<Integer>>();//number of pixels
	static ArrayList<Integer> finalFeature2 = new ArrayList<Integer>();
	static ArrayList<Integer> testLabels = new ArrayList<Integer>();
	
public static void main(String[] args) throws FileNotFoundException{
		
		
	
		/*for(int ww = 0; ww < 100; ww++) {
			
			images.clear();
			images2.clear();
			weights.clear();
			feature.clear();
			feature2.clear();
			tempImages.clear();
			indices.clear();
			oldWeights.clear();*/
			
		initializeFaces("trainingimages", images);
		initializeFaces("testimages", images2);
		
		for(int i = 0; i < images.size(); i++) {
			numberOfPixels(i, feature, images);
		}
		
		for(int i = 0; i < images2.size(); i++) {
			numberOfPixels(i, feature2, images2);
		}
		//System.out.println(images2.size());
		fillLabels("traininglabels", trainLabels);
		fillLabels("testlabels", testLabels);
		
		
		int x = 1;
	
		
		while(x <= 10) {
			
			
			indices.clear();
			tempImages.clear(); 
			weights.clear();
			finalFeature.clear();
			finalFeature2.clear();
			//System.out.println(images.size());
			//System.out.println("here " + (int)( (double) images.size() *  ((double)x/10)));
			
			for(int i = 0; i < (int)((double) images.size() *  ((double)x/10)); i++){//same image again or not?
//				Random r = new Random();
//				int index = (int) r.nextInt(images.size());
//				while(indices.contains(index) == true) {
//					index = (int) r.nextInt(images.size());
//				}
				indices.add(i);
				tempImages.add(images.get(i));
			}	
			//System.out.println(tempImages.size());
			runPerceptron(); 
		
			long starttime = System.nanoTime();
			
			for(int index = 0; index < images.size(); index++) {
				
				int sum = 0;
				fy1.clear();
				for(int i = 0; i < weights.size(); i++) {
				
					for(int j = 0; j < weights.get(i).size() - 1; j++){
						sum += (weights.get(i).get(j) * feature.get(index).get(j));
					}
					
					sum += weights.get(i).get(weights.get(i).size() - 1);
					fy1.add(sum);
			
				
				}
			
				sum = 0;
				for(int i = 0; i < fy1.size(); i++) {
					sum += fy1.get(i);
				}
				finalFeature.add(sum);
				
//				 sum = getMax(fy1);
//				 finalFeature.add(sum);
				
				
			}
			
			
			
			
			
			
			for(int index = 0; index < images2.size(); index++) {
				
				int sum = 0;
				fy2.clear();
				for(int i = 0; i < weights.size(); i++) {
				
					for(int j = 0; j < weights.get(i).size() - 1; j++){
						sum += (weights.get(i).get(j) * feature2.get(index).get(j));
					}
					
					sum += weights.get(i).get(weights.get(i).size() - 1);
					fy2.add(sum);
			
				
				}
			
				sum = 0;
				for(int i = 0; i < fy2.size(); i++) {
					sum += fy2.get(i);
				}
				
				finalFeature2.add(sum);
				
				
//				sum = getMax(fy2);
//				 finalFeature2.add(sum);
				
				
			}
			
			
			
			int correct = 0;
			ArrayList<Integer> indicies = new ArrayList<Integer>();
			
			for(int i = 0; i < finalFeature2.size(); i++) {
				indicies.clear();
//				System.out.print(feature2.get(i) + "  ");
				for(int k = 0; k < 51; k++) {
					
					int diff = 10000;
					int index = 0;
					for(int j = 0; j < feature.size(); j++) {
					
						if(Math.abs(finalFeature2.get(i) - finalFeature.get(j)) < diff) {
							if(inArray(indicies, j) == false) {
								diff = Math.abs(finalFeature2.get(i) - finalFeature.get(j));
								index = j;
							}
						}
					
					}
//					System.out.print(feature.get(index) + "  " + trainLabels.get(index) + "  ");
					indicies.add(index);
				
				}
			

				int num[] = {0,0,0,0,0,0,0,0,0,0};
				
				for(int k = 0; k < 51; k++) {
					if(trainLabels.get(indicies.get(k)) == 0) {
						num[0]++;
					}
					if(trainLabels.get(indicies.get(k)) == 1) {
						num[1]++;
					}
					if(trainLabels.get(indicies.get(k)) == 2) {
						num[2]++;
					}
					if(trainLabels.get(indicies.get(k)) == 3) {
						num[3]++;
					}
					if(trainLabels.get(indicies.get(k)) == 4) {
						num[4]++;
					}
					if(trainLabels.get(indicies.get(k)) == 5) {
						num[5]++;
					}
					if(trainLabels.get(indicies.get(k)) == 6) {
						num[6]++;
					}
					if(trainLabels.get(indicies.get(k)) == 7) {
						num[7]++;
					}
					if(trainLabels.get(indicies.get(k)) == 8) {
						num[8]++;
					}
					if(trainLabels.get(indicies.get(k)) == 9) {
						num[9]++;
					}
				}
				
				if(testLabels.get(i) == findmax(num)) {
					correct++;
				}
				
				
				
			
			}
			
			
			double answer = (double)correct / images2.size();
			answer = answer * 100;
			
			long endtime = System.nanoTime();
			
			System.out.println("Testing time: " + ((double)(endtime - starttime)/1000000000) + "s");
			
			System.out.println(answer + "%" );
			System.out.println();
			
			
			x++;
		
		}
}
		
		
	
		
		
		
		
		
		
		
		
		
/*		
		for(int j = 0; j < weights.get(0).size(); j++) {
		
			int sum = 0;
			for(int i = 0; i < weights.size(); i++) {
			
				sum = sum + weights.get(i).get(j);
			
			}
			
			finalWeights.add(sum);
		
		}
		
		
		
		
		
		
		
		

		for(int i = 0; i < feature.size(); i++) {
			int sum = 0;
			for(int j = 0; j < feature.get(i).size(); j++) {
				
				sum = sum + (feature.get(i).get(j) * finalWeights.get(j));
				
			}
			
			finalFeature.add(sum);
		}
		
		
		for(int i = 0; i < feature2.size(); i++) {
			int sum = 0;
			for(int j = 0; j < feature2.get(i).size(); j++) {
				
				sum = sum + (feature2.get(i).get(j) * finalWeights.get(j));
				
			}
			
			finalFeature2.add(sum);
		}
		
		
		int correct = 0;
		ArrayList<Integer> indicies = new ArrayList<Integer>();
		
		
		for(int i = 0; i < finalFeature2.size(); i++) {
			indicies.clear();
//			System.out.print(feature2.get(i) + "  ");
			for(int k = 0; k < 5; k++) {
				
				int diff = 10000;
				int index = 0;
				for(int j = 0; j < feature.size(); j++) {
				
					if(Math.abs(finalFeature2.get(i) - finalFeature.get(j)) < diff) {
						if(inArray(indicies, j) == false) {
							diff = Math.abs(finalFeature2.get(i) - finalFeature.get(j));
							index = j;
						}
					}
				
				}
//				System.out.print(feature.get(index) + "  " + trainLabels.get(index) + "  ");
				indicies.add(index);
			
			}
		
//			System.out.println();
			
			int num[] = {0,0,0,0,0,0,0,0,0,0};
			
			for(int k = 0; k < 5; k++) {
				if(trainLabels.get(indicies.get(k)) == 0) {
					num[0]++;
				}
				if(trainLabels.get(indicies.get(k)) == 1) {
					num[1]++;
				}
				if(trainLabels.get(indicies.get(k)) == 2) {
					num[2]++;
				}
				if(trainLabels.get(indicies.get(k)) == 3) {
					num[3]++;
				}
				if(trainLabels.get(indicies.get(k)) == 4) {
					num[4]++;
				}
				if(trainLabels.get(indicies.get(k)) == 5) {
					num[5]++;
				}
				if(trainLabels.get(indicies.get(k)) == 6) {
					num[6]++;
				}
				if(trainLabels.get(indicies.get(k)) == 7) {
					num[7]++;
				}
				if(trainLabels.get(indicies.get(k)) == 8) {
					num[8]++;
				}
				if(trainLabels.get(indicies.get(k)) == 9) {
					num[9]++;
				}
			}
			
			if(testLabels.get(i) == findmax(num)) {
				correct++;
			}
			
		}
			
			
			
						
//			System.out.println(index + "         " + trainLabels.get(index));
//			indicies.add(index);
			
		
		double answer = (double)correct / images2.size();
		answer = answer * 100;
		
//		long endtime = System.nanoTime();
		
//		System.out.println("Testing time: " + ((double)(endtime - starttime)/1000000000) + "s");
		
		System.out.println(answer + "%" );
		System.out.println();
		
		x++;
		
	}
}
		
*/		
		
		
		
		
		
		
		
		
		
		
		
		
/*	
		for(int index = 0; index < images2.size(); index++) {
			
			int sum = 0;
			int answer;
			fy2.clear();
			for(int i = 0; i < weights.size(); i++) {
			
				for(int j = 0; j < weights.get(i).size() - 1; j++){
					sum += (weights.get(i).get(j) * feature2.get(index).get(j));
				}
				
				sum += weights.get(i).get(weights.get(i).size() - 1);
				fy2.add(sum);
		
			}
			
			
		}

		
		for(int i = 0; i < weights.size(); i++) {
			
			for(int j = 0; j < weights.get(i).size(); j++) {
				
				System.out.print(weights.get(i).get(j) + "  ");
				
			}
			System.out.println();
		}
		System.out.println();
		
		
		for(int i = 0; i < fy2.size(); i++) {
			
			System.out.print(fy2.get(i) + "  ");
			
		}
		System.out.println();
		System.out.println();
*/
		
		
		
		
		

		
		
		
		
/*		
		for(int i = 0; i < weights.size(); i++) {
			
			for(int j = 0; j < weights.get(i).size(); j++) {
				
				System.out.print(weights.get(i).get(j) + "  ");
				
			}
			System.out.println();
		}
		System.out.println();
		

		
		for(int i = 0; i < weights.get(0).size(); i++) {
			int sum = 0;
			for(int j = 0; j < weights.size(); j++) {
				
				sum = sum + weights.get(j).get(i);
			
			}
			finalWeights.add(sum);
		}

		
		
//		for(int i = 0; i < weights.size(); i++) {
	    	
//	        for(int j = 0; j < weights.get(i).size(); j++) {
//	            sum.get(i) = sum.get(i) + f.get(i).get(j);
//	        	finalWeights.set(i, finalWeights.get(i) + weights.get(i).get(j));
//	        }
//	    }
		
		
		
		for(int i = 0; i < finalWeights.size(); i++) {
			
			System.out.print(finalWeights.get(i) + "  ");
			
		}
		System.out.println();
		
		for(int i = 0; i < feature.size(); i++) {
			int sum = 0;
			for(int j = 0; j < feature.get(i).size(); j++) {
				
				sum = sum + (feature.get(i).get(j) * finalWeights.get(j));
				
			}
			
			finalFeature.add(sum);
		}
		
		
		
		
		for(int i = 0; i < feature2.size(); i++) {
			int sum = 0;
			for(int j = 0; j < feature2.get(i).size(); j++) {
				
				sum = sum + (feature2.get(i).get(j) * finalWeights.get(j));
				
			}
			
			finalFeature2.add(sum);
		}
		

		int correct = 0;
		ArrayList<Integer> indicies = new ArrayList<Integer>();
		
		
		
		for(int i = 0; i < finalFeature2.size(); i++) {
			indicies.clear();
//			System.out.print(feature2.get(i) + "  ");
			for(int k = 0; k < 5; k++) {
				
				int diff = 10000;
				int index = 0;
				for(int j = 0; j < feature.size(); j++) {
				
					if(Math.abs(finalFeature2.get(i) - finalFeature.get(j)) < diff) {
						if(inArray(indicies, j) == false) {
							diff = Math.abs(finalFeature2.get(i) - finalFeature.get(j));
							index = j;
						}
					}
				
				}
//				System.out.print(feature.get(index) + "  " + trainLabels.get(index) + "  ");
				indicies.add(index);
			
			}
//			System.out.println();
			
			int num[] = {0,0,0,0,0,0,0,0,0,0};
			
			int face = 0;
			for(int k = 0; k < 5; k++) {
				if(trainLabels.get(indicies.get(k)) == 0) {
					num[0]++;
				}
				if(trainLabels.get(indicies.get(k)) == 1) {
					num[1]++;
				}
				if(trainLabels.get(indicies.get(k)) == 2) {
					num[2]++;
				}
				if(trainLabels.get(indicies.get(k)) == 3) {
					num[3]++;
				}
				if(trainLabels.get(indicies.get(k)) == 4) {
					num[4]++;
				}
				if(trainLabels.get(indicies.get(k)) == 5) {
					num[5]++;
				}
				if(trainLabels.get(indicies.get(k)) == 6) {
					num[6]++;
				}
				if(trainLabels.get(indicies.get(k)) == 7) {
					num[7]++;
				}
				if(trainLabels.get(indicies.get(k)) == 8) {
					num[8]++;
				}
				if(trainLabels.get(indicies.get(k)) == 9) {
					num[9]++;
				}
			}
			
			if(testLabels.get(i) == findmax(num)) {
				correct++;
			}
			
			
			
			
			
//			System.out.println(index + "         " + trainLabels.get(index));
//			indicies.add(index);
			
		}
		
		double answer = (double)correct / images2.size();
		answer = answer * 100;
		
//		long endtime = System.nanoTime();
		
//		System.out.println("Testing time: " + ((double)(endtime - starttime)/1000000000) + "s");
		
		System.out.println(answer + "%" );
		System.out.println();
		
		
		
		
		
		
		
		x++;
		
	}
		
}
		
		

		
		
		
		
		
		
		
		int correct = 0;
		
//		Random ran = new Random();
//		int r = ran.nextInt(100);
		
//		long starttime = System.nanoTime();
		
		for(int index = 0; index < images2.size(); index++) {
			
			int sum = 0;
			int answer;
			fy2.clear();
		for(int i = 0; i < weights.size(); i++) {
			
			for(int j = 0; j < weights.get(i).size() - 1; j++){
				sum += (weights.get(i).get(j) * feature2.get(index).get(j));
			}
				
			sum += weights.get(i).get(weights.get(i).size() - 1);
			fy2.add(sum);
		
			
		}
		
		 sum = getMax(fy2);
		 answer = fy2.indexOf(sum);
		// System.out.println("sup");
		// System.out.println("answer " + answer);
		 //System.out.println();
		 
//		 if(index >= r && index < r+20 && x == 10) {
//				printSingleDigit(images2.get(index));
//				System.out.println();
//				System.out.println();
//				System.out.println();
//				System.out.println(answer);
//				System.out.println();
//				System.out.println();
//				System.out.println();
//			}
			
			
			if(answer == getCorrect(index, "testlabels")) {
				correct++;
			}
			
		}
		
		double answer = (double)correct / images2.size();
		answer = answer * 100;
		
		//if(x == 10)
		
		
//		long endtime = System.nanoTime();
		
//		System.out.println("Testing time: " + ((double)(endtime - starttime)/1000000000) + "s");
		
		System.out.println(answer + "%" );
		System.out.println();
		//System.out.println(correct);
		
			
	
			
			x++;
*/			
			
			
			
		
		
		
		
			
			//printFaces();
			
			
	//System.out.println();
		
			
		//}
			
			
			
		
	


public static void runPerceptron() throws FileNotFoundException {
	
	long starttime = System.nanoTime();
	
	initializeWeights();
	
	while(true) {
			
			oldWeights.clear();
		for(int i = 0; i < weights.size(); i++) {
			
			oldWeights.add(weights.get(i));
	
		}
		//System.out.println("Here");
		
		for(int a = 0; a < tempImages.size(); a++) {
			//System.out.println(tempImages.size());
			fy.clear();
			int index = indices.get(a);
			int sum = 0;
			int answer;
		
			for(int i = 0; i < weights.size(); i++) {
				for(int j = 0; j < weights.get(i).size() - 1; j++){
				sum += (weights.get(i).get(j) * feature.get(index).get(j));
			}
			
			sum += weights.get(i).get(weights.get(i).size() - 1);
			fy.add(sum);
			}
			
			
			 sum = getMax(fy);
			 answer = fy.indexOf(sum);
/*			 
			 if(answer == 0) {
				 System.out.println("sup");
			 }
*/			
			int correct = getCorrect(index, "traininglabels");
			
			if(answer != correct) {
				
				//System.out.println("answer " + answer);	
		for(int i = 0; i < weights.get(answer).size() - 1; i++) {
		weights.get(answer).set(i, weights.get(answer).get(i) - feature.get(index).get(i));
					}
	weights.get(answer).set(weights.get(answer).size() - 1, weights.get(answer).get(weights.get(answer).size() - 1) - 1);
					
				
					
		for(int i = 0; i < weights.get(correct).size() - 1; i++) {
		weights.get(correct).set(i, weights.get(correct).get(i) + feature.get(index).get(i));
		}
	weights.get(correct).set(weights.get(correct).size() - 1, weights.get(correct).get(weights.get(correct).size() - 1) + 1);
					
				}
				
			
			
			
		}
		//System.out.println("Running");
			/*	System.out.print("Old ");
				for(int i = 0; i < oldWeights.size(); i++) {
					System.out.print(oldWeights.get(i) + " ");
				
				}
				System.out.println();
				System.out.print("New ");
				for(int i = 0; i < weights.size(); i++) {
					System.out.print(weights.get(i) + " ");
				
				}
				System.out.println();*/
		//System.out.println("here");
		if(terminate() == 1) {

			long endtime = System.nanoTime();
			
			System.out.println("Training time: " + ((double)(endtime - starttime)/1000000000) + "s");
			
			break;
		}
		
		

		
		
	}
	
	
}

public static void initializeFaces(String name, ArrayList<Integer[][]> image) throws FileNotFoundException {
	
	File file = new File(name);	
	Scanner line = new Scanner(file);
	int counter = 0;
	Integer temp1[][] = new Integer[rows][columns];
	
	while(line.hasNextLine()){
		
		String s = line.nextLine();
		
		Integer temp2[] = new Integer[columns];
		
		for(int i = 0; i < columns; i++) {
			if(s.charAt(i) == ' ') {
				temp2[i] = 0;
			}else{
				temp2[i] = 1;
			}
			
			
		}

		
		temp1[counter] = temp2;
		counter++;
		
		if(counter % rows == 0) {
			image.add(temp1);
			temp1 = new Integer[rows][columns];
			counter = 0;
			
		}
		
		
		
	}
	
	line.close();
	
	
}

public static int terminate() {
	
	int sumOld = 0;
	int sumNew = 0;
	
	for(int i = 0; i < weights.size(); i++) {
		for(int j = 0; j < weights.get(i).size() - 1; j++){
		sumNew += weights.get(i).get(j);
		}
	}
	
	for(int i = 0; i < weights.size(); i++) {
		for(int j = 0; j < weights.get(i).size() - 1; j++){
		sumOld += oldWeights.get(i).get(j);
		}
	}
	
	if(Math.abs(sumNew - sumOld) < 1) {
		return 1;
	}
	
	
	return 0;
}

public static int getCorrect(int index, String name) throws FileNotFoundException {
	
	File file = new File(name);	
	Scanner line = new Scanner(file);
	int counter = 0;
	
	while(line.hasNextLine()) {
		int correct = line.nextInt();
		
		if(counter == index) {
			line.close();
			return correct;
		}
		
		counter++;
		
		
	}
	
	line.close();
	return -1;
	
}

public static int getMax(ArrayList<Integer> f) {
	
	int max = f.get(0);
	int index = 0;
	
	for(int i = 1; i < f.size(); i++) {
		
		if(max < f.get(i)) {
			max = f.get(i);
			index = i;
		}
		
		
	}
	
	
	//System.out.println("here  " +  index);
	return max;
	
	
}

/*public static void initializeWeights() {// setting weights randomly from - to + of number units each image is
										//divided into
	
	Random ran = new Random();
	int r = ran.nextInt(2 * splitRows * splitColumns) - (splitRows * splitColumns) ;
	
	for(int i = 0; i < splitRows * splitColumns; i++) {
		weights.add(r);
		 r = ran.nextInt(2 * splitRows * splitColumns) - (splitRows * splitColumns);
	}
	
	weights.add(r);//adds bias
	
	
}*/

public static void initializeWeights() {// setting weights randomly from - to + of number units each image is
	//divided into

Random ran = new Random();
int r = ran.nextInt(2);

ArrayList<Integer> temp = new ArrayList<Integer>();

for(int i = 0; i < 10; i++){
	
	for(int j = 0; j < splitRows * splitColumns; j++) {
		//weights.get(i).add(r);
		temp.add(r);
		r = ran.nextInt(2);
}

//weights.get(i).add(r);//adds bias
	temp.add(r);
	//System.out.println(temp.size());
	weights.add(temp);
	//System.out.println("we size " + weights.get(i).size());
	temp = new ArrayList<Integer>();
	//System.out.println("   2we size " + weights.get(i).size());
	
}


}

public static void numberOfPixels(int index, ArrayList<ArrayList<Integer>> features, ArrayList<Integer[][]> image){//counts numbers of pixels and stores in them in features  according to the 
											//number of units each image is divided into
		
	int counter = 0;
	int r = rows/ splitRows;
	int c = columns/ splitColumns;
	ArrayList<Integer> temp = new ArrayList<Integer>();
	
	for(int i = 0; i < rows; i+=r){
		for(int j = 0; j < columns; j+=c) {
			for(int x = 0; x < r; x++) {
				for(int y = 0; y < c; y++) {
					if(image.get(index)[i + x][j + y] == 1) {
						counter++;
					}
				}
			}
			temp.add(counter);
			counter = 0;
		}
	}
		
		
		features.add(temp);
	
}


public static void printDigits(ArrayList<Integer[][]> image) {//Prints all faces
	for(int index = 0; index < image.size(); index++) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(image.get(index)[i][j]);
			}
			System.out.println();
		}
}
}

public static void printSingleDigit(Integer[][] image) {
	
	for(int i = 0; i < rows; i++) {
		for(int j = 0; j < columns; j++) {
			if(image[i][j] == 0){
				System.out.print(' ');
			}
			else {
				System.out.print('+');
			}
//			System.out.print(image[i][j]);
		}
		System.out.println();
	}
	
}

public static void printFeatures(int index) {
	//numberOfPixels(index);
	
	for(int i = 0; i < feature.get(index).size(); i++) {
		
		System.out.print(feature.get(index).get(i));
		
		if((i + 1) % splitColumns == 0) {
			System.out.println();
		}
		
		}

	}


public static boolean inArray(ArrayList<Integer> a, int j) {
	
	for(int i = 0; i < a.size(); i++) {
		
		if(a.get(i) == j) {
			return true;
		}
		
	}
	return false;
}


public static void fillLabels(String name, ArrayList<Integer> lables) throws FileNotFoundException {
	
	File file = new File(name);	
	Scanner line = new Scanner(file);
	
	while(line.hasNextLine()){
		
		String x = line.nextLine();
		int y = Integer.parseInt(x);
		lables.add(y);
		
		
	}
	
	line.close();
	
}



public static int findmax(int[] num) {
	
	int max = 0;
	for(int i = 0; i < num.length; i++) {
		
		if(num[i] > num[max]) {
			max = i;
		}
		
	}
	return max;
}


static ArrayList<Integer> sum(ArrayList<ArrayList<Integer>> f) {
    ArrayList<Integer> sum = new ArrayList<Integer>();
    for(int i = 0; i < sum.size(); i++) {
    	
        for(int j = 0; j < f.get(i).size(); j++) {
//            sum.get(i) = sum.get(i) + f.get(i).get(j);
        	sum.set(i, sum.get(i) + f.get(i).get(j));
        }
    }
    return sum;
}

/*
for(int i = 0; i < weights.get(0).size(); i++) {
	int sum = 0;
	for(int j = 0; j < weights.size(); j++) {
		
		sum = sum + weights.get(j).get(i);
	
	}
	finalWeights.add(sum);
}

*/
}