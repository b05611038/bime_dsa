import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] indexMatrix;
    private int num; //save the matrix len
    private int openCount = 0;
    private int bottomCount = 0;
    private int [] checkIndex;
    private WeightedQuickUnionUF UF;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) { //structure
        indexMatrix = new boolean[n][n];
        num = n;

        checkIndex = new int[n];
        UF = new WeightedQuickUnionUF(n * n + 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                indexMatrix[i][j] = false;
            }
        }
        //initial all conponent matrix not opoend
    }

    public void open(int row, int col) {
        if (row <= 0 || row > num || col <= 0 || col > num) {
            throw new IllegalArgumentException();
        }
        // open site (row, col) if it is not open already
        row = row - 1;
        col = col - 1;

        if (indexMatrix[row][col] == false) {
            indexMatrix[row][col] = true;
            openCount++;
            //assign the index for the open block

            if ((row - 1) == -1) {
                UF.union(0, (row * num + col + 1));
                //union the upper knob
            } else if ((row - 1) > -1 ) {
                if (indexMatrix[row - 1][col]) {
                    if (UF.connected(((row - 1) * num + col + 1), (row * num + col + 1))) {
                    } else {
                        UF.union(((row - 1) * num + col + 1), (row * num + col + 1));
                        //union the upper block
                    }
                }
            }

            if ((col - 1) >= 0 ) {
                if (indexMatrix[row][col - 1]) {
                    if (UF.connected((row * num + col), (row * num + col + 1))) {
                    } else {
                    UF.union((row * num + col), (row * num + col + 1));
                    //union the left block
                    }
                }
            }

            if ((col + 1) < num ) {
                if (indexMatrix[row][col + 1]) {
                    if (UF.connected((row * num + col + 1), (row * num + col + 2))) {
                    } else {
                    UF.union((row * num + col + 1), (row * num + col + 2));
                    //union the right block
                    }
                }
            }

            if ((row + 1) < num ) {
                if (indexMatrix[row + 1][col]) {
                    if (UF.connected(((row + 1) * num + col + 1), (row * num + col + 1))) {
                    } else {
                    UF.union(((row + 1) * num + col + 1), (row * num + col + 1));
                    //union the downward block
                    }
                }
            } else if ((row + 1) == num) {
                checkIndex[bottomCount] = row * num + col + 1;
                bottomCount++;
            }
        }
    }

    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        if (row <= 0 || row > num || col <= 0 || col > num) {
            throw new IllegalArgumentException();
        }
        row = row - 1;
        col = col - 1;

        return indexMatrix[row][col];
    }

    public boolean isFull(int row, int col) {
        //is site(row, col) full?)
        if (row <= 0 || row > num || col <= 0 || col > num) {
            throw new IllegalArgumentException();
        }
        row = row - 1;
        col = col - 1;

        if (UF.connected(0, (row * num + col + 1))) {
            return true;
        } else {
            return false;
        }
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        //does the system percolate
        boolean check = false;

        for (int i = 0; i < bottomCount; i++) {
            check = UF.connected(0, checkIndex[i]);
            if (check) {
                break;
            }
        }

        return check;
    }
}
