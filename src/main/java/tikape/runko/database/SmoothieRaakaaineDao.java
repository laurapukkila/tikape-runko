package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.Database;
import tikape.runko.domain.SmoothieRaakaaine;
import tikape.runko.domain.Smoothie;

public class SmoothieRaakaaineDao {

    private Database database;

    public SmoothieRaakaaineDao(Database database) {
        this.database = database;
    }

    public List<SmoothieRaakaaine> findAll(Integer key) throws SQLException {
        List<SmoothieRaakaaine> raakaaineet = new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine WHERE smoothie_id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Integer smoothie_id = rs.getInt("smoothie_id");
            Integer raakaaine_id = rs.getInt("raakaaine_id");
            String raakaaine = rs.getString("raakaaine");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");
            raakaaineet.add(new SmoothieRaakaaine(smoothie_id, raakaaine_id, raakaaine, maara, ohje));
        }

        rs.close();
        stmt.close();
        conn.close();

        return raakaaineet;
    }

    public SmoothieRaakaaine findOne(Integer smoothie_id, Integer raakaaine_id) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine WHERE smoothie_id = ? AND raakaaine_id = ?");
        stmt.setInt(1, smoothie_id);
        stmt.setInt(2, raakaaine_id);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        SmoothieRaakaaine smr = new SmoothieRaakaaine(rs.getInt("smoothie_id"), rs.getInt("raakaaine_id"), rs.getString("raakaaine"), rs.getString("maara"), rs.getString("ohje"));
        rs.close();
        stmt.close();
        conn.close();
        return smr;
    }

    public SmoothieRaakaaine saveOrUpdate(SmoothieRaakaaine object) throws SQLException {
        SmoothieRaakaaine sr = findOne(object.getSmoothieId(), object.getRaakaaineId());
        if (sr ==null) {
            save(object);
        } else {
            updateMaara(object.getSmoothieId(), object.getRaakaaineId(), object.getMaara());
        }
        return sr;
    }
    
    private SmoothieRaakaaine save(SmoothieRaakaaine object) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO SmoothieRaakaAine (smoothie_id, raakaaine_id, raakaaine, maara, ohje) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, object.getSmoothieId());
        stmt.setInt(2, object.getRaakaaineId());
        stmt.setString(3, object.getRaakaaine());
        stmt.setString(4, object.getMaara());
        stmt.setString(5, object.getOhje());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return object;
    }

    private SmoothieRaakaaine updateMaara(Integer smoothie_id, Integer raakaaine_id, String uusiMaara) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE SmoothieRaakaAine SET maara = ? WHERE smoothie_id = ? AND raakaaine_id = ?");
        stmt.setString(1, uusiMaara);
        stmt.setInt(2, smoothie_id);
        stmt.setInt(3, raakaaine_id);
        stmt.executeUpdate();

        stmt = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine WHERE smoothie_id = ? AND raakaaine_id = ?");
        stmt.setInt(1, smoothie_id);
        stmt.setInt(2, raakaaine_id);
        ResultSet rs = stmt.executeQuery();

        SmoothieRaakaaine s = new SmoothieRaakaaine(rs.getInt("smoothie_id"), rs.getInt("raakaaine_id"), rs.getString("raakaaine"), rs.getString("maara"), rs.getString("ohje"));
        rs.close();
        stmt.close();
        conn.close();
        return s;
    }

    public void delete(Integer smoothie_id, Integer raakaaine_id) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM SmoothieRaakaAine WHERE smoothie_id = ? AND raakaaine_id = ?");
        stmt.setInt(1, smoothie_id);
        stmt.setInt(2, raakaaine_id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}