
/**
 * The implementation of a sorted dynamic array list. 
 * implement this class as a generic class to practice that concept. It will be used as the storage of events in the day
 * planner.
 * @author Hongjia Hao
 * @param <T> Generic class
 */
public class MySortedArray<T extends Comparable<T>> {
	/**
	 * Default initial capacity.
	 */
	private static final int DEFAULT_CAPACITY = 2;

	/**
	 * Underlying array for storage.
	 */
	private T[] data;


	/**
	 * Size.
	 */
	private int size;
	
	/**
	 * Constructor.
	 */
	@SuppressWarnings("unchecked")
	public MySortedArray() {

		this.data = (T[])new Comparable[DEFAULT_CAPACITY];
		this.size = 0;
		
	}

	/**
	 * Constructor.
	 * @param initialCapacity	capacity
	 * @throws IllegalArgumentException	capacity >= 2
	 */
	@SuppressWarnings("unchecked")
	public MySortedArray(int initialCapacity) throws IllegalArgumentException{
		
		if(initialCapacity < 2){
			throw new IllegalArgumentException("Capacity must be at least 2!");
		}
		this.data = (T[])new Comparable[initialCapacity];
		this.size = 0;
		
	}
	
	/**
	 * Report the current number of elements.
	 * @return size
	 */
	public int size() {	
		
		return this.size; 

	}  
	
	/**
	 * Report max number of elements before the next expansion.
	 * @return	capacity
	 */
	public int capacity() { 
		
		return data.length; 
	}

	/**
	 * Insert the given value into the array and keep the array _SORTED_ in ascending order.
	 * @param value	given value
	 * @throws IllegalStateException	maxium capacity
	 * @throws IllegalArgumentException	vaild value
	 */
	public void add(T value) throws IllegalStateException, IllegalArgumentException{
		
		if(value == null){
			throw new IllegalArgumentException("Cannot add: null value!");
		}
		if(this.size() == this.capacity()){
			if(this.doubleCapacity() == false){
				throw new IllegalStateException("Cannot add: capacity upper-bound reached!");
			}
		}
		if(this.size() == 0){
			data[0] = value;
			this.size += 1;
		}
		else{
			int index = this.size();
			for(int i = 0; i < this.size(); i++){
				if(value.compareTo(this.data[i]) < 0){
					index = i;
					break;
				}
			}
			for(int i = this.size(); i > index; i--){
				data[i] = data[i - 1];
			}
			data[index] = value;
			this.size += 1;
		}

	}

	/**
	 * Return the item at the given index.
	 * @param index	index
	 * @return		value
	 * @throws IndexOutOfBoundsException	vaild index
	 */
	public T get(int index) throws IndexOutOfBoundsException{
		
		if(index >= this.size() || index < 0){
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}
		return data[index]; 

	}

	/**
	 * Change the item at the given index to be the given value.
	 * @param index	index
	 * @param value	value
	 * @return	true if sucessed, false if not
	 * @throws IndexOutOfBoundsException	vaild index
	 * @throws IllegalArgumentException		vaild value
	 */
	public boolean replace(int index, T value) throws IndexOutOfBoundsException, IllegalArgumentException{
		
		if(index >= this.size() || index < 0){
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}
		if(value == null){
			throw new IllegalArgumentException("Cannot add: null value!");
		}
		T temp = data[index];
		data[index] = value;
		if(index == 0){
			if(value.compareTo(data[1]) > 0){
				data[index] = temp;
				return false;
			}
		}
		else if(index == this.size() - 1){
			if(value.compareTo(data[this.size() - 2]) < 0){
				data[index] = temp;
				return false;
			}
		}
		else{
			if(value.compareTo(data[index - 1]) < 0 || value.compareTo(data[index + 1]) > 0){
				data[index] = temp;
				return false;
			}
		}
		return true;


	}

	/**
	 * Insert the given value at the given index. Shift elements if needed.
	 * @param index	index
	 * @param value value
	 * @return	true if sucessed, false if not
	 * @throws IndexOutOfBoundsException	vaild index
	 * @throws IllegalArgumentException		vaild value
	 * @throws IllegalStateException		vaild capacity
	 */
	public boolean add(int index, T value) throws IndexOutOfBoundsException, IllegalArgumentException, IllegalStateException{
		
		if(index == 0 && this.size() == 0){
			this.add(value);
			return true;
		}
		if(index > this.size() || index < 0){
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}
		if(value == null){
			throw new IllegalArgumentException("Cannot add: null value!");
		}
		if(index == 0){
			if(value.compareTo(data[1]) > 0){
				return false;
			}
		}
		else if(index == this.size()){
			if(value.compareTo(data[this.size() - 1]) < 0){
				return false;
			}
		}
		else{
			if(value.compareTo(data[index - 1]) < 0 || value.compareTo(data[index]) > 0){
				return false;
			}
		}
		if(this.size() == this.capacity()){
			if(this.doubleCapacity() == false){
				throw new IllegalStateException("Cannot add: capacity upper-bound reached!");
			}
		}
		for(int i = this.size(); i >= index; i--){
			data[i] = data[i - 1];
		}
		data[index] = value;
		this.size += 1;
		return true;
	} 
	
	/**
	 * Remove and return the element at the given index. Shift elements.
	 * @param index	index
	 * @return	deleted item
	 * @throws IndexOutOfBoundsException	vaild index
	 */
	public T delete(int index) throws IndexOutOfBoundsException{
		
		T result = data[index];
		int oldSize = this.size();
		if(index >= this.size() || index < 0){
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}
		for(int i = index + 1; i < this.size(); i++){
			data[i - 1] = data[i];
		}
		data[oldSize - 1] = null;
		this.size -= 1;
		if(this.size() <= (this.capacity() / 3)){
			this.halveCapacity();
		}
		return result;
	}  

	/**
	 * Double the max number of items allowed in data storage.
	 * @return	true if successed, false if not
	 */
	@SuppressWarnings("unchecked")
	public boolean doubleCapacity(){
		if(this.capacity() == Integer.MAX_VALUE - 50){
			return false;
		}
		if(this.capacity() * 2 < Integer.MAX_VALUE - 50){
			T[] temp = (T[])new Comparable[this.capacity() * 2];;
			for(int i = 0; i < this.data.length; i++){
				temp[i] = data[i];
			}
			this.data = temp;
			return true;
		}
		else{
			T[] temp = (T[])new Comparable[Integer.MAX_VALUE - 50];;
			for(int i = 0; i < this.data.length; i++){
				temp[i] = data[i];
			}
			this.data = temp;
			return true;
		}
	}

	/**
	 * Reduce the max number of items allowed in data storage by half.
	 * @return	true if successed, false if not
	 */
	@SuppressWarnings("unchecked")
	public boolean halveCapacity(){
		
		int capacity = this.capacity();
		if(this.capacity() % 2 == 1){
			capacity += 1;
		}
		capacity = this.capacity() / 2;
		if(capacity < 2){
			capacity = 2;
		}
		if(capacity < this.size()){
			return false;
		}
		else{
			T[] temp = (T[])new Comparable[capacity];
			for(int i = 0; i < temp.length; i++){
				temp[i] = data[i];
			}
			data = temp;
			return true;
		}
		

	}
