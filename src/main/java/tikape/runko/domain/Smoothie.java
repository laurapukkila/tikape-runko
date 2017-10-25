
package tikape.runko.domain;

public class Smoothie {
    private String nimi;
    private int id;
    
    public Smoothie(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
    public String getNimi() {
        return nimi;
    }

    public int getId() {
        return id;
    }
    
}