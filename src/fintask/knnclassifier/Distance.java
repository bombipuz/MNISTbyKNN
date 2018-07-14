package fintask.knnclassifier;

public class Distance {
    public double getEuclidean(final double[] a, final double[] b){
        double sum = 0;
        for (int i = 0; i < a.length; i++)
            sum += Math.pow(a[i] - b[i], 2);
        return Math.sqrt(sum);
    }
    public double getManhattan(final double[] a, final double[] b){
        double sum = 0;
        for (int i = 0; i < a.length; i++)
            sum += Math.abs(a[i] - b[i]);
        return sum;
    }
}
