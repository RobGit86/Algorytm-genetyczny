package genetyczny;

import static genetyczny.Parametry.*;
import static genetyczny.Funkcje.*;

public class Osobnik {

    private int x;
    private double fx;
    private int[] genotyp;
       
    public Osobnik() {
        
        x = losCalkowita(x_min, x_max);
        fx = y(x);
        genotyp = ustawGenotyp(x);
    }

    public int gen(int idx) {
    
        return genotyp[idx];
    }
    
    public void edytujGen(int idx, int g) {
    
        genotyp[idx] = g;
    }
    
    public final int[] ustawGenotyp(int x) {
        
        int[] g = new int[dlugosc_genotypu];
        
        for(int i=dlugosc_genotypu-1; i>=0; i--) {
            g[i] = x%2;
            x /= 2;
        }  
        return g;
    }
    
    public void mutuj(int idx) {
        
        if(genotyp[idx] == 0)
            genotyp[idx] = 1;
        else
            genotyp[idx] = 0;
    }
    
    public static Osobnik[] krzyzuj(Osobnik rodzic1, Osobnik rodzic2) {
        
        Osobnik[] para = new Osobnik[2];
        int przeciecie = losCalkowita(0, dlugosc_genotypu-2);
        Osobnik potomek1 = new Osobnik();
        Osobnik potomek2 = new Osobnik();
        
        //System.out.println("CIECIE: "+przeciecie);
        
        for(int i=0; i<dlugosc_genotypu; i++) {
            
            if(i <= przeciecie) {
                potomek1.edytujGen(i, rodzic1.gen(i));
                potomek2.edytujGen(i, rodzic2.gen(i));
            }
            else {
                potomek1.edytujGen(i, rodzic2.gen(i));
                potomek2.edytujGen(i, rodzic1.gen(i));
            }
        }
  
        para[0] = potomek1;
        para[0].aktualizujX();
        para[1] = potomek2;
        para[1].aktualizujX();
        
        return para;
    }
   
    public void dodajC(double c) {
        
        fx += c;
    }
    
    public void aktualizujX() {
        
        x = 0;
        int waga = 1;
        
        for(int i=dlugosc_genotypu-1; i>=0; i--) {
            x += genotyp[i]*waga;
            waga *= 2;
        }
        
        fx = y(x);
    }
    
    public void aktualizujFx(double x) {
        
        fx = x;
    }
    
    public int zwrocX() {
        
        return x;
    }
   
    public double zwrocFx() {
        
        return fx;
    }
        
    public int[] zwrocGenotyp() {
        
        return genotyp;
    }
    
    public String genotypString() {
        
        String str = "";
        for(int i=0; i<dlugosc_genotypu; i++) {
            str += Integer.toString(genotyp[i]);
        }
        return str;
    }
    
    public void pokaz() {
        
        System.out.print("X:"+x+" [");
        for(int i=0; i<dlugosc_genotypu-1; i++) {
            System.out.print(genotyp[i]+",");
        }
        System.out.println(genotyp[dlugosc_genotypu-1]+"]");
    }
}
