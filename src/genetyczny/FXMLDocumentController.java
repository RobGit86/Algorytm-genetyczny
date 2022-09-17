package genetyczny;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.pow;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField a;

    @FXML
    private TextField b;
    
    @FXML
    private TextField c;
    
    @FXML
    private TextField wielkosc_populacji;
    
    @FXML
    private TextField liczba_populacji;
    
    @FXML
    private TextField dlugosc_genotypu;
    
    @FXML
    private TextField x_max;
    
    @FXML
    private TextField pr_mutacji;
    
    @FXML
    private TextField pr_krzyzowania;
    
    @FXML
    private TableView tabela;
    
    @FXML
    private TextArea odchylenie;
    
    Najlepszy[] dane = new Najlepszy[Parametry.liczba_iteracji_algorytmu];
    final ObservableList<Najlepszy> lista = FXCollections.observableArrayList();
    private int licznik = 1;
    
    @FXML
    public void uruchom(ActionEvent ae) throws IOException {

        przekazParametry();
        
        lista.clear();
        
        Osobnik[] najlepszy = new Osobnik[Parametry.liczba_iteracji_algorytmu];
        
        for(int k=0; k<Parametry.liczba_iteracji_algorytmu; k++) { 
            
            Populacja populacja = new Populacja();
                        
            
            
            for(int i=0; i<Parametry.liczba_populacji; i++) {

                
                
                populacja.mutacja();
         
                
                populacja.krzyzowanie();
                
             
                populacja.selekcja();
           
                
                
                
            }
            najlepszy[k] = populacja.najlepszy();
            
           
            lista.add(new Najlepszy(
                Integer.toString(k+1),
                Integer.toString(najlepszy[k].zwrocX()),  
                najlepszy[k].genotypString(),
                Double.toString(najlepszy[k].zwrocFx())));    
        }
        
        odchylenie.appendText("URUCHOMIENIE "+Integer.toString(licznik)+"\n");
        odchylenie.appendText("a:"+Double.toString(Parametry.a)+" , ");
        odchylenie.appendText("b:"+Double.toString(Parametry.b)+" , ");
        odchylenie.appendText("c:"+Double.toString(Parametry.c)+" , ");
        odchylenie.appendText("rp:"+Integer.toString(Parametry.wielkosc_populacji)+" , ");
        odchylenie.appendText("lp:"+Integer.toString(Parametry.liczba_populacji)+" , ");
        odchylenie.appendText("dg:"+Integer.toString(Parametry.dlugosc_genotypu)+" , ");
        odchylenie.appendText("PM:"+Double.toString(Parametry.pr_mutacji)+" , ");
        odchylenie.appendText("PK:"+Double.toString(Parametry.pr_krzyzowania)+" , ");
        odchylenie.appendText("\n"+"ODCHYLENIE: "+Funkcje.odchylenieSt(najlepszy));
        odchylenie.appendText("\n================\n");
        licznik++;
        
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat format_filename = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
        
        try (BufferedWriter plik = new BufferedWriter(new FileWriter("Wyniki "+format_filename.format(date)+".txt"))) {
            plik.write(formatter.format(date)+"\n\nWyniki:\n");
            for(int i=0; i<najlepszy.length; i++) {
                plik.write(Double.toString(najlepszy[i].zwrocFx())+"\n");
            }
        }
    }  
    
    public void przekazParametry() {
        
        Parametry.a = Double.parseDouble(a.getText());
        Parametry.b = Double.parseDouble(b.getText());
        Parametry.c = Double.parseDouble(c.getText());
        Parametry.wielkosc_populacji = Integer.parseInt(wielkosc_populacji.getText());
        Parametry.liczba_populacji = Integer.parseInt(liczba_populacji.getText());
        Parametry.dlugosc_genotypu = Integer.parseInt(dlugosc_genotypu.getText());
        Parametry.pr_mutacji = Double.parseDouble(pr_mutacji.getText())/100;
        Parametry.pr_krzyzowania = Double.parseDouble(pr_krzyzowania.getText())/100;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
              
        x_max.setEditable(false);
        
        a.setText(Double.toString(Parametry.a));
        b.setText(Double.toString(Parametry.b));
        c.setText(Double.toString(Parametry.c));
        wielkosc_populacji.setText(Integer.toString(Parametry.wielkosc_populacji));
        liczba_populacji.setText(Integer.toString(Parametry.liczba_populacji));
        dlugosc_genotypu.setText(Integer.toString(Parametry.dlugosc_genotypu));
        x_max.setText(Integer.toString(Parametry.x_max));
        pr_mutacji.setText(Double.toString(Parametry.pr_mutacji*100));
        pr_krzyzowania.setText(Double.toString(Parametry.pr_krzyzowania*100));   
        
        TableColumn iteracja = new TableColumn("Iteracja");
        TableColumn x = new TableColumn("x");
        TableColumn genotyp = new TableColumn("Genotyp");
        TableColumn fx = new TableColumn("f(x)");
        
        tabela.getColumns().addAll(iteracja, x, genotyp, fx);
        
        iteracja.setCellValueFactory(new PropertyValueFactory<Najlepszy,String>("iteracja"));
        x.setCellValueFactory(new PropertyValueFactory<Najlepszy,String>("x"));
        genotyp.setCellValueFactory(new PropertyValueFactory<Najlepszy,String>("genotyp"));
        fx.setCellValueFactory(new PropertyValueFactory<Najlepszy,String>("fx"));
        
        tabela.setItems(lista);
        
        dlugosc_genotypu.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                x_max.setText(Integer.toString((int)pow(2,(Integer.parseInt(newValue)))-1));
            } catch (Exception e) {
                x_max.setText("WPISZ PRAWIDŁOWĄ WARTOŚĆ GENOTYPU");
            }
        });
    }    
}

