package fintask.dataset;

import java.util.ArrayList;
import java.util.List;

 public class Dataset {

    private final List<double[]> Images=new ArrayList<>();
    private final List<String> Labels=new ArrayList<>();


    protected final void addItem(final double[] imgs, final String lbls){
        Images.add(imgs);
        Labels.add(lbls);
    }

    public Dataset TornApart(final int NumOfPart,final int TotalParts){
        Dataset devided= new Dataset();
        int size=this.getImages().size();
        int length=size/TotalParts;
        int startindex=NumOfPart*length;
        int endindex=(NumOfPart+1)*length;
        for(int i=startindex;i<endindex;i++){
            devided.addItem(this.getImages().get(i),this.getLabels().get(i));
        }
        return devided;
    }

    public List<double[]>getImages(){
        return Images;
    }
    public List<String>getLabels(){
        return Labels;
    }
}