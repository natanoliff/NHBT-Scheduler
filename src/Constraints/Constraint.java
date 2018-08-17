package Constraints;

import java.time.LocalTime;

/**Constraints are meant for the Maryland teams. They signify a period of time 
 * where the team cannot play a game probably because of class, the period is 
 * signified by the startTime and the endTime of the constraint*/
public class Constraint {

	public LocalTime startTime;
	public LocalTime endTime;

	//Constructor that takes in strings (for users)
	public Constraint(String startTime, String endTime) {
		this.startTime = LocalTime.parse(startTime);
		this.endTime = LocalTime.parse(endTime);
	}

	//Constructor that takes in LocalTime Objects
	public Constraint(LocalTime startTime, LocalTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**This method determines if the time meets the constraints of the team,
	 * it takes in @param gameTime: which is the time of that game. It then 
	 * either @returns true if the time is not between the start and end time
	 * false if it is*/
	public boolean fufilled(LocalTime gameTime) {
		
		/*if the game time falls within the constraint times, then return false */
		if ((gameTime.isAfter(startTime) && gameTime.isBefore(endTime))) {
			return false;
		}
		
		//If it is not between the two times, then constraint is fulfilled 
		//and the team can play at that time
		return true;
	}
	

}