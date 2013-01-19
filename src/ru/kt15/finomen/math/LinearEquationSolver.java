package ru.kt15.finomen.math;

import java.util.Arrays;

public class LinearEquationSolver {
    //equation equation in x = M * x + V format.
    public static double[] solve(double left,  double center, double right, double[] vectOfValues) {
        double[] vect = Arrays.copyOf(vectOfValues, vectOfValues.length);
        int n = vect.length;
        double[][] matr = new double[n][n];

        for (int i = 0; i < n; i++) {
            if (i == 0) {
                matr[i][i] = center + left;
                matr[i][i + 1] = right;
            } else if (i == n - 1) {
                matr[i][i] = center + right;
                matr[i][i - 1] = left;
            } else {
                matr[i][i] = center;
                matr[i][i - 1] = left;
                matr[i][i + 1] = right;
            }
        }

        // i - line number

        for (int i = 0; i < n - 1; i++) {

            if (Math.abs(matr[i][i]) < Math.abs(matr[i + 1][i])) {
                double[] tempMatrLine = matr[i];
                double tempVectElement = vect[i];
                matr[i] = matr[i + 1];
                vect[i] = vect[i + 1];
                matr[i + 1] = tempMatrLine;
                vect[i + 1] = tempVectElement;
            }
            double divider = matr[i][i];
            double multiplyer = matr[i + 1][i];

            for (int j = i; j < n; j++) {
                matr[i][j] /= divider;
            }
            vect[i] /= divider;
            matr[i][i] = 1;

            for (int j = i; j < n; j++) {
                matr[i + 1][j] -= multiplyer * matr[i][j];
            }
            matr[i + 1][i] = 0;
            vect[i + 1] -= multiplyer * vect[i];
        }
        vect[n - 1] /= matr[n - 1][n - 1];
        matr[n - 1][n - 1] = 1;
        double[] result = new double[n];

        result[n - 1] = vect[n - 1];
        for (int i = n - 1; i >= 0; i--) {
            result[i] = vect[i];
            for (int j = i + 1; j < n; j++) {
                result[i] -= result[j] * matr[i][j];
            }
        }
        return result;
    }
}