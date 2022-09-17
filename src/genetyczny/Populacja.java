package genetyczny;

import static genetyczny.Parametry.*;
import static genetyczny.Funkcje.*;

public class Populacja {

    private Osobnik[] osobnik;
    
    public Populacja() {
        
        osobnik = new Osobnik[wielkosc_populacji];
        
        for(int i=0; i<wielkosc_populacji; i++) {
            osobnik[i] = new Osobnik();
        }
    }
    
    public void mutacja() {
        
        double pr;
        
        for(int i=0; i<wielkosc_populacji; i++) {
            for(int j=0; j<dlugosc_genotypu; j++){
                
                pr = losRzeczywista();
                
                if (pr <= pr_mutacji) {
                    osobnik[i].mutuj(j);
                    //System.out.println("ZMUTOWANO!!"+"OS: "+(i+1)+" ; GEN: "+j);
                }
            }
            osobnik[i].aktualizujX();
        }
    }
    
    public void krzyzowanie() {
        
        int[] pary = Funkcje.pary();
        double pk;
        Osobnik[] nowa_populacja = new Osobnik[wielkosc_populacji];
        
        for(int i=0, j=pary.length-1; i<pary.length/2; i++, j--) {
            //System.out.print(pary[i]+" ");
            //System.out.println(pary[j]);
            
            pk = losRzeczywista();
            
            if(pk <= pr_krzyzowania) {
                
                Osobnik[] potomki;

                if(pary[i] < pary[j])
                    potomki = Osobnik.krzyzuj(osobnik[pary[i]], osobnik[pary[j]]);
                else 
                    potomki = Osobnik.krzyzuj(osobnik[pary[j]], osobnik[pary[i]]);
                    
                //System.out.println("PARA: "+pary[i]+" "+pary[j]);

                if(pary[i] > pary[j]) {
                    nowa_populacja[pary[i]] = potomki[1];
                    nowa_populacja[pary[j]] = potomki[0];
                } else {
                    nowa_populacja[pary[i]] = potomki[0];
                    nowa_populacja[pary[j]] = potomki[1];
                }
                
                //System.out.println("SKRZYZOWANO: "+(pary[i]+1)+" z "+(pary[j]+1));
            }
        }
        
        for(int i=0; i<wielkosc_populacji; i++) {
            if(nowa_populacja[i] == null)
                nowa_populacja[i] = osobnik[i];
        }
        
        osobnik = nowa_populacja;
    }
    
    public void selekcja() {
        
        double suma_fx = 0.0;
        double[] px = new double[wielkosc_populacji];
        Osobnik[] nowa_populacja = new Osobnik[wielkosc_populacji];
        
        
        this.dodajStala();
        
        for(int i=0; i<wielkosc_populacji; i++) {
            suma_fx += osobnik[i].zwrocFx();    
            //System.out.println(osobnik[i].zwrocFx());
        }

        for(int i=0; i<wielkosc_populacji; i++) {
            px[i] = osobnik[i].zwrocFx()/suma_fx;
        }
        
        this.usunStala();
        
        for(int i=0; i<wielkosc_populacji; i++) {
            
            double suma = 0.0;
            double ps = losRzeczywista();
            
            //System.out.println("PS: "+ps);
            
            for(int j=0; j<wielkosc_populacji; j++) {

                suma += px[j];

                if(ps <= suma) {
                    nowa_populacja[i] = osobnik[j];
                    //System.out.println("Do nowej pop Osobnik nr "+(j+1));
                    break;
                }
            }
        }
        osobnik = nowa_populacja;
    }
    
    public void dodajStala() {

        double stalaC = 0.0;
        
        for(int i=0; i<wielkosc_populacji; i++) {
            if(osobnik[i].zwrocFx() < 0 && osobnik[i].zwrocFx() < stalaC)
                stalaC = osobnik[i].zwrocFx();
        }
        
        
        
        if(stalaC < 0) {
            for(int i=0; i<wielkosc_populacji; i++) {
                osobnik[i].dodajC(-stalaC);
            }
        }
        stala_selekcja = stalaC;
    }
    
    public void usunStala() {
        
        //System.out.println(stala_selekcja);
        
        for(int i=0; i<wielkosc_populacji; i++) {
            osobnik[i].aktualizujFx(osobnik[i].zwrocFx()+stala_selekcja);
            
        }
    }
    
    
    
    public Osobnik najlepszy() {
        
        double max = osobnik[0].zwrocFx();
        Osobnik os = osobnik[0];
        
        for(int i=0; i<wielkosc_populacji; i++) {
            if(osobnik[i].zwrocFx() > max) {
                max = osobnik[i].zwrocFx();
                os = osobnik[i];
            }
        }
        
        return os;
    }
    
    public Osobnik[] zwrocOsobnikow() {
        
        return osobnik;
    }
    
    public void pokazPopulacje() {
        
        for(int i=0; i<wielkosc_populacji; i++) {
            System.out.print("Osobnik nr "+(i+1)+": ");
            osobnik[i].pokaz();
        }
    } 
}
