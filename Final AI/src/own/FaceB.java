package own;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FaceB {
	static ArrayList<Integer[][]> images = new ArrayList<Integer[][]>();

	 
	
	static int rows = 70;
	static int columns = 60;
	static int splitRows = 7;
	static int splitColumns = 60;
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
	static Double[][] totalCount = new Double[totalFeatures][maxNumber];
	static Double[][] totalCount2 = new Double[totalFeatures][maxNumber];
	static ArrayList<Integer[][]> tempImages = new ArrayList<Integer[][]>();
	
	public Double[][] build(int index){
		if(index == 1) {
		return totalCount;
		}else {
			return totalCount2;
		}
	}
	
	public void baysF() throws FileNotFoundException {

		initializeFaces("facedatatrain", images);
		initializeFaces("facedatatest", images2);
		storeLabels("facedatatrainlabels", "train");
		storeLabels("facedatatestlabels", "test");

		for (int i = 0; i < images.size(); i++) {
			numberOfPixels(i, feature, images);
		}
		

		for (int i = 0; i < images2.size(); i++) {
			numberOfPixels(i, feature2, images2);
		}
		
		double pyFace;
		double pyNotFace;
		
		double counter = 0;
		
		for(int i = 0; i < trainLabels.size(); i++) {
			if(trainLabels.get(i) == 1){
			counter++;
			}
	}
		pyFace = counter/ trainLabels.size();
		pyNotFace = 1 - pyFace;
				int x = 1;
		while(x <= 10) {
			
			indices.clear();
			tempImages.clear();
			 totalCount = new Double[totalFeatures][maxNumber];
			 totalCount2 = new Double[totalFeatures][maxNumber];
	for(int i = 0; i < (int)((double) images.size() *  ((double)x/10)); i++){//same image again or not?
				Random r = new Random();
				int index = (int) r.nextInt(images.size());
				while(indices.contains(index) == true) {
					index = (int) r.nextInt(images.size());
				}
				indices.add(index);
				tempImages.add(images.get(index));
				
				
				
			}	
	
	
		buildTable(0);
		buildTable(1);
	
		
		int correct = 0;
		for(int i = 0; i < feature2.size(); i++) {
			
			double multiF = 1;
			double multiNF = 1;
			int answer = -1;
			for(int j = 0; j < feature2.get(i).size();j++) {
				//System.out.println(feature2.get(i).get(j));
				double answer1 = totalCount[j][feature2.get(i).get(j)];
				
				multiF *= answer1;
				//System.out.println("here" + multiF);
				
				double answer2 = totalCount2[j][feature2.get(i).get(j)];
				
				multiNF *= answer2;
			
				
			}
			
			
			multiF *= pyFace;
			multiNF *= pyNotFace;
			
			if(multiF > multiNF) {
				answer = 1;
			}else {
				answer = 0;
			}
			
			if(answer == testLabels.get(i)) {
				correct++;
			}
			
			
			
		}
		
		double answer = (double)correct / images2.size();
		answer = answer * 100;
		
		
		System.out.println(answer + "%" );
		x++;
		}
		
		
	}
		
		
		
		


	public static void buildTable(int correct) {

		if (correct == 1) {

			Double sum[] = new Double[totalFeatures];

			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					totalCount[i][j] = 0.0;
				}
			}

			for (int index = 0; index < tempImages.size(); index++) {
				
				int i = indices.get(index);
				
				if (trainLabels.get(i) == 1) {
					for (int j = 0; j < feature.get(i).size(); j++) {

						totalCount[j][feature.get(i).get(j)]++;

					}
				}

			}

			for (int i = 0; i < totalFeatures; i++) {
				sum[i] = 0.0;
			}
			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					sum[i] += totalCount[i][j];
				}
			}

			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					double d =  totalCount[i][j] /  sum[i];

					 if(d == 0){
					 totalCount[i][j] = 0.000000000000000001;
					 }else {
					totalCount[i][j] = d;
					 }
				}
			}

			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
				//	System.out.print(totalCount[i][j] + " ");
				}
				//System.out.println();
			}

			double check = 0;
			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					check += totalCount[i][j];
				}
			//	System.out.println("check " + check);
				check = 0;
			}

		} else { ///////NOT FACE

			Double sum[] = new Double[totalFeatures];

			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					totalCount2[i][j] = 0.0;
				}
			}

			for (int index = 0; index < tempImages.size(); index++) {
				
				int i = indices.get(index);
				if (trainLabels.get(i) == 0) {
					for (int j = 0; j < feature.get(i).size(); j++) {
						//System.out.println(feature.get(i).get(j));
						totalCount2[j][feature.get(i).get(j)]++;

					}
				}

			}

			for (int i = 0; i < totalFeatures; i++) {
				sum[i] = 0.0;
			}
			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					sum[i] += totalCount2[i][j];
				}
			}

			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					double d =  totalCount2[i][j] / sum[i];

					 if(d == 0){
					totalCount2[i][j] = 0.000000000000000001;
					 }else {
					totalCount2[i][j] = d;
					 }
				}
			}

			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					//System.out.print(totalCount2[i][j] + " ");
				}
			//	System.out.println();
			}

			double check = 0;
			for (int i = 0; i < totalFeatures; i++) {
				for (int j = 0; j < maxNumber; j++) {
					check += totalCount2[i][j];
				}
				//System.out.println("check " + check);
				check = 0;
			}

		}

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
