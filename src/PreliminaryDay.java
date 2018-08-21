import java.util.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.*;

import enums.ArrivalDay;
import enums.Gender;

public class PreliminaryDay {

	//Variables
	List<TimeSlot> schedule;
	List<Court> courts;
	List<Team> teams, mensTeams, womensTeams;
	List<Division> mensDivisions, womensDivisions, totalDivisions; 
	int numberOfGameSlots;
	long minutesBetweenGames;
	String timeOfFirstGame;

	/**Default constructor, initializes everything to be 0 or empty*/
	public PreliminaryDay() {
		this.schedule = new ArrayList<TimeSlot>();
		this.courts = new ArrayList<Court>();
		this.teams = new ArrayList<Team>();
		this.mensTeams = new ArrayList<Team>();
		this.womensTeams = new ArrayList<Team>();
		this.mensDivisions = new ArrayList<Division>();
		this.womensDivisions = new ArrayList<Division>();
		this.totalDivisions = new ArrayList<Division>();
		this.numberOfGameSlots = 0;
		this.timeOfFirstGame = new String();
		this.minutesBetweenGames = 0;
	}

	/**This is the main method in this class. It organizes the tournament.
	 * First, it gets all the info and divides the tourney into multiple 
	 * divisions for both the mens and womens bracket. It also figures
	 * out how many game slots are needed. Finally, it fills in the schedule.*/ 
	public void organizeTournament() {

		Collections.sort(mensTeams);
		Collections.sort(womensTeams);
		//First, figure out the number of game slots needed
		numberOfGameSlots = determineNumberOfGameSlots(mensTeams.size());
		numberOfGameSlots += determineNumberOfGameSlots(womensTeams.size());

		//Next, divide the tournament into divisions
		mensDivisions = divideTourneyIntoDivisions(mensTeams.size(),mensTeams);
		womensDivisions = divideTourneyIntoDivisions(womensTeams.size(),womensTeams);
		totalDivisions.addAll(mensDivisions);
		totalDivisions.addAll(womensDivisions);
		Collections.sort(totalDivisions);

		//Now fill in the schedule
		initializeSchedule();

	}

	/**This is the scheduling method. It takes all the teams and places them
	 *in the schedule  */
	public void scheduleTournament() {
		boolean validTourney = false;

		//Get a list of all the games
		List<Game> games = turnDivisionsIntoGames();
		
		//Keep trying add games until viable solution is found
		while (!validTourney) {
			//clear games out
			for (Game game: games) {
				game.awayTeam.gameTimes.clear();
				game.homeTeam.gameTimes.clear();
				game.filled = false;
			}
			validTourney = addGamesToTourney(games);
		}
		
	}

	/** This method goes through each games and adds it to the tourney*/
	private boolean addGamesToTourney(List<Game> games) {
		
		//Reverse the schedule, as the most constrained teams come first
		//So we want these teams to play later
		Collections.reverse(schedule);
		int index = 1;
		//Loop through each game
		for (Game game: games) {
			
			Collections.shuffle(schedule);
			
			//Place that game into the schedule
			if (!placeGameIntoSlot(game)) {
				
				//If fails, erase and start again
				for (TimeSlot timeSlot: schedule) {
					timeSlot.games.clear();
					timeSlot.filled = false;
				}
				return false;
			}
			
			System.out.println("Game Number " + index++ + " Placed");
		}
		
		Collections.sort(schedule);
		return true;
	}

	private boolean placeGameIntoSlot(Game game) {
		boolean gamePlaced = false;
		int index = 0;
		
		//Try adding the game to the first slot
		do {
			TimeSlot slot = schedule.get(index);
			LocalTime gameTime = slot.time;
	
			//Check if it can be added
			if (!slot.filled &&
					game.homeTeam.canPlay(gameTime, minutesBetweenGames) &&
					game.awayTeam.canPlay(gameTime, minutesBetweenGames)) {
				
				//If so, add the game there
				slot.games.add(game);
				
				//If the slot is filled for each court, then mark it as filled
				if (slot.games.size() == courts.size()) {
					slot.filled = true;
				}
				
				//Game is placed
				gamePlaced = true;
				
				//Update the times info of the game object
				game.timeSlot = slot.time;
				 
				//update the times for each team
				game.awayTeam.gameTimes.add(game.timeSlot);
				game.homeTeam.gameTimes.add(game.timeSlot);
				
			} 
			//Increment index
			if (++index >= numberOfGameSlots) {
				return false;
			}
		
		//Keep going if game is not placed	
		} while (!gamePlaced);
		
		return true;
	}

	/** This method is a for loop that goes through all the different 
	 * divisions and then calls the retrieve games method to collect all the
	   games from in the tournament */
	private List<Game> turnDivisionsIntoGames() {
		List<Game> games = new ArrayList<Game>();

		//Loop through each division and retrieve all the possible matches
		for (Division division: totalDivisions) {

			int teamsInDivision = division.teams.size();
			games.addAll(retrieveGamesFromDivision(division,teamsInDivision));

		}

		return games;

	}

	/**This method retrieves all the games from the mini round robin within 
	 * each division. I hard coded in all the matchups because the divisions
	 * are not supposed to be bigger than 5, so I never have to make more
	 * than 8 different games for a division. It can handle divisions of either
	 * 4 or 5 */
	private List<Game> retrieveGamesFromDivision(Division division, int divisionSize) {
		//Create List of Games
		List<Game> games = new ArrayList<Game>();

		//Shorthand all the teams for readability
		List<Team> teams = division.teams;
		Team team1 = teams.get(0), team2 = teams.get(1), team3 = teams.get(2),
				team4 = teams.get(3);

		//Add games that occur in both 4/5 size divisions
		games.add(new Game(team1,team2));
		games.add(new Game(team1,team3));
		games.add(new Game(team1,team4));
		games.add(new Game(team2,team3));
		games.add(new Game(team3,team4));

		//Add the game that only occurs in 4 sized divisions
		if (divisionSize == 4) { 
			games.add(new Game(team2,team4));

			//Now, if size is 5. . .
		} else {
			//... we add a team5
			Team team5 = teams.get(4); 
			//And add the rest of the games
			games.add(new Game(team1,team5));
			games.add(new Game(team2,team5));
			games.add(new Game(team4,team5));
		}

		//return the games
		return games;
	}

	/**This method does a double for loop to initialize the schedule of the 
	 * tournament with game slot and games*/
	private void initializeSchedule() {

		LocalTime currentTimeToAdd = LocalTime.parse(timeOfFirstGame);
	
		//Next, we set up the schedule. we use a for loop to figure out how many
		//different slots we need to set up
		for (int index = 0; index < numberOfGameSlots; index++) {
			
			//Add the Game
			schedule.add(new TimeSlot(currentTimeToAdd));
			currentTimeToAdd = currentTimeToAdd.plusMinutes(minutesBetweenGames);
		}
	}


	/**Takes in the @param numberOfTeams of which ever gender, figures out how
	 * many divisions that gender needs. Then, we divide all @param teams of 
	 * that gender into different divisions*/
	private List<Division> divideTourneyIntoDivisions(int numberOfTeams, List<Team> teams) {

		//setting up all needed variables
		List<Team> teamsCopy = new ArrayList<Team>(teams);
		int numberOfDivisions = numberOfTeams/4;
		int numberOfFourTeamDivisions = 
				numberOfTeams/4 - numberOfTeams % 4;
		int index;

		//Create an array list of divisions
		List<Division> divisions = new ArrayList<Division>();

		//Initialize all the divisions
		for (int i = 0; i < numberOfDivisions; i++) {
			divisions.add(new Division());
		}

		//Loop through all 4 teams divisions
		for (index = 0; index < numberOfFourTeamDivisions; index++) {

			Division division = divisions.get(index);

			//Add teams to that divisions
			for (int col = 0; col < 4; col++ ) {

				//Add the next viable team
				addTeamToDivision(division, teamsCopy);
			}

		}

		//Loop through all 5 teams divisions
		for (int index2 = index; index2 < numberOfDivisions; index2++) {

			Division division = divisions.get(index2);

			//Add teams to that divisions
			for (int col = 0; col < 5; col++ ) {

				//Add the next viable team
				addTeamToDivision(division, teamsCopy);
			}

		}

		//Sort and return the divisions
		return divisions;
	}

	/**This method basically starts at the beginning of the teams and then
	 * keeps going through until it finds one that can be added*/
	private void addTeamToDivision(Division division, List<Team> teamsCopy) {
		int index = 0;
		boolean viableTeamFound = false;

		do {

			//Check if the division doesn't contain the school
			if (!division.schools.contains(teamsCopy.get(index).school)){

				//Add school,
				division.add(teamsCopy.get(index));
				//Remove it from the list
				teamsCopy.remove(index);
				//Change boolean to reflect that
				viableTeamFound = true;

			//If not found, move the index to check the next team
			} else {
				index++;
			}

			//Keep going through a viable team is found	
		} while (!viableTeamFound);

	}

	/**This method determines the amount of game slots (the amount of columns
	in the array) by first figuring out the split of 4 team divisions and 5
	team divisions, as we know how many games each division must play. Then,
	we take the amount of games played and divide that by the number of courts
	to figure out the number of slots*/
	private int determineNumberOfGameSlots(int numberOfTeams) {

		/*The number of divisions with 4 teams in them is equal to the number
		 * of teams divided by 4 minus the remainder, as the remainder is the
		 * count of divisions with 5 teams*/
		int numberOfFourTeamDivisions = 
				numberOfTeams/4 - numberOfTeams % 4;

		/*The number of divisions with 5 teams = teams (mod 4)*/	
		int numberOfFiveTeamDivisions = numberOfTeams % 4;

		/*Each four team division plays a total of 6 games, as each team plays 3,
		and two teams participate in one game. In a five team division 4 teams 
		play 3 games and one team plays 4 games */
		double totalGames = (numberOfFourTeamDivisions * 6) + 
				(numberOfFiveTeamDivisions * 8);

		//Game slots is the ceiling of total games/number of courts
		return (int)Math.ceil(totalGames/(double)courts.size());

	}

	/**Adds a new court to the list of court with the name @courtName
	 * the amount of courts is incremented by 1*/ 
	public void addCourt(String courtName) {
		courts.add(new Court(courtName));
	}
	
	public void addTeam(Team team) {

		//Add team to general list of teams
		teams.add(team);

		//If team is mens...
		if(team.gender.equals(Gender.MEN)) {

			//Add it to men team list
			mensTeams.add(team);

			//Else, it is a womens team
		} else {

			//Add it to womens team list
			womensTeams.add(team);
		}
	}

	/**Adds a new team to the list of teams for the correct gender*/ 
	public void addTeam(String school, String captain, Gender teamGender,
			String arrivalTime, ArrivalDay arrivalDay) {

		Team newTeam;

		//If school is maryland, create the special MarylandTeam instance
		if (school.toLowerCase().equals("maryland")) {
			newTeam = new MarylandTeam(school,captain,teamGender,
					arrivalTime,arrivalDay);
			//Else just create a regular team	
		} else {
			newTeam = new Team(school,captain,teamGender,arrivalTime,arrivalDay);
		}

		//Add team to general list of teams
		teams.add(newTeam);

		//If team is mens...
		if(teamGender.equals(Gender.MEN)) {

			//Add it to men team list
			mensTeams.add(newTeam);

			//Else, it is a womens team
		} else {

			//Add it to womens team list
			womensTeams.add(newTeam);
		}


	}
	
	@Override 
	public String toString() {
		String string = "";
		for (TimeSlot timeSlot: schedule) {
			string += timeSlot + "\n";
		}
		return string;
	}

	/**This method simply lists the courts in string form*/
	public void listCourts() {
		int courtNumber = 1;

		for (Court court:courts) {
			System.out.println("Court" + (courtNumber++) + ": " + court.name);
		}
	}

	/**This method simply lists the teams in string form*/
	public void listTeams() {
		int teamNumber = 1;

		for (Team team:teams) {
			System.out.println("Team" + (teamNumber++) + ": " + team.school);
		}
	}


	
	


}
