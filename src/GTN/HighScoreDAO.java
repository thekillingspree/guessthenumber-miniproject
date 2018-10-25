package GTN;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import GTN.Player;
import GTN.ConnectDatabse;

public class HighScoreDAO {
	public static boolean isHighScore(int score) {
		boolean isHighScore = false;
		try {
			Connection connection = ConnectDatabse.getConnection();
			PreparedStatement highscoreStmt = connection.prepareStatement("select min(score) from highscores");
            ResultSet rs = highscoreStmt.executeQuery();
            rs.next();
			int highScore = rs.getInt("min(score)");
			return score < highScore;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isHighScore;
	}
	
	public static ArrayList<Player> getLeaderboard() {
		ArrayList<Player> leaderboard = new ArrayList<>();
		try {
			Connection connection = ConnectDatabse.getConnection();
			Statement leaderboardStmt = connection.createStatement();
            ResultSet rs = leaderboardStmt.executeQuery("select * from highscores order by score asc");
            int count = 0;
            while(rs.next() && count < 10) {
                int score = rs.getInt("score");
                String name = rs.getString("name");
                Player p = new Player(name, score);
                leaderboard.add(p);
                count++;
            }
		} catch(SQLException e) {
			e.printStackTrace();
		}
 		return leaderboard;
    }
    
    public static void addScore(Player player) {
        try {
			Connection connection = ConnectDatabse.getConnection();
            PreparedStatement highscoreStmt = connection.prepareStatement("insert into highscores(name, score) values(? , ?);");
            highscoreStmt.setString(1, player.getName());
            highscoreStmt.setInt(2, player.getScore());
			highscoreStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
