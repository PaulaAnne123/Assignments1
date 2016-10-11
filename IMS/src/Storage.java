import java.util.*;

public class Storage implements InventoryManagementSystem {
    private HashMap<String, getData> itemTable;
    private HashMap<String, Location> locationTable;

    public Storage(){
        this.itemTable = new HashMap<String, getData>();
        this.locationTable = new HashMap<String, Location>();
    }

    public Storage addItem(getData item){
    	itemTable.put(item.getName(), item);
        return this;
    }
    public Storage addLocation(Location location){
    	locationTable.put(location.getName(), location);
        return this;
    }

    public HashMap<String, getData> getItems(){
        return this.itemTable;
    }

    public HashMap<String, Location> getLocations(){
        return this.locationTable;
    }

    public void ShowItems(String item_name){
    	//shows item information for a specific item
    	 getData item = this.itemTable.get(item_name);
        System.out.println(item.getName() + " : " + item.getCount() + " left in stock. ");
    }
    
    public void ShowAllItems(Boolean showAll){
    	//shows a list of item names

       System.out.println("All possible items by name (both in and out of stock): ");
		for(Object objname:itemTable.keySet()) {
			   System.out.println(objname);
		 }
       
   }

    public Products pickProduct(String productName, int productCount){
    	//get rid of products when selected
    	 getData item = itemTable.get(productName);
        item.deleteItem(productCount);

        Products productResult = new Products(item);
        return productResult;
    }
    public Results restockProduct(String productName, int productMissing){
    	//add products back in
    	 getData item = itemTable.get(productName);
        item.addItem(productMissing);

        Results results = new Results(item);
        return results;
    }
}
