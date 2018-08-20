import java.time.LocalTime;
import java.time.format.*;
import java.util.*;

public class TimeSlot implements Comparable<TimeSlot> {

	//Variables
	Set<Game> games;
	LocalTime time;
	boolean filled;
	
	/**This constructor takes in a string of the time in hh:mm format and then
	 * converts it into a LocalTime variable*/
	public TimeSlot(String timeString) {
		//Convert Time	
		this.time = LocalTime.parse(timeString);
		this.games = new HashSet<Game>();
		this.filled = false;
	}
	
	public TimeSlot(LocalTime time) {
		this.time = time;
		this.games = new HashSet<Game>();
		this.filled = false;
	}

	@Override
	public String toString() {
		return time + ": " + games;
	}

	@Override
	public int compareTo(TimeSlot other) {
		return this.time.compareTo(other.time);
	}
}
