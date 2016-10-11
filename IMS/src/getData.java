
public class getData {
	
	//this class gets the counts of items, names, and where they are
	 private String product;
	    private int count;
	    private Location location;

	    public getData(String name, Location location, int count){
	        this.product = name;
	        this.location = location;
	        this.count = count;
	    }

	    public String getName(){
	        return this.product;
	    }
	    public int getCount(){
	        return this.count;
	    }
	    public Location getLocation(){
	        return this.location;
	    }

	    public void deleteItem(int count){
	        this.count = this.count - count;
	    }
	    public void addItem(int count){
	        this.count = this.count + count;
	    }
}
