package fintask.knnclassifier;

import fintask.dataset.Dataset;
import fintask.knnclassifier.distance.Distance;

import java.util.*;

public class ClassifierKNN {

    private final Distance distance;
    private final List<double[]> TrainImages = new ArrayList<>();
    private final List<String> TrainLabels = new ArrayList<>();
    private  int testsize;
    private  int mistakes;
    private  int checked;

    public ClassifierKNN(final Distance distance) {
        this.mistakes=0;
        this.checked=0;
        this.distance = distance;
    }

    public void SeedData(final Dataset dataset){
        for (int i = 0; i < dataset.getImages().size(); i++){
            TrainImages.add(dataset.getImages().get(i));
            TrainLabels.add(dataset.getLabels().get(i));
        }
    }

    public String Classify(final double[] imgs, final int k) {
        final Map<Double, List<String>> distanceLabelMap = DistanceLabelMap(imgs);
        final List<String> neighbors = Predictors(distanceLabelMap, k);
        return Consensus(neighbors);
    }

    private static List<String> Predictors(final Map<Double, List<String>> map, final int k) {
        final List<String> list = new ArrayList<>();
        for (final List<String> labels : map.values())
            if (list.addAll(labels) && list.size() > k)
                break;
        return list;
    }
    private String Consensus(final List<String> list) {
        final Map<String, Integer> map = new HashMap<>();
        int majority_count = 0;
        String majorityLabel = null;
        for (final String s : list) {
            final int temp = map.getOrDefault(s, 0) + 1;
            if (temp > majority_count) {
                majority_count = temp;
                majorityLabel = s;
            }
            map.put(s, temp);
        }
        return majorityLabel;
    }
    private Map<Double, List<String>> DistanceLabelMap(final double[] imgs) {
        final Map<Double, List<String>> map = new TreeMap<>();
        for (int i = 0; i < TrainLabels.size(); i++) {
            final double dist = distance.calculate(imgs, TrainImages.get(i));
            final List<String> labels = map.getOrDefault(dist, new ArrayList<>());
            labels.add(TrainLabels.get(i));
            map.put(dist, labels);
        }
        return map;
    }

    public final double Accuracy(final Dataset dataset, final int k) {
        int correct = 0;
        int size=dataset.getImages().size();
        for (int i = 0; i < size; i++) {
            checked++;
            if (Classify(dataset.getImages().get(i), k).equals(dataset.getLabels().get(i)))
                correct++;
            System.out.print("\rCompleted "+ PercentCompleted()+"%");
        }
        this.mistakes+=(size-correct);
        double acc= 1d*correct / size;
        return acc;
    }
    public double PercentCompleted(){
        double percents=100d*getChecked()/testsize;
        return percents;
    }
    public void setsize(int n){
        this.testsize=n;
    }
    public int getCurrMistakes(){
        return mistakes;
    }
    public int getChecked(){
        return checked;
    }
}
