package com.joshstamps;

/**
 * An implementation of the Matrix ADT. Provides four basic operations over an
 * immutable type.
 *
 * @Time: 10 hours
 *
 * @author Joshua Stamps, Ruben Acuna
 * @version 8/28/2019
 */

import java.util.Arrays;


public class StampsMatrix implements Matrix {

    //Creates 2D array of type double, with rows and columns of type int
    public int[][] Matrix;

    //Constructor
    public StampsMatrix(int[][] matrix) {

        Matrix = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            Matrix[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }
    }

    @Override
    //getter for x and y elements in 2D array
    public int getElement(int y, int x) {
        return this.Matrix[x][y];
    }

    @Override
    //getter for rows
    public int getRows() {
        int rows = 0;
        try {
            rows = Matrix.length;
        }catch(ArrayIndexOutOfBoundsException ae){
            System.out.println("No rows");
        }
        return rows;
    }

    @Override
    //getter for columns
    public int getColumns() {
        int columns = 0;
        try {
            columns = Matrix[0].length;
        }catch(ArrayIndexOutOfBoundsException ae){
            System.out.println("No columns");
        }
        return columns;
    }

    @Override
    //Returns this matrix scaled by a factor.
    public Matrix scale(int scalar) {

        int[][] result;
        int rowCount = getRows();
        int columnCount;
        int r = 0;
        int count = 0;
        int c;

        if (rowCount != 0) {
            columnCount = getColumns();
            int resultRow = rowCount * scalar;
            int resultCol = columnCount * scalar;
            result = new int[resultRow][resultCol];

            for (int i = 0; i < resultRow; i++) {
                c = 0;
                for (int j = 0; j < columnCount; j++) {
                    for (int k = 0; k < scalar; k++) {
                        result[i][c] = this.Matrix[r][j];
                        c++;
                    }
                }
                count++;
                if (count >= scalar) {
                    r++;
                    count = 0;
                }
            }
            return new StampsMatrix(result);
        }
        return null;
    }

    @Override
    //Returns this matrix added with another matrix.
    public Matrix plus(Matrix other) {
        int rows = getRows();
        int columns = getColumns();
        if((rows != other.getRows()) || (columns != other.getColumns()) )
            throw new RuntimeException("Matrices do not have matching dimensions");
        int[][] sum = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sum[i][j] = this.getElement(i,j) + other.getElement(i, j);
            }
        }
        return new StampsMatrix(sum);
    }

    @Override
    //Returns this matrix subtracted by another matrix.
    public Matrix minus(Matrix other) {
        int rows = getRows();
        int columns = getColumns();
        if((rows != other.getRows()) || (columns != other.getColumns()) )
            throw new RuntimeException("Matrices do not have matching dimensions");
        int[][] difference = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                difference[i][j] = this.getElement(i,j) - other.getElement(i, j);
            }
        }
        return new StampsMatrix(difference);
    }

    @Override
    //Returns this matrix multiplied by another matrix.
    public Matrix multiply(Matrix other) {
        int rows = getRows();
        int columns = getColumns();
        if((rows != other.getRows()) || (columns != other.getColumns()) )
            throw new RuntimeException("Matrices do not have matching dimensions");
        int[][] product = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                product[i][j] = this.getElement(i,j) * other.getElement(i, j);
            }
        }
        return new StampsMatrix(product);
    }

    @Override
    //Returns true if this matrix matches another matrix.
    public boolean equals(Object other) {
        int rows=getRows();
        int columns=getColumns();
        Matrix m = null;
        boolean flag = true;
        try {
            m = (StampsMatrix) other;
            if((rows != m.getRows()) || (columns != m.getColumns()) ) {
                flag = false;
                return flag;
            }
            int[][] result = new int[rows][columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if(this.getElement(i,j) == m.getElement(i, j))
                        flag= flag && true;
                    else{
                        flag= flag && false;
                    }
                }
            }
        }catch(Exception e){
            flag = false;
            System.out.print("Null or Not a valid type.\n");
        }
        return flag;
    }

    @Override
    //Returns a string representation of this matrix. A new line character will
    //separate each row, while a space will separate each column.
    public final String toString() {
        StringBuffer sb = new StringBuffer("[");

        int rows = getRows();
        int columns = getColumns();

        for (int i = 0; i < rows; i++) {
            sb.append("[");
            for (int j = 0; j < columns; j++)
                sb.append(" " + Matrix[i][j] + " ");
            sb.append("]");
        }
        return sb + "]";
    }





    /**
     * Entry point for matrix testing.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final int[][] data1 = new int[0][0];
        final int[][] data2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        final int[][] data3 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        final int[][] data4 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};

        Matrix m1 = new StampsMatrix(data1);
        Matrix m2 = new StampsMatrix(data2);
        Matrix m3 = new StampsMatrix(data3);
        Matrix m4 = new StampsMatrix(data4);

        System.out.println("m1 --> Rows: " + m1.getRows() + " Columns: " + m1.getColumns());
        System.out.println("m2 --> Rows: " + m2.getRows() + " Columns: " + m2.getColumns());
        System.out.println("m3 --> Rows: " + m3.getRows() + " Columns: " + m3.getColumns());

        //check for reference issues
        System.out.println("m2 -->\n" + m2);
        data2[1][1] = 101;                                                                                                                                          data2[1][1] = 5;
        System.out.println("m2 -->\n" + m2);

        //test equals
        System.out.println("m2==null: " + m2.equals(null));             //false
        System.out.println("m3==\"MATRIX\": " + m2.equals("MATRIX"));   //false
        System.out.println("m2==m1: " + m2.equals(m1));                 //false
        System.out.println("m2==m2: " + m2.equals(m2));                 //true
        System.out.println("m2==m3: " + m2.equals(m3));                 //false
        System.out.println("m3==m4: " + m3.equals(m4));                 //true

        //test operations (valid)
        System.out.println("2 * m2:\n" + m2.scale(2));
        System.out.println("m2 + m3:\n" + m2.plus(m3));
        System.out.println("m2 - m3:\n" + m2.minus(m3));

        //not tested... multiply(). you know what to do.

        //test operations (invalid)
        //System.out.println("m1 + m2" + m1.plus(m2));
        //System.out.println("m1 - m2" + m1.minus(m2));
    }
}
