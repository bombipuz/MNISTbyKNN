package fintask;

import fintask.dataset.Dataset;
import fintask.dataset.MNISTset;
import fintask.knnclassifier.distance.Euclidean;
import fintask.knnclassifier.threading.AccuracyInThreads ;
import fintask.knnclassifier.ClassifierKNN;
import fintask.knnclassifier.distance.Distance;


import java.io.*;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws IOException{
        String Train_Label_Path="data\\loadres\\train-labels.idx1-ubyte";
        String Train_Image_Path="data\\loadres\\train-images.idx3-ubyte";
        String Test_Label_Path="data\\loadres\\t10k-labels.idx1-ubyte";
        String Test_Image_Path="data\\loadres\\t10k-images.idx3-ubyte";
        int k=4;
        int NumberOfThreads=5;
            System.out.println("Program started...");
            System.out.println("Parsing from MNIST...\n\t...can take few minutes...");
            Dataset trainDataset=DatasetParsing(Train_Label_Path,Train_Image_Path);
            System.out.print("Train Data parsed!");
            Dataset testDataset = DatasetParsing(Test_Label_Path,Test_Image_Path);
            System.out.println("\tTest Data parsed!");

        ClassifierKNN knn=new ClassifierKNN(new Euclidean()); // or new Manhattan(), or another one
        knn.SeedData(trainDataset);
        System.out.println("Train data loaded to classifier");
        System.out.println("Classifying test set...");
        //knn.Accuracy(testDataset,k);
        AccuracyInThreads acc=new AccuracyInThreads(knn,NumberOfThreads,testDataset,k);
        acc.GoGoBF();
    }

    static public Dataset DatasetParsing(String lblpath,String imgpath)throws IOException{
        InputStream ImagesStream = new FileInputStream(imgpath);
        InputStream LabelsStream = new FileInputStream(lblpath);
        Dataset dtst=new MNISTset().parser(LabelsStream,ImagesStream);
        return dtst;
    }

}
