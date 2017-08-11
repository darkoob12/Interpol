package ir.darkoob;

import java.util.Random;

public class Interpolater {

    public double learningRate; // for optimization
    public double precision; // for optimization
    // number of coefficients
    public int m;
    // weights to be learned
    public double[] W;

    public Interpolater(int m_) {
        m = m_;
        W = new double[m];

        learningRate = 0.01;
        precision = 0.0000001;
    }


    public double f(double x) {
        double ret = 0;

        for (int i = 0;i < W.length;i++) {
            ret += W[0] * Math.pow(x, i);
        }
        return ret;
    }

    public double up(double[] x, double[] y, int index) {
        double ret = 0;
        for (int i = 0;i < x.length;i++)
            ret += Math.pow(x[index],index) * (f(x[i]) - y[i]);
        return ret;
    }

    public static double distance(double[] x, double[] y) {
        if (x.length != y.length) return -1;
        double ret = 0;
        for (int i = 0;i < x.length;i++) ret += Math.pow(x[i] - y[i],2);
        return Math.sqrt(ret);
    }

    /**
     * this function uses batch gradient descent to compute the coefficients
     * @param x vector of x values
     * @param y vector of target values
     */
    public void optimize(double[] x, double[] y) {
        Random rnd = new Random(21);
        // initialize the coefficients
        double[] Wp = new double[m];
        for (int i = 0;i < W.length;i++){
            W[i] = rnd.nextDouble();
            Wp[i] = 0;
        }

        int iter = 0;
        while (true) {
            iter++;
            // compute new coefficients
            for (int i = 0;i < m;i++) {
                Wp[i] = W[i] - learningRate * up(x,y,i);
            }
            // check for terminations
            double error = distance(W, Wp);
            if (iter % 10 == 0) System.out.println("error = " + error);
            if (error < precision) {
                System.out.println(iter);
                return;
            }
            // apply the changes
            System.arraycopy(Wp, 0, W, 0, Wp.length);
        }
    }

    public static double fx(double x) {
        return Math.pow(x, 2);
    }

    public static double minimize() {
        Random rnd = new Random(122);
        // random initial guess
        double x0 = rnd.nextDouble()*10;
        double x1 = 0;  // next estimate of the minimum
        int iter = 0;

        while (true) {
            iter++;
            x1 = x0 - 0.1 * (2 * x0);

            if ((Math.abs(x1 - x0) < 0.0000000000000000000001) || (iter > 100000)) {
                System.out.println("iterations: " + iter);
                return x1;
            }
            x0 = x1;
        }

    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0;i < W.length;i++)
            str.append(String.format("%.5f\t", W[i]));
        return str.toString();
    }
}
