
package tikape.runko.domain;

public class RaakaAine {
    private String nimi;
    private int id;
    
    public RaakaAine(int id, String nimi) {
        this.nimi = nimi;
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public int getId() {
        return id;
    }
}
