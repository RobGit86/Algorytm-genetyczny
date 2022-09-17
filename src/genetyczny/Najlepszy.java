package genetyczny;

import javafx.beans.property.SimpleStringProperty;

public class Najlepszy {
    
    private final SimpleStringProperty iteracja;
    private final SimpleStringProperty x;
    private final SimpleStringProperty genotyp;
    private final SimpleStringProperty fx;
    
    public Najlepszy(String iteracja, String x, String genotyp, String fx) {
        this.iteracja = new SimpleStringProperty(iteracja);
        this.x = new SimpleStringProperty(x);
        this.genotyp = new SimpleStringProperty(genotyp);
        this.fx = new SimpleStringProperty(fx);
    }

    public String getIteracja() {
        return iteracja.get();
    }

    public void setIteracja(String iteracja) {
        this.iteracja.set(iteracja);
    }

    public String getX() {
        return x.get();
    }

    public void setX(String x) {
        this.x.set(x);
    }
    
    public String getGenotyp() {
        return genotyp.get();
    }

    public void setGenotyp(String genotyp) {
        this.genotyp.set(genotyp);
    }
    
    public String getFx() {
        return fx.get();
    }

    public void setFx(String fx) {
        this.fx.set(fx);
    }
}
