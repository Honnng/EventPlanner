/**
 * The class representing a time with two integer components: an hour within [0,23] and
 * a minute within [0,59]. It implements Comparable interface. Comparison of MyTime objects is the basis of
 * ordering events in our day planner.
 * @author Hongjia Hao
 */
public class MyTime implements Comparable<MyTime> {

	/**
	 * Hour.
	 */
	private int hour;
	/**
	 * Minutes.
	 */
	private int min;
	
	/**
	 * Default Constructor.
	 */
	public MyTime(){
		
		this.hour = 0;
		this.min = 0;
		
	}
	
	/**
	 * Constructor with hour specified.
	 * @param hour	hour
	 * @throws IllegalArgumentException	A valid hour can only be within [0, 23]
	 */
	public MyTime(int hour) throws IllegalArgumentException{
		if(hour < 0 || hour > 23){
			throw new IllegalArgumentException("Hour must be within [0, 23]!");
		}
		else{
			this.hour = hour;
			this.min = 0;
		}
	}
	
	/**
	 * Constructor with hour and minutes specified.
	 * @param hour  hour
	 * @param min	minutes
	 * @throws IllegalArgumentException	vaild hour within[0, 23], minutes within [0, 59]
	 */
	public MyTime(int hour, int min) throws IllegalArgumentException{
		
		if(hour < 0 || hour > 23 || min < 0 || min > 59){
			throw new IllegalArgumentException("Hour must be within [0, 23]; Minute must be within [0, 59]!");
		}
		else{
			this.hour = hour;
			this.min = min;
		}


	}
	
	/**
	 * Get hour.
	 * @return	hour
	 */
	public int getHour(){
		return hour; 
	}

	/**
	 * Get minutes.
	 * @return	minutes
	 */
	public int getMin(){
		return min;
	}
	
	/**
	 * Compare two times for ordering.
	 * @param otherTime	time to compare
	 * @return 	result of compare
	 * @throws IllegalArgumentException	vaild time
	 */
	@Override 
	public int compareTo(MyTime otherTime) throws IllegalArgumentException{
		
		if(otherTime == null){
			throw new IllegalArgumentException("Null Time object!");
		}
		if(this.getHour() > otherTime.getHour()){
			return 1;
		}
		else if(this.getHour() < otherTime.getHour()){
			return -1;
		}
		else{
			if(this.getMin() > otherTime.getMin()){
				return 1;
			}
			else if(this.getMin() < otherTime.getMin()){
				return -1;
			}
			else{
				return 0;
			}
		}
	}
	
	/**
	 * Calculate the total time used in a event.
	 * @param endTime	end time
	 * @return	the number of minutes starting from this Time and ending at endTime
	 * @throws IllegalArgumentException	vaild time
	 */
	public int getDuration(MyTime endTime) throws IllegalArgumentException{
		
		if(endTime == null){
			throw new IllegalArgumentException("Null Time object");
		}
		if(this.compareTo(endTime) > 0){
			return -1;
		}
		else{
			int totalhour = endTime.getHour() - this.getHour();
			int totalmin = endTime.getMin() - this.getMin();
			int totaltime = totalhour * 60 + totalmin;
			return totaltime;
		}
	}
	
	/**
	 * Calculate the end time.
	 * @param duration	time used
	 * @return	end time
	 * @throws IllegalArgumentException	vaild duration
	 */
	public MyTime getEndTime(int duration) throws IllegalArgumentException{
		
		if(duration < 0){
			throw new IllegalArgumentException("Duration must be non-negative!");
		}
		int durationMin = duration % 60;
		int durationHour = (duration - durationMin) / 60;
		int newHour = this.getHour() + durationHour;
		int newMin = this.getMin() + durationMin;
		if(newMin >= 60){
			newHour += 1;
			newMin -= 60;
		}
		if(newHour >= 24){
			return null;
		}
		else{
			MyTime newTime = new MyTime(newHour, newMin);
			return newTime;
		}
	}

	/**
	 * ToString method.
	 * @return HH:MM
	 */
	public String toString() {
		
		String strHour = String.format("%02d", this.getHour());
		String strMin = String.format("%02d", this.getMin());
		String result = strHour + ":" + strMin;
		return result; 
	}
	
	/**
	 * 3 test cases.
	 * @param args	not used
	 */
	public static void main(String[] args){
		
		MyTime time1 = new MyTime(7);
		MyTime time2 = new MyTime(9,30);
		
		//checking hour/minute
		if (time1.getHour() == 7 && time1.getMin() == 0 && time2.getHour() == 9
			&& time2.getMin() == 30){
			System.out.println("Yay 1");			
		}		
	
		//compareTo, duration
		if (time1.compareTo(time2) < 0 && time1.compareTo(new MyTime(7,0)) == 0
			&& time2.compareTo(time1) > 0 && time1.getDuration(time2) == 150){
			System.out.println("Yay 2");						
		}
		
		//getEndTime
		MyTime time3 = time1.getEndTime(500);
		if (time3!=null && time3.getHour() == 15 && time3.getMin() == 20 
			&& time2.getEndTime(870) == null){
			System.out.println("Yay 3");								
		}
		
	}
}
