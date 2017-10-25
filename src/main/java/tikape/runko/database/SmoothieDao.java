package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Smoothie;

public class SmoothieDao implements Dao<Smoothie, Integer> {

    private Database database;

    public SmoothieDao(Database database) {
        this.database = database;
    }

    @Override
    public Smoothie findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Smoothie WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Smoothie s = new Smoothie(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return s;
    }

    @Override
    public List<Smoothie> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Smoothie");

        ResultSet rs = stmt.executeQuery();
        List<Smoothie> smoothiet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            smoothiet.add(new Smoothie(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return smoothiet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Smoothie WHERE id =?");
        stmt.setInt(1,key);
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }
    
    public Smoothie save(String nimi) throws SQLException {
        Smoothie byName = findByName(nimi);
        if (byName != null) {
            return byName;
        }

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Smoothie (nimi) VALUES (?)");
        stmt.setString(1, nimi);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return findByName(nimi);
    }
    
    public Smoothie findByName(String nimi) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie WHERE nimi = ?");
        stmt.setString(1, nimi);

        ResultSet result = stmt.executeQuery();
        if (!result.next()) {
            return null;
        }
        Smoothie s = new Smoothie(result.getInt("id"), result.getString("nimi"));
        result.close();
        stmt.close();
        conn.close();
        return s;
    }

}
