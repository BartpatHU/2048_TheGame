package hu.projekt.dao;

import hu.projekt.model.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAOImpl implements ScoreDAO{


    private static final String CONN_STR = "jdbc:sqlite:db.db";
    private static final String SELECT_ALL_SCORE = "SELECT * FROM Score;";
    private static final String CREATE_DB = "CREATE TABLE if not exists Score(" +
            "id INTEGER NOT NULL PRIMARY KEY autoincrement," +
            "scores INTEGER NOT NULL," +
            "ido TEXT NOT NULL);";

    private static final String INSERT_INTO = "INSERT INTO Score VALUES(NULL ,?,?)";


    public ScoreDAOImpl() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection asd = DriverManager.getConnection(CONN_STR);
            Statement st = asd.createStatement();
        ){
            st.executeUpdate(CREATE_DB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Score> listALL() {

        List<Score> result = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(CONN_STR);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_ALL_SCORE)
        ){
            while(rs.next()) {
                Score s = new Score(
                        rs.getInt(1),
                        rs.getString(2)
                );
                result.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean addScore(Score score) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement st = conn.prepareStatement(INSERT_INTO)) {
            st.setInt(1, score.getScore());
            st.setString(2, score.getDate());
            int res = st.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
