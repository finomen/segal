package ru.kt15.finomen.cw;

public class Constants {
    public static final double K = 1.6e6;            // 1 / s
    public static final double E = 8e4;              // J / mol
    public static final double ALPHA = 1;
    public static final double Q = 7e5;              // J / kg
    public static final double RHO = 830;            // kg / m^3
    public static final double T_0 = 293;            // K
    public static final double C = 1980;             // J / (kg K)
    public static final double T_M = T_0 + Q / C;    // ~650 K
    public static final double LAMBDA = 0.0013;        // J / (m s K)
    public static final double KAPPA = LAMBDA / RHO / C;
    public static final double D = KAPPA;
    public static final double R = 8.31;             // m^2 kg / (s^2 K mol)
}
