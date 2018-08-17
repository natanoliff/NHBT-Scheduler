import java.util.*;


/**This class is represents a division, a set of 4 or 5 teams that are grouped
 * together to play a round robin schedule against each other on the friday */
public class Division  {

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
	
	
	
}
