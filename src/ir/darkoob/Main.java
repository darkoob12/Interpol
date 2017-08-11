package ir.darkoob;

import java.util.Random;

public class Main {

    public static final Random rnd = new Random(1);

    public static void main(String[] args) {
        int n = 10; // number of data points
        double[] x = new double[n];
        double[] y = new double[n];

        generateRandomData(x,y);

        Interpolater model = new Interpolater(9);
        model.optimize(x,y);
        System.out.println(model.toString());
        System.out.println(model.f(x[0]));
        System.out.println(y[0]);

    }

    public static void generateRandomData(double[] x, double[] y) {
        for (int i = 0;i < x.length;i++) {
            x[i] = Math.abs(rnd.nextDouble());
            y[i] = Math.sin(2 * Math.PI * x[i]);
        }
    }

}
