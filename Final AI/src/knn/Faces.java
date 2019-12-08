package knn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Faces {
	
	static ArrayList<Integer[][]> images = new ArrayList<Integer[][]>();
	static ArrayList<Integer[][]> tempImages = new ArrayList<Integer[][]>();
	static ArrayList<Integer> indices = new ArrayList<Integer>();
	static int rows = 70; 
	static int columns = 60;
	static int splitRows = 35;
	static int splitColumns = 30;
	static ArrayList<Integer> weights = new ArrayList<Integer>();
	static ArrayList<ArrayList<Integer>> feature =  new ArrayList<ArrayList<Integer>>();//number of pixels
	static ArrayList<Integer> finalFeature = new ArrayList<Integer>();
	static ArrayList<Integer> oldWeights = new ArrayList<Integer>();
	static ArrayList<Integer> trainLabels = new ArrayList<Integer>();
	
	
	static ArrayList<Integer[][]> images2 = new ArrayList<Integer[][]>();
	static ArrayList<ArrayList<Integer>> feature2 =  new ArrayList<ArrayList<Integer>>();//number of pixels
	static ArrayList<Integer> finalFeature2 = new ArrayList<Integer>();
	static ArrayList<Integer> testLabels = new ArrayList<Integer>();
	
public static void main(String[] args) throws FileNotFoundException{
		
		
	
//		for(int ww = 0; ww < 100; ww++) {
			
			images.clear();
			images2.clear();
			weights.clear();
			feature.clear();
			feature2.clear();
			tempImages.clear();
			indices.clear();
			oldWeights.clear();
			finalFeature.clear();
			finalFeature2.clear();
			
		initializeFaces("facedatatrain", images);
		initializeFaces("facedatatest", images2);
		
		for(int i = 0; i < images.size(); i++) {
			numberOfPixels(i, feature, images);
		}
		
		for(int i = 0; i < images2.size(); i++) {
			numberOfPixels(i, feature2, images2);
		}
		//System.out.println(images2.size());
		fillLabels("facedatatrainlabels", trainLabels);
		fillLabels("facedatatestlabels", testLabels);
		
		
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
//			Random r = new Random();
//			int index = (int) r.nextInt(images.size());
//			while(indices.contains(index) == true) {
//				index = (int) r.nextInt(images.size());
//			}
			indices.add(i);
			tempImages.add(images.get(i));
		}	
		//System.out.println(tempImages.size());
		
		
		runPerceptron(); 
		
		long starttime = System.nanoTime();
		
		for(int i = 0; i < feature.size(); i++) {
			int sum = 0;
			for(int j = 0; j < feature.get(i).size(); j++) {
				
				sum = sum + (feature.get(i).get(j) * weights.get(j));
				
			}
			
			finalFeature.add(sum);
		}
		
		
		for(int i = 0; i < feature2.size(); i++) {
			int sum = 0;
			for(int j = 0; j < feature2.get(i).size(); j++) {
				
				sum = sum + (feature2.get(i).get(j) * weights.get(j));
				
			}
			
			finalFeature2.add(sum);
		}
		
		
		
		int correct = 0;
		ArrayList<Integer> indicies = new ArrayList<Integer>();
		
		
		for(int i = 0; i < finalFeature2.size(); i++) {
			indicies.clear();
//			System.out.print(feature2.get(i) + "  ");
			for(int k = 0; k < 3; k++) {
				
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
			
			int face = 0;
			for(int k = 0; k < 3; k++) {
				if(trainLabels.get(indicies.get(k)) == 1) {
					face++;
				}
			}
			
			if(face > 1) {
				if(testLabels.get(i) == 1) {
					
					correct++;
				}
			}
			else {
				if(testLabels.get(i) == 0) {
					correct++;
				}
			}
			
			
			
			
			
//			System.out.println(index + "         " + trainLabels.get(index));
//			indicies.add(index);
			
		}
		
		double answer = (double)correct / images2.size();
		answer = answer * 100;
		
		long endtime = System.nanoTime();
		
		System.out.println("Testing time: " + ((double)(endtime - starttime)/1000000000) + "s");
		
		System.out.println(answer + "%" );
		System.out.println();
		
		x++;
		
	}
		
		
		
		
		
		
		
/*		
		
		int correct = 0;
		
		Random ran = new Random();
		int r = ran.nextInt(100);
		
		long starttime = System.nanoTime();
		for(int index = 0; index < images2.size(); index++) {
			
			int sum = 0;
			int answer;
			
			
			for(int i = 0; i < weights.size() - 1; i++) {
				sum += (weights.get(i) * feature2.get(index).get(i));
			}
				
			sum += weights.get(weights.size() - 1);
			
			if(sum >= 0){
				answer = 1;
			}else{
				answer = 0;
			}
			
			
			
			if(index >= r && index < r+20 && x == 10) {
				printSingleFace(images2.get(index));
				System.out.println();
				System.out.println();
				System.out.println();
				if(answer == 1) {
					System.out.println("FACE");
				}
				else {
					System.out.println("NOT FACE");
				}
				System.out.println();
				System.out.println();
				System.out.println();
			}
			
			if(answer == getCorrect(index, "facedatatestlabels")) {
				correct++;
			}
			
		}
		
		double answer = (double)correct / images2.size();
		answer = answer * 100;
		
		//if(x == 10)
		
		
		long endtime = System.nanoTime();
		
		System.out.println("Testing time: " + ((double)(endtime - starttime)/1000000000) + "s");
		
		System.out.println(answer + "%" );
		System.out.println();
		//System.out.println(correct);
		
			
			
			x++;
			
	
*/			
			
		}
			
			
		
	


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
			int index = indices.get(a);
			int sum = 0;
			int answer;
			
			for(int i = 0; i < weights.size() - 1; i++) {
				sum += (weights.get(i) * feature.get(index).get(i));
			}
			
			sum += weights.get(weights.size() - 1);
			
			if(sum >= 0){
				answer = 1;
			}else{
				answer = 0;
			}
			
			int correct = getCorrect(index, "facedatatrainlabels");
			
			if(answer != correct) {
				if(sum >= 0) {
					
					for(int i = 0; i < weights.size() - 1; i++) {
						weights.set(i, weights.get(i) - feature.get(index).get(i));
					}
				weights.set(weights.size() - 1, weights.get(weights.size() - 1) - 1);
					
				}else{
					
					for(int i = 0; i < weights.size() - 1; i++) {
						weights.set(i, weights.get(i) + feature.get(index).get(i));
					}
				weights.set(weights.size() - 1, weights.get(weights.size() - 1) + 1);
					
				}
				
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
		sumNew += weights.get(i);
	}
	
	for(int i = 0; i < oldWeights.size(); i++) {
		sumOld += oldWeights.get(i);
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
int r = ran.nextInt(10);


for(int i = 0; i < splitRows * splitColumns; i++) {
weights.add(r);
r = ran.nextInt(10);
}

weights.add(r);//adds bias


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


public static void printFaces(ArrayList<Integer[][]> image) {//Prints all faces
	for(int index = 0; index < image.size(); index++) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(image.get(index)[i][j]);
			}
			System.out.println();
		}
}
}

public static void printSingleFace(Integer[][] image) {
	
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


}

































/*
package knn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Faces {
	
	static ArrayList<Integer[][]> images = new ArrayList<Integer[][]>();
	static ArrayList<Integer> feature =  new ArrayList<Integer>();
//	static ArrayList<ArrayList<Integer>> newfeature =  new ArrayList<ArrayList<Integer>>();
	static ArrayList<Integer> trainLabels = new ArrayList<Integer>();
//	static ArrayList<Double> mainFeature = new ArrayList<Double>();
	static int rows = 70; 
	static int columns = 60;
	static int splitRows = 7;
	static int splitColumns = 6;
	static ArrayList<Integer[][]> images2 = new ArrayList<Integer[][]>();
	static ArrayList<Integer> feature2 =  new ArrayList<Integer>();
//	static ArrayList<ArrayList<Integer>> newfeature2 =  new ArrayList<ArrayList<Integer>>();
	static ArrayList<Integer> testLabels = new ArrayList<Integer>();
//	static ArrayList<Double> mainFeature2 = new ArrayList<Double>();
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException{
		
		images.clear();
		images2.clear();
		feature.clear();
		feature2.clear();
//		mainFeature.clear();
//		mainFeature2.clear();
		trainLabels.clear();
		testLabels.clear();
		
		initializeFaces("facedatatrain", images);
		initializeFaces("facedatatest", images2);
		fillFeature(images, feature);
		fillFeature(images2, feature2);
		fillLabels("facedatatrainlabels", trainLabels);
		fillLabels("facedatatestlabels", testLabels);
		
		int newFeature[][] = new int[3][feature.size()];
		int newFeature2[][] = new int[3][feature2.size()];
		
		
		
		for(int i = 0; i < feature.size(); i++) {
			
			newFeature[0][i] = i;
			newFeature[1][i] = feature.get(i);
			newFeature[2][i] = trainLabels.get(i);
			
		}
		
		for(int i = 0; i < feature2.size(); i++) {
			
			newFeature2[0][i] = i;
			newFeature2[1][i] = feature2.get(i);
			newFeature2[2][i] = testLabels.get(i);
			
		}
		
		
//		sort2d(newFeature);
//		sort2d(newFeature2);
		
		
		
		
		
		
		
//		newfeature.add(feature);
//		newfeature2.add(feature2);
		
		
		
		
//		sort(feature);
//		sort(feature2);
		
		int correct = 0;
		ArrayList<Integer> indicies = new ArrayList<Integer>();
		
		
		for(int i = 0; i < feature2.size(); i++) {
			indicies.clear();
			System.out.print(feature2.get(i) + "  ");
			for(int k = 0; k < 5; k++) {
				
				int diff = 10000;
				int index = 0;
				for(int j = 0; j < feature.size(); j++) {
				
					if(Math.abs(feature2.get(i) - feature.get(j)) < diff) {
						if(inArray(indicies, j) == false) {
							diff = Math.abs(feature2.get(i) - feature.get(j));
							index = j;
						}
					}
				
				}
				System.out.print(feature.get(index) + "  " + trainLabels.get(index) + "  ");
				indicies.add(index);
			
			}
			System.out.println();
			
			int face = 0;
			for(int k = 0; k < 5; k++) {
				if(trainLabels.get(indicies.get(k)) == 1) {
					face++;
				}
			}
			
			if(face > 2) {
				if(testLabels.get(i) == 1) {
					
					correct++;
				}
			}
			else {
				if(testLabels.get(i) == 0) {
					correct++;
				}
			}
			
			
			
			
			
//			System.out.println(index + "         " + trainLabels.get(index));
//			indicies.add(index);
			
		}
		
		double answer = (double)correct / images2.size();
		answer = answer * 100;
		
		System.out.println(answer + "%" );
		
	}
		
		
		
//		lastFeature(feature, mainFeature);
//		lastFeature(feature2, mainFeature2);
//		sort(mainFeature);
//		sort(mainFeature2);
		

		


		
		
		int indicies[][] = new int[mainFeature2.size()][5];
		
		for(int i = 0; i < mainFeature2.size(); i++) {
	
			double diff = 100;
			int index = 0;
			for(int j = 0; j < mainFeature.size(); j++) {
				
				
				if(Math.abs(mainFeature2.get(i) - mainFeature.get(j)) < diff) {
					diff = Math.abs(mainFeature2.get(i) - mainFeature.get(j));
					index = j;
				}
				
			}
			
			if(index <= 4) {
				indicies[i][0] = index;
				indicies[i][1] = index+1;
				indicies[i][2] = index+2;
				indicies[i][3] = index+3;
				indicies[i][4] = index+4;	
				
			}
			else if(index >= mainFeature.size()-4) {
				indicies[i][0] = index-4;
				indicies[i][1] = index-3;
				indicies[i][2] = index-2;
				indicies[i][3] = index-1;
				indicies[i][4] = index;	
				
			}
			else {
				indicies[i][0] = index-2;
				indicies[i][1] = index-1;
				indicies[i][2] = index-0;
				indicies[i][3] = index+1;
				indicies[i][4] = index+2;	
				
			}
				
			
		}

		for(int i = 0; i < mainFeature2.size(); i++) {
			for(int j = 0; j < 5; j++) {
				System.out.print(indicies[i][j] + " ");
			}
			System.out.println();
		}
		

		
		
		
		
		
		
		
	
	
	
	
	
	
	public static boolean inArray(ArrayList<Integer> a, int j) {
		
		for(int i = 0; i < a.size(); i++) {
			
			if(a.get(i) == j) {
				return true;
			}
			
		}
		return false;
	}

	
	
	public static void sort2d(int[][] a) {
		
		for(int i = 0; i < a[1].length; i++) {
			
			int minIndex = i;
			
			for(int j = i; j < a[1].length; j++) {
				
				if(a[1][j] < a[1][minIndex]) {
					minIndex = j;
				}
				
			}
			int temp1 = a[0][minIndex];
			int temp2 = a[1][minIndex];
			int temp3 = a[2][minIndex];
			
			a[0][minIndex] = a[0][i];
			a[1][minIndex] = a[1][i];
			a[2][minIndex] = a[2][i];
			
			a[0][i] = temp1;
			a[1][i] = temp2;
			a[2][i] = temp3;	
			
		}
		
	}
	
	
	public static void sort(ArrayList<Integer> a) {
		
		for(int i = 0; i < a.size(); i++) {
			
			int minIndex = i;
			
			for(int j = i+1; j < a.size(); j++) {
				
				if(a.get(j) < a.get(minIndex)) {
					minIndex = j;
				}
				
			}
			
			int temp = a.get(minIndex);
			a.set(minIndex, a.get(i));
			a.set(i, temp);
			
		}	
	}
	
	
	public static void lastFeature(ArrayList<ArrayList<Integer>> features, ArrayList<Double> mainFeatures) {
		
		for(int index = 0; index < features.size(); index++){
			double sum = 0;
			for(int i = 0; i < features.get(index).size(); i++) {
				
				sum = sum + ((double)features.get(index).get(i)/(i+1));
				
			}
			
			mainFeatures.add(sum);
			
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
	
	
	
	

	public static void fillFeature(ArrayList<Integer[][]> image, ArrayList<Integer> features) {
		
		for(int index = 0; index < image.size(); index++) {
			int numBlackPix = 0;
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {
					if(image.get(index)[i][j] == 1) {
						numBlackPix++;
					}
				}
			}
			
			features.add(numBlackPix);
			
		}
		
		
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

}
*/