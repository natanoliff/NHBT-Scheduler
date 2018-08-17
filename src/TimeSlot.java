import java.time.LocalTime;
import java.time.format.*;
import java.util.*;

public class TimeSlot {

	//Variables
	List<Game> games;
	LocalTime time;
	
	/**This constructor takes in a string of the time in hh:mm format and then
	 * converts it into a LocalTime variable*/
	public TimeSlot(String timeString) {
		//Convert Time	
		this.time = LocalTime.parse(timeString);
		this.games = new ArrayList<Game>();
	}
	
	public TimeSlot(LocalTime time) {
		this.time = time;
		this.games = new ArrayList<Game>();
	}
}
