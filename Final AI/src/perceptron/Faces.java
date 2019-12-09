package perceptron;

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
	static ArrayList<Integer> oldWeights = new ArrayList<Integer>();
	
	
	static ArrayList<Integer[][]> images2 = new ArrayList<Integer[][]>();
	static ArrayList<ArrayList<Integer>> feature2 =  new ArrayList<ArrayList<Integer>>();//number of pixels
	
	
public static void main(String[] args) throws FileNotFoundException{
		
		

//	System.out.println(getCorrect(442,"facedatatrainlabels"));
//		for(int ww = 0; ww < 49; ww++) {

	
//		for(int ww = 0; ww < 100; ww++) {

			
			images.clear();
			images2.clear();
			weights.clear();
			feature.clear();
			feature2.clear();
			tempImages.clear();
			indices.clear();
			oldWeights.clear();
			
		initializeFaces("facedatatrain", images);
		initializeFaces("facedatatest", images2);
		
		for(int i = 0; i < images.size(); i++) {
			numberOfPixels(i, feature, images);
		}
		
		for(int i = 0; i < images2.size(); i++) {
			numberOfPixels(i, feature2, images2);
		}
		//System.out.println(images2.size());
		
		
		;
		int x = 1;
	
		
		while(x <= 10) {
			
			
			
			indices.clear();
			tempImages.clear(); 
			weights.clear();
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
		runPerceptron(); 
		
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
			
			
		
	}
}
		
		
		
			
			//printFaces();
			
			
			//System.out.println();
			
			
//		}
			
			
			


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
	
	while(line.hasNext()) {
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

}