package genetyczny;

import static genetyczny.Parametry.*;
import static java.lang.Math.sqrt;
import java.text.DecimalFormat;
import java.util.Random;

public class Funkcje {
    
    public static int losCalkowita(int a, int b) {
        
        Random r = new Random();
        return r.nextInt((b - a) + 1) + a;
    }
    
    public static double losRzeczywista() {
        
        Random r = new Random();
        return r.nextDouble();
    }
    
    public static double y(int x) {
        return a*x*x + b*x + c;
    }
    
    public static int[] pary() {
        
        int[] pary = new int[wielkosc_populacji];
        
        for(int i=0; i<wielkosc_populacji; i++) {
            pary[i] = i;
        }

        for (int i=pary.length-1; i>0; i--) {
            zamien(pary, i, losCalkowita(0, i));
        }
        return pary;
    }
    
    public static void zamien(int[] tab, int i, int j) {
        int tmp = tab[i];
        tab[i] = tab[j];
        tab[j] = tmp;
    }
    
    public static String odchylenieSt(Osobnik[] os) {
        
        double suma = 0.0;
        double srednia = 0.0;
        
        for(int i=0; i<Parametry.liczba_iteracji_algorytmu; i++) {
            suma += os[i].zwrocFx()*os[i].zwrocFx();
        }
        
        for(int i=0; i<Parametry.liczba_iteracji_algorytmu; i++) {
            srednia += os[i].zwrocFx();
        }
        
        srednia /= liczba_iteracji_algorytmu;
        
        double wynik = sqrt((suma/liczba_iteracji_algorytmu) -(srednia*srednia));
        DecimalFormat df = new DecimalFormat("0.00");
        String odch = df.format(wynik);

        return odch;
    }
}
