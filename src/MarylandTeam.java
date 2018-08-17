import java.util.HashSet;
import java.util.Set;
import static java.time.temporal.ChronoUnit.MINUTES;
import Constraints.Constraint;
import enums.ArrivalDay;
import enums.Gender;

/**A maryland team is just a specific type of team that also has constraints
 * due to members having class times or other things to do on campus,
 * thus, it gets precedence in terms of division placement and scheduling*/
public class MarylandTeam extends Team implements Comparable<Team> {
	
	Set<Constraint> constraints;

	public MarylandTeam(String school, String captainName, Gender gender, 
						String arrivalTime, ArrivalDay arrivalDay) {
		super(school, captainName, gender, arrivalTime, arrivalDay);
		this.constraints = new HashSet<Constraint>();
	}
	
	//This method adds a constraint
	public void addConstraint(String startTime, String endTime) {
		constraints.add(new Constraint(startTime,endTime));
	}
	
	
	@Override 
	public int compareTo(Team otherTeam) {

		//If other team is not a Maryland Team, then we come first
		if (!(otherTeam instanceof MarylandTeam)) {
			return -1;
		}
		
		//Cast the other team into a MarylandTeam
		MarylandTeam otherMarylandTeam = (MarylandTeam) otherTeam;
	
		//If both teams are Maryland, then compare constraint length
		return this.calculateConstraintSeverity() -
			   otherMarylandTeam.calculateConstraintSeverity();
	}

	/**This method calculates the total amount of minutes that one team cannot
	 * play on the Friday and returns it, used to compare to Maryland Teams*/
	private int calculateConstraintSeverity() {
		
		long constraintTime = 0;
		
		//Loop through each constraint and figure out the amount of minutes not
		//allowed to play in that constraint
		for (Constraint constraint: constraints) {
			constraintTime += MINUTES.between(constraint.startTime,constraint.endTime);
		}
		
		return (int) constraintTime;
	}

}
