package ru.kt15.finomen.cw;

import static ru.kt15.finomen.cw.Constants.*;
import static java.lang.Math.pow;
import static java.lang.Math.exp;

public class Functions {
    public static double dW_dT(double a, double T) {
        return -E * K * pow(a, ALPHA) * exp(-E / (R * T)) / (R * T * T);
    }

    public static double dW_da(double a, double T) {
        return -ALPHA * K * pow(a, ALPHA - 1) * exp(-E / (R * T));
    }
    
    public static double W(double a, double T){
        return -K * pow(a, ALPHA) * exp(-E / (R * T));
    }
    
}
