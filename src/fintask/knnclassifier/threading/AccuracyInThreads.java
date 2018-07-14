package fintask.knnclassifier.threading;

import fintask.dataset.Dataset;
import fintask.knnclassifier.ClassifierKNN;

import java.util.ArrayList;

public class AccuracyInThreads {

    private ArrayList<Thread> threads;
    private  int NumThreads;
    private final ClassifierKNN classifier;
    private Dataset TestSet;
    private int neighbournum;

    public AccuracyInThreads(ClassifierKNN clsfr, int nThreads, Dataset dataset, int k){
        this.threads = new ArrayList<>();
        this.NumThreads=nThreads;
        this.TestSet=dataset;
        this.classifier=clsfr;
        this.neighbournum=k;
        this.classifier.setsize(dataset.getImages().size());
    }
    public boolean GoGoBF(){
        Threading();
        for (Thread thread : threads)  thread.start();
        try{ for (Thread thread : threads)  thread.join();  }
        catch(InterruptedException e){e.printStackTrace(); return false;}
        OutputConsoleResults();
        return true;
    }

    private void OutputConsoleResults() {
        int mist=classifier.getCurrMistakes();
        int checked=classifier.getChecked();
        double acc=100d*(checked-mist)/TestSet.getImages().size();
        System.out.print("\nMistakes "+ mist);
        System.out.print("\nChecked "+ classifier.getChecked());
        System.out.print("\nAccuracy "+ acc);
    }
    private void Threading(){
        Dataset[] devided=GetThreadSets();
        for(int i=0;i<NumThreads;i++){
            int index=i;
            Thread tr=new Thread(()->classifier.Accuracy(devided[index],neighbournum));
            threads.add(tr);
        }
    }
    private Dataset[] GetThreadSets(){   //devide dataset into parts, returned as array of Dataset[NumThreds]
        Dataset[] devided=new Dataset[NumThreads];
        for(int i=0;i<NumThreads;i++){
            devided[i]=new Dataset();
            devided[i]=TestSet.TornApart(i,NumThreads);
        }
        return devided;
    }

}
