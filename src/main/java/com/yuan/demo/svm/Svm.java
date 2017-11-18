package com.yuan.demo.svm;

import java.io.IOException;



public class Svm {
	 public static void main(String[] args) throws IOException {  
	        // TODO Auto-generated method stub  
	        //Test for svm_train and svm_predict  
	        //svm_train:   
	        //    param: String[], parse result of command line parameter of svm-train  
	        //    return: String, the directory of modelFile  
	        //svm_predect:  
	        //    param: String[], parse result of command line parameter of svm-predict, including the modelfile  
	        //    return: Double, the accuracy of SVM classification  
	        String[] trainArgs = {"svm/breast-cancer"};//directory of training file  
	        String modelFile = svm_train.main(trainArgs);  
//	        String[] testArgs = {"breast-cancer_scale", "breast-cancer.model", "breast-cancer_output"};//directory of test file, model file, result file  
//	        Double accuracy = svm_predict.main(testArgs);  
//	        System.out.println("SVM Classification is done! The accuracy is " + accuracy);  

	        //Test for cross validation  
//	        String[] crossValidationTrainArgs = {"-v", "10", "breast-cancer"};// 10 fold cross validation  
//	        modelFile = svm_train.main(crossValidationTrainArgs);  
//	        System.out.print("Cross validation is done! The modelFile is " + modelFile);  
		 
//		 	String[] trainArgs = {"libsvm"};//directory of training file  
//	        String modelFile = svm_train.main(trainArgs);  
//	        String[] testArgs = {"libsvmtest", "libsvm.model", "libsvm.output"};//directory of test file, model file, result file  
//	        Double accuracy = svm_predict.main(testArgs);  
//	        System.out.println("SVM Classification is done! The accuracy is " + accuracy);  

	        //Test for cross validation  
//	        String[] crossValidationTrainArgs = {"-v", "3", "libsvm"};// 10 fold cross validation  
//	        modelFile = svm_train.main(crossValidationTrainArgs);  
//	        System.out.print("Cross validation is done! The modelFile is " + modelFile);  
	    }  
}
