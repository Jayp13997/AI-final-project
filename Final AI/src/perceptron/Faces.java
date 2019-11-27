package perceptron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Faces {
	
	static ArrayList<Integer[][]> images = new ArrayList<Integer[][]>();
	static int rows = 70; 
	static int columns = 60;
	static int splitRows = 14;
	static int splitColumns = 12;
	static ArrayList<Integer> weights = new ArrayList<Integer>();
	static ArrayList<ArrayList<Integer>> feature =  new ArrayList<ArrayList<Integer>>();//number of pixels
	static ArrayList<Integer> oldWeights = new ArrayList<Integer>();
	
	
public static void main(String[] args) throws FileNotFoundException{
		
		
		initializeFaces("facedatavalidation");
		//printFaces();
		runPerceptron(); 
		
			
			//printFaces();
			
			
			int correct = 0;
			
			for(int index = 0; index < images.size(); index++) {
				
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
				
				if(answer == getCorrect(index, "facedatavalidationlabels")) {
					correct++;
				}
				
			}
			
			double answer = (double)correct / images.size();
			answer = answer * 100;
			
			System.out.println(answer + "%" );
			System.out.println(correct);
			
			
			
			
			
			
			
		
	}


public static void runPerceptron() throws FileNotFoundException {
	
	for(int i = 0; i < images.size(); i++) {
		numberOfPixels(i);
	}
	
	initializeWeights();
	
	while(true) {
			oldWeights.clear();
		for(int i = 0; i < weights.size(); i++) {
			
			oldWeights.add(weights.get(i));
		}
		System.out.println("Here");
		
		for(int index = 0; index < images.size(); index++) {
			
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
			
			int correct = getCorrect(index, "facedatavalidationlabels");
			
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
				System.out.print("Old ");
				for(int i = 0; i < oldWeights.size(); i++) {
					System.out.print(oldWeights.get(i) + " ");
				
				}
				System.out.println();
				System.out.print("New ");
				for(int i = 0; i < weights.size(); i++) {
					System.out.print(weights.get(i) + " ");
				
				}
				System.out.println();
		
		if(terminate() == 1) {
			break;
		}
		
		

		
		
	}
	
	
}

public static void initializeFaces(String name) throws FileNotFoundException {
	
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
			images.add(temp1);
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
	
	if(Math.abs(sumNew - sumOld) < 10) {
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
int r = ran.nextInt(100) - 50;

for(int i = 0; i < splitRows * splitColumns; i++) {
weights.add(r);
r = ran.nextInt(10) - 5;
}

weights.add(r);//adds bias


}

public static void numberOfPixels(int index){//counts numbers of pixels and stores in them in features  according to the 
											//number of units each image is divided into
		
	int counter = 0;
	int r = rows/ splitRows;
	int c = columns/ splitColumns;
	ArrayList<Integer> temp = new ArrayList<Integer>();
	
	for(int i = 0; i < rows; i+=r){
		for(int j = 0; j < columns; j+=c) {
			for(int x = 0; x < r; x++) {
				for(int y = 0; y < c; y++) {
					if(images.get(index)[i + x][j + y] == 1) {
						counter++;
					}
				}
			}
			temp.add(counter);
			counter = 0;
		}
	}
		
		
		feature.add(temp);
	
}


public static void printFaces() {//Prints all faces
	for(int index = 0; index < images.size(); index++) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(images.get(index)[i][j]);
			}
			System.out.println();
		}
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
