/**
 * The implementation of an event with a starting time, an ending time, and a description. It
 * also implements Comparable. The ordering of two events is determined by the ordering of their starting times.
 * @author Hongjia Hao
 */
public class Event implements Comparable<Event> {

	//starting and ending time of the event
	/**
	 * Start time.
	 */
	private MyTime startTime;	
	/**
	 * End time.
	 */
	private MyTime endTime;
		
	//description of the event
	/**
	 * Description.
	 */
	private String description;
	
	/**
	 * Constructor with start and end times.
	 * @param startTime	start time
	 * @param endTime	end time
	 * @throws IllegalArgumentException	vaild time
	 */
	public Event(MyTime startTime, MyTime endTime) throws IllegalArgumentException{
		// constructor with start and end times
		// set description to be empty string ""
		
		// Throw IllegalArgumentException if endTime comes before startTime
		// - Assume that the start time can be the same as the end time 
		//   (0-duration event allowed)

		// Throw IllegalArgumentException if either time is null. 
		if(startTime == null || endTime == null){
			throw new IllegalArgumentException("Null Time object!");
		}
		else if(endTime.compareTo(startTime) < 0){
			throw new IllegalArgumentException("End Time cannot come before Start Time!");
		}
		else{
			this.startTime = startTime;
			this.endTime = endTime;
			this.description = "";
		}
				
	}
	
	/**
	 * Constructor with start time, end time, and description.
	 * @param startTime	start time
	 * @param endTime	end time
	 * @param description	description
	 * @throws IllegalArgumentException	vaild time
	 */
	public Event(MyTime startTime, MyTime endTime, String description) throws IllegalArgumentException{
		// constructor with start time, end time, and description
		
		// perform the same checking of start/end times and 
		// throw the same exception as the constructor above
		
		// if description argument is null, 
		// set description of the event to be empty string ""
		if(startTime == null || endTime == null){
			throw new IllegalArgumentException("Null Time object!");
		}
		else if(endTime.compareTo(startTime) < 0){
			throw new IllegalArgumentException("End Time cannot come before Start Time!");
		}
		else{
			this.startTime = startTime;
			this.endTime = endTime;
		}
		if(description == null){
			this.description = "";
		}
		else{
			this.description = description;
		}
	}
	
	/**
	 * Report starting time.
	 * @return	start time
	 */
	public MyTime getStart(){
		// report starting time

		return this.startTime; //default return, remove/change as needed
	}
	
	/**
	 * Report end time.
	 * @return	end time
	 */
	public MyTime getEnd(){
		// report starting time

		return this.endTime; //default return, remove/change as needed
	}

	/**
	 * Report description.
	 * @return description
	 */
	public String getDescription(){
		// report description
		
		return this.description; //default return, remove/change as needed
	}
	
	/**
	 * Compare two times for ordering.
	 * @param otherEvent	other event
	 * @return 	result of compare
	 * @throws IllegalArgumentException	vaild event
	 */
	@Override 
	public int compareTo(Event otherEvent) throws IllegalArgumentException{
		// compare two times for ordering
		
		// The ordering of two events is the same as the ordering of their start times
	
		// Throw IllegalArgumentException if otherEvent is null.
		if(otherEvent == null){
			throw new IllegalArgumentException("Null Event object!");
		}
		int result = this.getStart().compareTo(otherEvent.getStart());
		return result; //default return, remove/change as needed

	}

	/**
	 * Move the start time of this Event to be newStart but keep the same duration.
	 * @param newStart	new start time
	 * @return	true if success, false if not
	 */
	public boolean moveStart(MyTime newStart){
		// Move the start time of this Event to be newStart but keep the same duration. 
		
		// The start time can be moved forward or backward but the end time cannot 
		// go beyond 23:59 of the same day.  Do not update the event if this condition
		// cannot be satisfied and return false.  Return false if newStart is null. 
		
		// Return true if the start time can be moved to newStart successfully.
		
		int duration = this.getStart().getDuration(this.getEnd());
		MyTime newEnd = newStart.getEndTime(duration);
		if(newEnd == null){
			return false;
		}
		else{
			this.startTime = newStart;
			this.endTime = newEnd;
			return true;
		}
	}
	
	/**
	 * Change the duration of event to be the given number of minutes.
	 * @param minute	minutes to changed
	 * @return	true if success, false if not
	 */
	public boolean changeDuration(int minute){
		// Change the duration of event to be the given number of minutes.
		// Update the end time of event based on the updated duration.	
			
		if(minute < 0){
			return false;
		}
		MyTime newEnd = this.getStart().getEndTime(minute);
		if(newEnd == null){
			return false;
		}
		else{
			this.endTime = newEnd;
			return true;
		}
		//return false; //default return, remove/change as needed
	
	}
	
	/**
	 * Set the description of this event.
	 * @param newDescription	new description
	 */
	public void setDescription(String newDescription){
		// set the description of this event

		// if newDescription argument is null, 
		// set description of the event to be empty string ""
		if(newDescription == null){
			this.description = "";
		}
		else{
			this.description = newDescription;
		}
	
	}
	
	/**
	 * Return a string representation of the event in the form of startTime-endTime/description.
	 * @return string
	 */
	public String toString(){
		String result = this.getStart().toString() + "-" + this.getEnd().toString() + "/" + this.getDescription();
		return result; //default return, remove/change as needed
	
	}
	
	/**
	 * This is a main method with 5 test cases.
	 * @param args	not used
	 */
	public static void main(String[] args){
		// creating an event
		Event breakfast = new Event(new MyTime(7), new MyTime(7,30), "breakfast");
		
		// checking start/end times
		if (breakfast.getStart()!=null && breakfast.getEnd()!=null &&
			breakfast.getStart().getHour() == 7 && breakfast.getEnd().getHour() == 7 && 
			breakfast.getStart().getMin() == 0 && breakfast.getEnd().getMin() == 30){
			System.out.println("Yay 1");			
		}		
		//System.out.println(breakfast);
		//expected output (excluding quote):
		//"07:00-07:30/breakfast"

		// moveStart
		if (breakfast.moveStart(new MyTime(6,30)) && breakfast.getStart().getHour() == 6
			&& breakfast.getStart().getMin() == 30 && breakfast.getEnd().getMin() == 0){
			System.out.println("Yay 2");					
		}
		//System.out.println(breakfast);
		
		//longer duration
		if (breakfast.changeDuration(45) && breakfast.getStart().getHour() == 6
			&& breakfast.getStart().getMin() == 30 && breakfast.getEnd().getMin() == 15
			&& breakfast.getEnd().getHour() == 7){

			System.out.println("Yay 3");					
		}
		//System.out.println(breakfast);
		
		//shorter duration
		if (!breakfast.changeDuration(-10) && breakfast.changeDuration(15) 
			&& breakfast.getStart().getHour() == 6 && breakfast.getStart().getMin() == 30 
			&& breakfast.getEnd().getMin() == 45 && breakfast.getEnd().getHour() == 6){
			System.out.println("Yay 4");					
		}
		//System.out.println(breakfast);
		
		// compareTo
		Event jogging = new Event(new MyTime(5), new MyTime(6), "jogging");
		Event morningNews = new Event(new MyTime(6, 30), new MyTime(7), "morning news");
		if (breakfast.compareTo(jogging)>0 && jogging.compareTo(breakfast)<0
			&& breakfast.compareTo(morningNews) == 0){
			System.out.println("Yay 5");								
		}
	}

}
