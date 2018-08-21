import java.util.*;
import java.lang.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import Constraints.Constraint;
import enums.ArrivalDay;
import enums.Gender;
import static java.time.temporal.ChronoUnit.MINUTES;

/** This class represents a team in the tournament */
public class Team implements Comparable<Team>{

	// Variables
	String school;
	String captainName;
	Gender gender;
	LocalTime arrivalTime;
	ArrivalDay arrivalDay;
	List<LocalTime> gameTimes;

	// constructor with team name and gender
	public Team(String school, String captainName, Gender gender, String arrivalTime, ArrivalDay arrivalDay) {
		this.school = school;
		this.captainName = captainName;
		this.arrivalTime = LocalTime.parse(arrivalTime); 
		this.arrivalDay = arrivalDay;
		this.gender = gender;
		this.gameTimes = new ArrayList<LocalTime>();
	}
	
	public boolean canPlay(LocalTime gameTime, long timePerGame) {
		
		//Loop through each of the game times that this team is scheduled for
		for (LocalTime scheduledTime: gameTimes) {
			
			//Check if the current game time is within two slots of another
			//Time slot that this team is playing . . .
			if (Math.abs(MINUTES.between(gameTime, scheduledTime)) <= 2 * timePerGame) {
				
				//Then return false
				return false;
			}
		}
		
		//If not, then return true
		return true;
	}


	@Override
	public String toString() {
		return school + " (" + captainName  + "-" +  gameTimes.size() + ")";
	}

	@Override
	public int compareTo(Team otherTeam) {
		
		//Maryland Teams get preference
		if (otherTeam instanceof MarylandTeam) {
			return 1; 
		}
		
		//Teams are compared first on their arrival day
		if (!arrivalDay.equals(otherTeam.arrivalDay)){
			return arrivalDay.getValue() - otherTeam.arrivalDay.getValue();
		}
		
		
		//If the arrival day is the same, then compare based on the time
		return (-1) * arrivalTime.compareTo(otherTeam.arrivalTime);
	}


}
