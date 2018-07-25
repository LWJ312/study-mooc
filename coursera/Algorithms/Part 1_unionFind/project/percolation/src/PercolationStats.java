import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private final double[] results;
    private final double mean;
    private final double dev;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("out of bound");
        }
        results = new double[trials];
        int testsize = n;
        int row;
        int col;    
        int i;
        for (i = 0; i < trials; i++) {
            Percolation test = new Percolation(testsize);
            while (!test.percolates()) {
                row = StdRandom.uniform(1, testsize+1);
                col = StdRandom.uniform(1, testsize+1);
                test.open(row, col);
            }
            results[i] = (double) test.numberOfOpenSites() / (testsize*testsize);
        }
        mean = StdStats.mean(results);
        dev = StdStats.stddev(results);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return dev;
    }

    public double confidenceLo() {
        return mean - CONFIDENCE*dev / Math.sqrt(results.length);
    }

    public double confidenceHi() {
        return mean + CONFIDENCE*dev / Math.sqrt(results.length);

    }

    public static void main(String[] args) {
        Stopwatch sw = new Stopwatch();
        PercolationStats demo = new PercolationStats(20, 10000);
        double time = sw.elapsedTime();
        StdOut.print("time: " + time + "\n");
        StdOut.print("mean: " + demo.mean() + "\n");
        StdOut.print("stddev: " + demo.stddev() + "\n");
        StdOut.print("95% confidence interval:[" + demo.confidenceLo() + "," + demo.confidenceHi() + "]\n");
    }
}

