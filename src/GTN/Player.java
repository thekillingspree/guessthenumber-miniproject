package GTN;

public class Player {
	private int score;
	private String name;
	
	public Player (String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
