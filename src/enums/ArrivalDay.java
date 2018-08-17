package enums;

/**This Enum is simply the arrival day, Thursday is given a higher value then
 * Friday, this is used to compare arrival times*/
public enum ArrivalDay {
	
	THURSDAY(2),FRIDAY(1);
	
	private int numVal;
	
	ArrivalDay(int numVal){
		this.numVal = numVal;
	}
	
	public int getValue() {
		return this.numVal;
	}
}
