package fintask.knnclassifier.distance;

public class Manhattan implements Distance{


    public double calculate(final double[] a, final double[] b){
        double sum = 0;
        for (int i = 0; i < a.length; i++)
            sum += Math.abs(a[i] - b[i]);
        return sum;
    }
}
