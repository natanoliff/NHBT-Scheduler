import java.util.*;


/**This class is represents a division, a set of 4 or 5 teams that are grouped
 * together to play a round robin schedule against each other on the friday */
public class Division implements Comparable<Division> {

	//variables
	List<Team> teams;	
	Set<String> schools;

	public Division() {
		this.teams = new ArrayList<Team>();
		this.schools = new HashSet<String>();
	}
	
	public void add(Team team) {
		teams.add(team);
		schools.add(team.school);
	}

	/**Divisions are compared based on the arrival time of the first non-UMD
	 * team in the division*/ 
	@Override
	public int compareTo(Division otherDivision) {
		//Grab the first non UMD team from each division
		Team firstTeam = !(teams.get(0) instanceof MarylandTeam) ? teams.get(0): 
																 teams.get(1);
		Team otherFirstTeam = !(otherDivision.teams.get(0) instanceof MarylandTeam)
						? otherDivision.teams.get(0): otherDivision.teams.get(1);
						
		//Compare those teams
		return firstTeam.compareTo(otherFirstTeam);
	}
	
	
	
}
