import enums.Gender;

import java.time.LocalTime;
import java.util.*;

public class Game{

	//Variables
	LocalTime timeSlot;
	Court court;
	Team homeTeam;
	Team awayTeam;
	boolean filled;
	
	//Constructor, the game starts out with no gender specifically yet
	public Game(LocalTime timeSlot, Team homeTeam, Team awayTeam) {
		this.timeSlot = timeSlot;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.filled = false;
		this.court = new Court();
	}
	
	public Game(Team homeTeam, Team awayTeam) {
		this.timeSlot = null;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.filled = false;
		this.court = new Court();
	}
	
	public Game(LocalTime timeSlot, Court court) {
		this.timeSlot = timeSlot;
		this.homeTeam = null;
		this.awayTeam = null;
		this.filled = false;
		this.court = court;
	}

	@Override
	public String toString() {
		return  homeTeam + " at " + awayTeam;
	}
	 
	
}
