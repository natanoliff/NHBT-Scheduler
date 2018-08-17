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

	// constructor with team name and gender
	public Team(String school, String captainName, Gender gender, String arrivalTime, ArrivalDay arrivalDay) {
		this.school = school;
		this.captainName = captainName;
		this.arrivalTime = LocalTime.parse(arrivalTime); 
		this.arrivalDay = arrivalDay;
		this.gender = gender;
	}
	
	


	@Override
	public String toString() {
		return school + " - " + captainName;
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
