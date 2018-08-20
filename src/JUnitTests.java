import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import enums.ArrivalDay;
import enums.Gender;

class JUnitTests {

	@Test
	void test() {
		
		//Create tourney
		PreliminaryDay tourney = new PreliminaryDay();
		
		//Add courts
		tourney.addCourt("Eppley1");
		tourney.addCourt("Eppley2");
		tourney.addCourt("Eppley3");
		tourney.addCourt("Eppley4");
		tourney.addCourt("Eppley5");
		
		//Add timining info
		tourney.timeOfFirstGame = "08:30";
		tourney.minutesBetweenGames = 35;
		
		//Add Mens teams
		tourney.addTeam("Northwestern","Oliff", Gender.MEN, "20:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Miami","Miller", Gender.MEN, "21:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Indiana","Nagar", Gender.MEN, "19:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Kansas","Wiggins", Gender.MEN, "20:30", ArrivalDay.THURSDAY);
		tourney.addTeam("Michigan","Mendelsohn", Gender.MEN, "08:00", ArrivalDay.FRIDAY);
		tourney.addTeam("Columbia","Rapoport", Gender.MEN, "22:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Columbia","Lederer", Gender.MEN, "07:00", ArrivalDay.FRIDAY);
		tourney.addTeam("Maryland", "Klausner", Gender.MEN, "20:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Penn","Charnoff", Gender.MEN, "23:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Brandeis","Eisenstein", Gender.MEN, "23:30", ArrivalDay.THURSDAY);
		tourney.addTeam("Duke","Williamson", Gender.MEN, "00:10", ArrivalDay.FRIDAY);
		tourney.addTeam("Syracuse","Greenberg", Gender.MEN, "20:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Yeshiva","Bokor", Gender.MEN, "20:15", ArrivalDay.THURSDAY);
		tourney.addTeam("Yeshiva","Leifer", Gender.MEN, "20:30", ArrivalDay.THURSDAY);
		tourney.addTeam("Chicago","Elias", Gender.MEN, "18:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Baruch","Singer", Gender.MEN, "19:30", ArrivalDay.THURSDAY);
		tourney.addTeam("Penn State","Jones", Gender.MEN, "21:15", ArrivalDay.THURSDAY);
		tourney.addTeam("Hopkins","Feingold", Gender.MEN, "06:00", ArrivalDay.FRIDAY);
		tourney.addTeam("NYU","Best", Gender.MEN, "20:45", ArrivalDay.THURSDAY);
		tourney.addTeam("Virginia","Schwartz", Gender.MEN, "22:15", ArrivalDay.THURSDAY);
		tourney.addTeam("WashU","Helfand", Gender.MEN, "20:30", ArrivalDay.THURSDAY);
		tourney.addTeam("Texas","Durant", Gender.MEN, "09:00", ArrivalDay.FRIDAY);
		tourney.addTeam("Iowa","Smart", Gender.MEN, "18:00", ArrivalDay.THURSDAY);
		tourney.addTeam("North Carolina","Jordan", Gender.MEN, "20:10", ArrivalDay.THURSDAY);
		//tourney.addTeam("Maryland", "Oliff", Gender.MEN, "20:00", ArrivalDay.THURSDAY);
		MarylandTeam marylandOliff = new MarylandTeam 
				("Maryland", "Oliff", Gender.MEN, "20:00", ArrivalDay.THURSDAY);
		marylandOliff.addConstraint("12:00", "14:00");
		tourney.addTeam(marylandOliff);
		
		//Add women teams
		tourney.addTeam("HebrewU","Harris", Gender.WOMEN, "20:30", ArrivalDay.THURSDAY);
		tourney.addTeam("Baylor","Griner", Gender.WOMEN, "00:10", ArrivalDay.FRIDAY);
		tourney.addTeam("Notre Dame","Loyd", Gender.WOMEN, "20:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Yeshiva","Gordon", Gender.WOMEN, "20:15", ArrivalDay.THURSDAY);
		tourney.addTeam("Yeshiva","Miller", Gender.WOMEN, "20:30", ArrivalDay.THURSDAY);
		tourney.addTeam("Illinois","Retsky", Gender.WOMEN, "18:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Baruch","Stein", Gender.WOMEN, "19:30", ArrivalDay.THURSDAY);
		tourney.addTeam("Penn State","Rosenstein", Gender.WOMEN, "21:15", ArrivalDay.THURSDAY);
		tourney.addTeam("Maryland","Warshawsky", Gender.WOMEN, "06:00", ArrivalDay.FRIDAY);
		tourney.addTeam("NYU","Chavel", Gender.WOMEN, "20:45", ArrivalDay.THURSDAY);
		tourney.addTeam("Maryland", "Loh", Gender.WOMEN, "20:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Virginia","Goldsmith", Gender.WOMEN, "22:15", ArrivalDay.THURSDAY);
		tourney.addTeam("Northwestern","Benmelech", Gender.WOMEN, "20:30", ArrivalDay.THURSDAY);
		tourney.addTeam("Texas","Yaron", Gender.WOMEN, "09:15", ArrivalDay.FRIDAY);
		tourney.addTeam("UMBC","Smart", Gender.WOMEN, "18:00", ArrivalDay.THURSDAY);
		tourney.addTeam("Michigan","Miller", Gender.WOMEN, "20:10", ArrivalDay.THURSDAY);

		//Do the magic
		tourney.organizeTournament();
		tourney.scheduleTournament();
		System.out.println(tourney);
		
	}

}
