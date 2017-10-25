package tikape.runko.domain;

public class SmoothieRaakaaine {
    private Integer smoothieId;
    private Integer raakaaineId;
    private String maara;
    private String ohje;
    private String raakaaine;

    public SmoothieRaakaaine (Integer smoothieId, Integer raakaaineId, String raakaaine, String maara, String ohje) {
        this.smoothieId = smoothieId;
        this.raakaaineId = raakaaineId;
        this.raakaaine = raakaaine;
        this.maara = maara;
        this.ohje = ohje;
    }
    
    public String getOhje() {
        return ohje;
    }
    
    public Integer getSmoothieId() {
        return smoothieId;
    }

    public Integer getRaakaaineId() {
        return raakaaineId;
    }

    public String getMaara() {
        return maara;
    }
    public String getRaakaaine() {
        return raakaaine;
    }
}