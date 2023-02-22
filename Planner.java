/**
 * The implementation of a day planner. It stores a collection of events in ascending order
 * of their starting times. The planner supports multiple operations for maintenance, including adding a new event,
 * deleting an event, and updating an event.
 * @author Hongjia Hao
 */
public class Planner{

	/**
	 * Underlying array of events.
	 */
	private MySortedArray<Event> events;
	
	/**
	 * Constructor with no arguments.
	 */
	public Planner(){
	
		this.events = new MySortedArray<>();
		
	}

	/**
	 * Return the number of events in the list.
	 * @return	size
	 */
	public int size(){
		return events.size(); 
	}
	
	/**
	 * ToString method.
	 * @return string
	 */
	public String toString(){
		
		String result = "";
		for(int i = 0; i < events.size(); i++){
			result += ("[" + i + "]");
			result += events.get(i).toString();
			if(i != events.size() - 1){
				result += "\n";
			}
		}
		return result; 
	}
	
	/**
	 * Add a new event into the list.
	 * @param event	event
	 * @throws IllegalArgumentException vaild event
	 */
	public void addEvent(Event event) throws IllegalArgumentException{
		
		if(event == null){
			throw new IllegalArgumentException("Null Event object!");
		}
		events.add(event);

	}
	
	/**
	 * Move the event at index to be start at newStart.
	 * @param index	index
	 * @param newStart	new start time
	 * @return	true if successed
	 */
	public boolean moveEvent(int index, MyTime newStart){
		
		if(index < 0 || index >= events.size()){
			return false;
		}
		if(newStart == null){
			return false;
		}
		if(events.get(index).moveStart(newStart) == false){
			return false;
		}
		Event temp = events.get(index);
		events.delete(index);
		if(events.add(index, temp) == true){
			return true;
		}
		else{
			events.add(temp);
			return true;
		}
	}

	/**
	 * Change the duration of event at index to be the given number of minutes.
	 * @param index		index
	 * @param minute	minutes
	 * @return	true if successed
	 */
	public boolean changeDuration (int index, int minute){
		
		if(index < 0 || index >= events.size()){
			return false;
		}
		if(minute < 0){
			return false;
		}		
		return events.get(index).changeDuration(minute); 
	
	}

	/**
	 * Change the description of event at index.
	 * @param index	idnex
	 * @param description	description
	 * @return	true if successed
	 */
	public boolean changeDescription(int index, String description){
		
		if(index < 0 || index >= events.size()){
			return false;
		}
		events.get(index).setDescription(description);
		return true;
	}
	
	/**
	 * Remove the event at index.
	 * @param index	index
	 * @return	true if successed
	 */
	public boolean removeEvent(int index){
		
		if(index < 0 || index >= events.size()){
			return false;
		}
		events.delete(index); 
		return true;
	}
	
	/**
	 * Get event.
	 * @param index	idnex
	 * @return	the event at index
	 */
	public Event getEvent(int index){
		
		if(index < 0 || index >= events.size()){
			return null;
		}
		return events.get(index); 
	}

	/**
	 * 5 test cases.
	 * @param args	not used
	 */
	public static void main(String[] args){
	
		// creating a planner
		Planner day1 = new Planner();

		// adding two events		
		Event breakfast = new Event(new MyTime(7), new MyTime(7,30), "breakfast");
		Event jogging = new Event(new MyTime(5), new MyTime(6), "jogging");
		day1.addEvent(breakfast);
		day1.addEvent(jogging);
		
		if (day1.size()==2 && day1.getEvent(0)==jogging && day1.getEvent(1)==breakfast ){
			System.out.println("Yay 1");					
		}
		
		//toString
		if (day1.toString().equals("[0]05:00-06:00/jogging\n[1]07:00-07:30/breakfast")){
			System.out.println("Yay 2");							
		}
		//System.out.println(day1);

		// move start of breakfast		
		MyTime newBFTime = new MyTime(6,30);
		
		if (day1.moveEvent(1, newBFTime) && day1.getEvent(1).getStart().getHour() == 6
			&& day1.getEvent(1).getStart().getMin() == 30){
			System.out.println("Yay 3");								
		}
		//System.out.println(day1);
		
		// change duration
		if (day1.changeDuration(0, 45) && day1.getEvent(0).getEnd().getHour() == 5 
			&& day1.getEvent(0).getEnd().getMin() == 45 && day1.changeDuration(1, 60)
			&& day1.getEvent(1).getEnd().getHour() == 7 
			&& day1.getEvent(1).getEnd().getMin() == 30){
			System.out.println("Yay 4");											
		}
		//System.out.println(day1);
		
		// change description, remove
		if (day1.changeDescription(1, "sleeping") && !day1.removeEvent(3) 
			&& !day1.removeEvent(-2) && day1.removeEvent(0)){
			System.out.println("Yay 5");							
		}
		//System.out.println(day1);
		
	}
}
