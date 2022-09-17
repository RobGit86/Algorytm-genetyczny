package genetyczny;

import static java.lang.Math.pow;

public class Parametry {
    
    public static double a = -100;
    public static double b = 8;
    public static double c = 145;
   
    public static double stala_selekcja = 0;
    
    public static int wielkosc_populacji = 30;
    public static int liczba_populacji = 5;
    public static int dlugosc_genotypu = 8;
   
    public static int x_min = 0;
    public static int x_max = (int) (pow(2, dlugosc_genotypu)-1);
    
    public static double pr_mutacji = 0.09;
    public static double pr_krzyzowania = 0.8;
    
    public static int liczba_iteracji_algorytmu = 40;
}
