package bayes_classifiers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Digits{
	static ArrayList<Integer[][]> images = new ArrayList<Integer[][]>();

	 
	
	static int rows = 28;
	static int columns = 28;
	static int splitRows = 7;
	static int splitColumns = 28;
	static ArrayList<ArrayList<Integer>> feature = new ArrayList<ArrayList<Integer>>();// number of pixels
	static ArrayList<Integer> indices = new ArrayList<Integer>();
	static ArrayList<Integer> trainLabels = new ArrayList<Integer>();
	static ArrayList<Integer> testLabels = new ArrayList<Integer>();
	static ArrayList<Integer[][]> images2 = new ArrayList<Integer[][]>();
	static ArrayList<ArrayList<Integer>> feature2 = new ArrayList<ArrayList<Integer>>();// number of pixels
	static int totalFeatures = splitRows * splitColumns;
	static int r = rows / splitRows;
	static int c = columns / splitColumns;
	static int maxNumber = (r * c) + 1;
	static ArrayList<Double[][]> totalCount = new ArrayList<Double[][]>();
	static ArrayList<Integer[][]> tempImages = new ArrayList<Integer[][]>();
	static ArrayList<Double> correctNum = new ArrayList<Double>();
	static ArrayList<Double> probY = new ArrayList<Double>();
	
	public static void main(String[] args) throws FileNotFoundException {

		initializeFaces("trainingimages", images);
		initializeFaces("testimages", images2);
		storeLabels("traininglabels", "train");
		storeLabels("testlabels", "test");

		for (int i = 0; i < images.size(); i++) {
			numberOfPixels(i, feature, images);
		}
		

		for (int i = 0; i < images2.size(); i++) {
			numberOfPixels(i, feature2, images2);
		}
		
		
		
		for(int number = 0; number < 10; number++) {
			probY.add(0.0);
			for(int i = 0; i < trainLabels.size(); i++) {
				if(trainLabels.get(i) == number){
					probY.set(number, probY.get(number) + 1);
					}
			}
		}
		
		
		for(int number = 0; number < 10; number++) {
			probY.set(number, probY.get(number)/trainLabels.size());
			
		}
		
		
		
		int x = 1;
		while(x <= 10) {
			
			
			indices.clear();
			tempImages.clear();
			// totalCount = new ArrayList<Double[][]>();
			 
			
			 
			for(int number = 0; number < 10; number++) {

			 totalCount.add(new Double[totalFeatures][totalFeatures]);
			 
			}
	for(int i = 0; i < (int)((double) images.size() *  ((double)x/10)); i++){//same image again or not?
				Random r = new Random();
				int index = (int) r.nextInt(images.size());
				while(indices.contains(index) == true) {
					index = (int) r.nextInt(images.size());
				}
				indices.add(index);
				tempImages.add(images.get(index));
				
				
				
			}	
	
	for(int number = 0; number < 10; number++) {
		buildTable(number);
	}
		
		int correct = 0;
		for(int i = 0; i < feature2.size(); i++) {
			
			double multiF = 1;
			
			int answer = -1;
		for(int number = 0; number < 10; number++){
			for(int j = 0; j < feature2.get(i).size();j++){
				//System.out.println(feature2.get(i).get(j));
				
				
					double answer1 = totalCount.get(number)[j][feature2.get(i).get(j)];
					
					multiF *= answer1;
					
					
				}
				
			multiF *= probY.get(number);
			correctNum.add(multiF);
			multiF = 1;
				
			
				
			}
			
			//System.out.println(correctNum.get(5));
			
			//multiF *= pyFace;
			//multiNF *= pyNotFace;
			
			answer = getIndex(correctNum); //return index of max digit prob 
			//System.out.println(answer);
			if(answer == testLabels.get(i)) {
				correct++;
			}
			
			correctNum.clear();
			
			
		}
		
		double answer = (double)correct / images2.size();
		answer = answer * 100;
		
		
		System.out.println(answer + "%" );
		x++;
		}
		
		
	}
		
		
		
		


	public static void buildTable(int correct) {

		Double temp[][] = new Double[totalFeatures][maxNumber];

			Double sum[] = new Double[totalFeatures];
		
			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					
					temp[i][j] = 0.0;
					
				}
			}

			for (int index = 0; index < tempImages.size(); index++) {
				
				int i = indices.get(index);
				//System.out.println(i);
				if (trainLabels.get(i) == correct){
					for (int j = 0; j < feature.get(i).size(); j++) {

						temp[j][feature.get(i).get(j)]++;

					}
				}

			}

			for (int i = 0; i < totalFeatures; i++) {
				sum[i] = 0.0;
			}
			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					sum[i] += temp[i][j];
				}
			}

			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					double d =  temp[i][j] /  sum[i];

					 if(d == 0){
						 temp[i][j] = 0.000000000000000001;
					 }else {
						 temp[i][j] = d;
					 }
				}
			}
			
			totalCount.set(correct,temp);

			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					//System.out.print(temp[i][j] + " ");
				}
				//System.out.println();
			}

			double check = 0;
			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					check += temp[i][j];
				}
				//System.out.println(" check " + check);
				check = 0;
			}
			
		}

	

	public static int getIndex(ArrayList<Double> f) {
		
		double max = f.get(0);
		int index = 0;
		
		for(int i = 1; i < f.size(); i++) {
			
			if(max < f.get(i)) {
				max = f.get(i);
				index = i;
			}
			
			
		}
		
		
		//System.out.println("here  " +  index);
		return index;
		
	}
	

	public static void initializeFaces(String name, ArrayList<Integer[][]> image) throws FileNotFoundException {

		File file = new File(name);
		Scanner line = new Scanner(file);
		int counter = 0;
		Integer temp1[][] = new Integer[rows][columns];

		while (line.hasNextLine()) {

			String s = line.nextLine();

			Integer temp2[] = new Integer[columns];

			for (int i = 0; i < columns; i++) {
				if (s.charAt(i) == ' ') {
					temp2[i] = 0;
				} else {
					temp2[i] = 1;
				}
			}

			temp1[counter] = temp2;
			counter++;

			if (counter % rows == 0) {
				image.add(temp1);
				temp1 = new Integer[rows][columns];
				counter = 0;

			}

		}

		line.close();

	}

	public static void numberOfPixels(int index, ArrayList<ArrayList<Integer>> features, ArrayList<Integer[][]> image) {// counts
		// the
		// number of units each image is divided into

		int counter = 0;
		int r = rows / splitRows;
		int c = columns / splitColumns;
		ArrayList<Integer> temp = new ArrayList<Integer>();

		for (int i = 0; i < rows; i += r) {
			for (int j = 0; j < columns; j += c) {
				for (int x = 0; x < r; x++) {
					for (int y = 0; y < c; y++) {
						if (image.get(index)[i + x][j + y] == 1) {
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

	public static void storeLabels(String name, String test) throws FileNotFoundException {

		File file = new File(name);
		Scanner line = new Scanner(file);
		
		if(test.equals("train")) {

		while (line.hasNext()) {
			int correct = line.nextInt();

			trainLabels.add(correct);

			}
		}else {
			
			while (line.hasNext()) {
				int correct = line.nextInt();

				testLabels.add(correct);
				
			}
			
			
		}

		line.close();

	}

	public static void printFaces(ArrayList<Integer[][]> image) {// Prints all faces
		for (int index = 0; index < image.size(); index++) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					System.out.print(image.get(index)[i][j]);
				}
				System.out.println();
			}
		}
	}

	public static void printFeatures(int index) {
		// numberOfPixels(index);

		for (int i = 0; i < feature.get(index).size(); i++) {

			System.out.print(feature.get(index).get(i) + " ");

			if ((i + 1) % splitColumns == 0) {
				System.out.println();
			}

		}

	}

}
