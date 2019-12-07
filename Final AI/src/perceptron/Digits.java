package perceptron;

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
	static int splitRows = 28;
	static int splitColumns = 14;
	static ArrayList<ArrayList<Double>> weights = new ArrayList<ArrayList<Double>> ();
	static ArrayList<ArrayList<Integer>> feature =  new ArrayList<ArrayList<Integer>>();//number of pixels
	static ArrayList<ArrayList<Double>> oldWeights = new ArrayList<ArrayList<Double>>();
	static ArrayList<Double> fy = new ArrayList<Double>();
	static ArrayList<Double> fy2 = new ArrayList<Double>();
	
	
	static ArrayList<Integer[][]> images2 = new ArrayList<Integer[][]>();
	static ArrayList<ArrayList<Integer>> feature2 =  new ArrayList<ArrayList<Integer>>();//number of pixels
	
	
public static void main(String[] args) throws FileNotFoundException{
		
		
	ArrayList<Integer> no = new ArrayList<Integer>();
	
	
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
		//printDigits(images);
		
		for(int i = 0; i < images.size(); i++) {
			numberOfPixels(i, feature, images);
		}
		
		for(int i = 0; i < images2.size(); i++) {
			numberOfPixels(i, feature2, images2);
		}
		//System.out.println(images2.size());
		
		System.out.println(images.size());
		
		int x = 1;
	
		
		while(x <= 10) {
			
			indices.clear();
			tempImages.clear(); 
			weights.clear();
			//System.out.println(images.size());
			//System.out.println("here " + (int)( (double) images.size() *  ((double)x/10)));
			System.out.println((double)x * 10  + "%");
			System.out.println("image size: " + images.size() *  ((double)x/10));
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
		
		for(int index = 0; index < images2.size(); index++) {
			
			double sum = 0;
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
			
			
			if(answer == getCorrect(index, "testlabels")) {
				correct++;
			}
			
		}
		
		double answer = (double)correct / images2.size();
		answer = answer * 100;
		
		//if(x == 10)
		System.out.println(answer + "%" );
		System.out.println();
		
			
			
			x++;
			
			
			
		}
		
		
		
			
			//printFaces();
			
			
	//System.out.println();
		
			
		//}
			
			
			
		
	}


public static void runPerceptron() throws FileNotFoundException {
	
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
			double sum = 0;
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
	
	if(Math.abs(sumNew - sumOld) == 0) {
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

public static double getMax(ArrayList<Double> fy3) {
	
	Double max = fy3.get(0);
	int index = 0;
	
	for(int i = 1; i < fy3.size(); i++) {
		
		if(max < fy3.get(i)) {
			max = fy3.get(i);
			index = i;
		}
		
		
	}
	
	
	//System.out.println("here  " +  index);
	return max;
	
	
}

public static int getIndex(ArrayList<Integer> f) {
	
	int max = f.get(0);
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
double r = 0;

ArrayList<Double> temp = new ArrayList<Double>();

for(int i = 0; i < 10; i++){
	
	for(int j = 0; j < splitRows * splitColumns; j++) {
		//weights.get(i).add(r);
		r = ran.nextInt(2) - 1;
		temp.add(r);
		
}

//weights.get(i).add(r);//adds bias
	r = ran.nextInt(2) - 1;
	temp.add(r);
	//System.out.println(temp.size());
	weights.add(temp);
	//System.out.println("we size " + weights.get(i).size());
	temp = new ArrayList<Double>();
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