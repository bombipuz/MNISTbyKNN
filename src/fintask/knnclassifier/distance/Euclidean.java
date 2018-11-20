package fintask.knnclassifier.distance;

public class Euclidean  implements Distance {

    public double calculate(final double[] a, final double[] b){
        double sum = 0;
        for (int i = 0; i < a.length; i++)
            sum += Math.pow(a[i] - b[i], 2);
        return Math.sqrt(sum);
    }
}
