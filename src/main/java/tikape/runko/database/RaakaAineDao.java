package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.RaakaAine;

public class RaakaAineDao {

    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    public RaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        RaakaAine ra = new RaakaAine(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return ra;
    }

    public List<RaakaAine> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<RaakaAine> raakaaineet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            raakaaineet.add(new RaakaAine(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return raakaaineet;
    }

    public void delete(int id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public RaakaAine save(String nimi) throws SQLException {
        RaakaAine byName = findByName(nimi);
        if (byName != null) {
            return byName;
        }

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)");
        stmt.setString(1, nimi);
        stmt.executeUpdate();
        stmt.close();
        conn.close();

        return findByName(nimi);
    }

    public RaakaAine findByName(String nimi) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi FROM RaakaAine WHERE nimi = ?");
        stmt.setString(1, nimi);

        ResultSet result = stmt.executeQuery();
        if (!result.next()) {
            return null;
        }

        RaakaAine r = new RaakaAine(result.getInt("id"), result.getString("nimi"));
        result.close();
        stmt.close();
        conn.close();
        return r;
    }

}
