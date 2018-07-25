import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid2D;
    private final int size;
    private int openSiteNum;
    private final WeightedQuickUnionUF uf;
    private final int siteVirtualTop;
    private final int siteVirtualBottom;
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("out of bound");
        }
        this.size = n;
        this.openSiteNum = 0;
        this.grid2D = new boolean[size][size];
        uf = new WeightedQuickUnionUF(size*size+2); 
        siteVirtualTop = 0;
        siteVirtualBottom = size*size+1; 
    }
    private int xyTo1D(int row, int col) {
        return (row-1)*this.size+col;
    }

    public void open(int row, int col) {
        if (row <= 0 || col <= 0)
            throw new IllegalArgumentException("out of bound");
        if (row > size || col > size)
            throw new IllegalArgumentException("out of bound");
        if (!isOpen(row, col)) {
            this.grid2D[row-1][col-1] = true;
            this.openSiteNum = this.openSiteNum+1;
            int site1D = xyTo1D(row, col);
            if (row == 1) {
                this.uf.union(site1D, this.siteVirtualTop);           
            }
            if (row == size) {
                this.uf.union(site1D, this.siteVirtualBottom);            
            }
            if (row-1 >= 1 && this.isOpen(row-1, col)) {
                this.uf.union(site1D, xyTo1D(row-1, col));
            }
            if (row+1 <= size && this.isOpen(row+1, col)) {
                this.uf.union(site1D, xyTo1D(row+1, col));
            }
            if (col-1 >= 1 && this.isOpen(row, col-1)) {
                this.uf.union(site1D, xyTo1D(row, col-1));
            }
            if (col+1 <= size && this.isOpen(row, col+1)) {
                this.uf.union(site1D, xyTo1D(row, col+1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0)
            throw new IllegalArgumentException("out of bound");
        if (row > size || col > size)
            throw new IllegalArgumentException("out of bound");
        return this.grid2D[row-1][col-1];
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0)
            throw new IllegalArgumentException("out of bound");
        if (row > size || col > size)
            throw new IllegalArgumentException("out of bound");
        int siteToBeJudged = xyTo1D(row, col);        
        return uf.connected(siteToBeJudged, siteVirtualTop);
    }

    public int numberOfOpenSites() {
        return this.openSiteNum;
    }

    public boolean percolates() {
        return uf.connected(siteVirtualTop, siteVirtualBottom);
    }

    public static void main(String[] args) {
        int testsize = 5;
        Percolation test1 = new Percolation(testsize);
        StdOut.print(test1.isOpen(1, 1)+"\n");
        test1.open(1, 1);
        StdOut.print(test1.isOpen(1, 1)+"\n");   
    }
}
