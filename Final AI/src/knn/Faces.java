package knn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Faces{
	
	static ArrayList<Integer[][]> images = new ArrayList<Integer[][]>();
	static ArrayList<Integer[][]> tempImages = new ArrayList<Integer[][]>();
	static ArrayList<Integer> indices = new ArrayList<Integer>();
	static int rows = 70; 
	static int columns = 60;
	static int splitRows = 35;
	static int splitColumns = 30;
	
	static ArrayList<ArrayList<Integer>> feature =  new ArrayList<ArrayList<Integer>>();//number of pixels
 
	static ArrayList<Integer> trainLabels = new ArrayList<Integer>();
	
	static int k = 10;
	
	static ArrayList<Integer[][]> images2 = new ArrayList<Integer[][]>();
	static ArrayList<ArrayList<Integer>> feature2 =  new ArrayList<ArrayList<Integer>>();//number of pixels
	static ArrayList<Integer> testLabels = new ArrayList<Integer>();
	static ArrayList<Double> distance = new ArrayList<Double>();
	
public static void main(String[] args) throws FileNotFoundException{
		
		
			
	initializeFaces("facedatatrain", images);
	initializeFaces("facedatatest", images2);
	storeLabels("facedatatrainlabels", "train");
	storeLabels("facedatatestlabels", "test");
		
		for(int i = 0; i < images.size(); i++) {
			numberOfPixels(i, feature, images);
		}
		
		for(int i = 0; i < images2.size(); i++) {
			numberOfPixels(i, feature2, images2);
		}

		
		
		int x = 1;
	
		
		while(x <= 10) {
			
			
			indices.clear();
			tempImages.clear(); 
			distance.clear();
			//System.out.println(images.size());
			//System.out.println("here " + (int)( (double) images.size() *  ((double)x/10)));
			
			for(int i = 0; i < (int)((double) images.size() *  ((double)x/10)); i++){//same image again or not?
			Random r = new Random();
				int index = (int) r.nextInt(images.size());
				while(indices.contains(index) == true) {
					index = (int) r.nextInt(images.size());
			}
				indices.add(index);
				tempImages.add(images.get(index));
			}	
			//System.out.println(tempImages.size());
			//runPerceptron(); 
			System.out.println("RUNNING: " + (x * 10) + "%");
			differences();
			x++;
			k = 10;
			System.out.println();
			}
			
		
		}

public static void differences() {
	
	int correct = 0;
	int tempInd = 0;
	for(int index = 0; index < images2.size(); index++) {
		
		for(int trainI = 0; trainI < tempImages.size(); trainI++) {
			double sum = 0;
			 tempInd = indices.get(trainI);//actual index
				for(int f = 0; f < feature.get(tempInd).size(); f++) {
					 sum += Math.pow((feature.get(tempInd).get(f) - feature2.get(index).get(f)), 2);
					
					}
				distance.add(Math.sqrt(sum));
				sum = 0;
					
				}
		//System.out.println(testLabels.get(index));
			int n = answer();
			while(n == -1) {
				k+=5;
				//System.out.println("running");
				n = answer();
			}
			
			if(n == testLabels.get(index)) {
				
				correct++;
			}
		distance.clear();
		k = 10;
	}
			
	double answer = (double)correct / images2.size();
	answer = answer * 100;
	
	System.out.println(correct);
	System.out.println("size " + images2.size());
	
	System.out.println(answer + "%" );
			
		}


public static int answer() {
	
	
	ArrayList<Integer> temp = new ArrayList<Integer>();
	
	 ArrayList<Double> dis = new ArrayList<Double>();
	 
	 for(int i = 0; i < distance.size(); i++) {
		 dis.add(distance.get(i));
	 }
	 
	for(int i = 0; i < k; i++) {
			double min = dis.get(0);
			int ind = 0;
			for(int j = 0; j < dis.size(); j++) {
				if(min > dis.get(j)) {
					min = dis.get(j);
						ind = j;
					}
				}
			int actualIndex = indices.get(ind);
			temp.add(trainLabels.get(actualIndex));
			dis.set(ind, Double.MAX_VALUE);
		
	}
	
	
	
	
	int freq[][] = new int[k][1];
	ArrayList<Integer> done = new ArrayList<Integer>();
	
	for(int i = 0; i < temp.size(); i++){
		
		int fr = Collections.frequency(temp, temp.get(i));
		
		if(done.contains(temp.get(i))){
			freq[i][0] = -1;
			
		}else {
			freq[i][0] = fr;
			done.add(temp.get(i));
	
	}
		
		
	}
	
	
	/*for(int i = 0; i < k; i++) {
		System.out.print(freq[i][0] + " ");
		
	}
	
	System.out.println("\nnew");*/
	
	int max = freq[0][0];
	int mainInd = 0;
		for(int i = 1; i < k; i++) {
			if(max < freq[i][0]) {
				max = freq[i][0];
				mainInd = i;
			}else if(max == freq[i][0]) {
				//System.out.println("size " + max);
				return -1;
			}
		}
	
		
	
		return temp.get(mainInd);
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



}