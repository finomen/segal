package ru.kt15.finomen.cw;

import static ru.kt15.finomen.cw.Constants.*;

public class Calculator {
    public static interface ProgressListener {
        void progessUpdated(int progress);
    }

    private static void secureProgressUpdate(ProgressListener listener, int progress) {
        if (listener == null) {
            return;
        }
        listener.progessUpdated(progress);
    }

    public static CalculationResults calculate(double ti, double timeStep, int stepCount,
                                               double nodeStep, int nodeCount, ProgressListener listener) {
        int m = nodeCount;
        double T[][] = new double[stepCount][m];
        double a[][] = new double[stepCount][m];
        double W[][] = new double[stepCount][m];
        double dt = timeStep;
        double dx2 = nodeStep * nodeStep;
        double Q_C = Q / C;

        // initial conditions
        for (int i = 0; i < m; i++) {
            T[0][i] = T_0;
            a[0][i] = 1;
        }

        for (int time = 1; time < stepCount; time++) {
            secureProgressUpdate(listener, time);
            double eq[][] = new double[2 * m][2 * m];
            double vector[] = new double[2 * m];

            double fW, fdW_da, fdW_dT;

            // LEFT BORDER
            fW = Functions.W(a[time - 1][0], T[time - 1][0]);
            fdW_da = Functions.dW_da(a[time - 1][0], T[time - 1][0]);
            fdW_dT = Functions.dW_dT(a[time - 1][0], T[time - 1][0]);

            //fill left border from 1**
            eq[0][0] = 1 / dt + 2 * D / dx2 - fdW_da;
            eq[0][2] = -D / dx2;
            eq[0][1] = -fdW_dT;
            //left vector from 1**
            vector[0] = a[time - 1][0] / dt + fW - fdW_da * a[time - 1][0] - fdW_dT * T[time - 1][0];

            //left border from 2**
            eq[1][1] = 1 / dt + 2 * KAPPA / dx2 + Q_C * fdW_dT;
            eq[1][3] = -KAPPA / dx2;
            eq[1][0] = Q_C * fdW_da;
            // left vector from 2**
            vector[1] = ti * KAPPA / dx2 + T[time - 1][0] / dt +
                    Q_C * (-fW + fdW_da * a[time - 1][0] + fdW_dT * T[time - 1][0]);

            // INNER CELLS
            fW = Functions.W(a[time - 1][m - 1], T[time - 1][m - 1]);
            fdW_da = Functions.dW_da(a[time - 1][m - 1], T[time - 1][m - 1]);
            fdW_dT = Functions.dW_dT(a[time - 1][m - 1], T[time - 1][m - 1]);

            for (int x = 1; x < m - 1; x++) {
                fW = Functions.W(a[time - 1][x], T[time - 1][x]);
                fdW_da = Functions.dW_da(a[time - 1][x], T[time - 1][x]);
                fdW_dT = Functions.dW_dT(a[time - 1][x], T[time - 1][x]);

                //fill cells for a from 1**
                eq[2 * x][2 * x - 2] = -D / dx2;
                eq[2 * x][2 * x] = 1 / dt + 2 * D / dx2 - fdW_da;
                eq[2 * x][2 * x + 2] = -D / dx2;
                //fill cells for t from 1**
                eq[2 * x][2 * x + 1] = -fdW_dT;
                //fill free vector for 1**
                vector[2 * x] = a[time - 1][x] / dt + fW - fdW_da * a[time - 1][x] - fdW_dT * T[time - 1][x];

                //fill cells for a from 2**
                eq[2 * x + 1][2 * x] = Q_C * fdW_da;
                //fill cells for T from 2**
                eq[2 * x + 1][2 * x - 1] = -KAPPA / dx2;
                eq[2 * x + 1][2 * x + 1] = 1 / dt + 2 * KAPPA / dx2 + Q_C * fdW_dT;
                eq[2 * x + 1][2 * x + 3] = -KAPPA / dx2;
                //fill free vector for 2**
                vector[2 * x + 1] = T[time - 1][x] / dt +
                        Q_C * (-fW + fdW_da * a[time - 1][x] + fdW_dT * T[time - 1][x]);
            }

            // RIGHT BORDER
            //right border from 1**
            eq[2 * m - 2][2 * m - 4] = -D / dx2;
            eq[2 * m - 2][2 * m - 2] = 1 / dt + D / dx2 - fdW_da;
            eq[2 * m - 2][2 * m - 1] = -fdW_dT;
            // right vector from 1**
            vector[2 * m - 2] = a[time - 1][m - 1] / dt +
                    fW - fdW_da * a[time - 1][m - 1] - fdW_dT * T[time - 1][m - 1];

            //right border from 2**
            eq[2 * m - 1][2 * m - 3] = -KAPPA / dx2;
            eq[2 * m - 1][2 * m - 1] = 1 / dt + 2 * KAPPA / dx2 + Q_C * fdW_dT;
            eq[2 * m - 1][2 * m - 2] = Q_C * fdW_da;
            // right vector from 2**
            vector[2 * m - 1] = KAPPA * T_0 / dx2 + T[time - 1][m - 1] / dt +
                    Q_C * (-fW + fdW_da * a[time - 1][m - 1] + fdW_dT * T[time - 1][m - 1]);

            // SOLVING EQUATION SYSTEM
            double result[] = EquationSystemSolver.solve(eq, vector);
            for (int x = 0; x < m; x++) {
                a[time][x] = result[2 * x];
                T[time][x] = result[2 * x + 1];
            }
        }
        for (int i = 0; i < stepCount; i++) {
            for (int j = 0; j < m; j++) {
                W[i][j] = Math.abs(Functions.W(a[i][j], T[i][j]));
            }
        }
        return new CalculationResults(T, a, W, ti, timeStep, nodeStep);
    }

    public static CalculationResults calculate(double ti, double timeStep, int stepCount, double nodeStep, int nodeCount) {
        return calculate(ti, timeStep, stepCount, nodeStep,  nodeCount, null);
    }
}
