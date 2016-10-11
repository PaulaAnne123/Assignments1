import java.io.*;  

public class start {
    public static Storage itemStorage = new Storage();

    public static void main(String args[]) {
    	itemStorage = buildData(itemStorage);
    	//builds the list of items in an object
    	
        new Thread(new runner()).start();
        //displays the info and lists
    }

    static class runner implements Runnable {
        public void run(){
            int menuItem = 0;
            BufferedReader bufferedInput = null;
            
            try{

            	while (menuItem != 5) {

            		System.out.println("Select from the following:");
            		System.out.println("1 = Select items from inventory");
            		System.out.println("2 = View item details from inventory");
            		System.out.println("3 = Restock items back into inventory");
            		System.out.println("4 = Show list of existing item names");
            		System.out.println("5 = Close IMS Program");
            		
                	
            		InputStreamReader input=new InputStreamReader(System.in);  
            		bufferedInput=new BufferedReader(input); 
            		
            		//reset the buffered input
            		bufferedInput.mark(0); 
            		bufferedInput.reset(); 
                
            		try {
            			//reads the menu entry from the user
            			menuItem = Integer.parseInt(bufferedInput.readLine());
            		}catch (NumberFormatException e){
            			System.out.println("Non-numeric input, please input an integer."); 
            			break;
            		} 
                
            		//takes the menu entry and selects a method
                itemStorage = select(menuItem);

            	}
            	//close the buffer object
            	bufferedInput.close();
            }
            
            catch (IOException e){
                System.out.println("Error, please try again."); 
            }
            menuItem = 0;
         }
     }


    public static Storage select(int menuItem){
        String productName;
        int productCount;
        int productMissing;
        boolean showAllItems = false;

        try {
            switch (menuItem) {
            case 1:
            	//lets the user pick items from inventory
                productName = findItem(itemStorage);
                
                if (productName.equals("Empty")){
                	//allows the user a back option
                	break;
                }
                
                //number picked
                productCount = verifyProductCount(productName, itemStorage);
                
                Products productResult = itemStorage.pickProduct(productName, productCount);

                //output the data
                System.out.println("You have selected " + productCount + " of " + productResult.getItem().getName() + 
                ". Remaining inventory count for "+ productResult.getItem().getName() + " is " + 
                productResult.getItem().getCount() + " and located in " + productResult.getItem().getLocation().getName());

                System.out.println();
                break;
                
            case 2:
            	//views detail on one item
                productName = findItem(itemStorage);
                
                if (productName.equals("Empty")){
                	//allows the user a back option
                	break;
                }
                
                itemStorage.ShowItems(productName);
                System.out.println();
                break;
                
            case 3:
            	//restocks items
                productName = findItem(itemStorage);
                
                if (productName.equals("Empty")){
                	//allows the user a back option
                	break;
                }
                
                productMissing = inventory();

                Results findItems = itemStorage.restockProduct(productName,productMissing);

                System.out.println("Inventory count for " + findItems.getItem().getName() + " is now "
                        + findItems.getItem().getCount() + " and located in: " +
                		findItems.getItem().getLocation().getName());

                System.out.println();
                break;
           
            case 4:
            	//show all item names possible
            	showAllItems = true;
                itemStorage.ShowAllItems(showAllItems);
                System.out.println();
                showAllItems = false;
                break;
                
            case 5:
                System.out.println("IMS - Exiting Program");
                System.out.println();
                break;
            
                
        	default:
        		//if any other character, restart
        		break;
            }
        } finally {
        	if (menuItem != 4){
        	//output Search Complete if not exiting
        	System.out.println("Search Complete.");
        	System.out.println();
        	}
        }

        return itemStorage;
    }

    public static String findItem(Storage itemStorage){

    	boolean state = true;
    	String strItem = "";
    	BufferedReader bufferedInput = null;
 
        try {

        	while (state) {

            	String exit = "";
            	strItem = "";

            	System.out.println("Please enter a product and press Enter/Return:");
            	bufferedInput = new BufferedReader(new InputStreamReader(System.in)); 
            	strItem = bufferedInput.readLine();
            
            	//check to see if the item exists in the hashmap, if not, prompt
            	//the user to continue or go back
        		if(itemStorage.getItems().containsKey(strItem)){
        			state = false;
        		} else {
        			System.out.println("Item does not exist.");
        			System.out.println("To go back to the main menu, type 'y' and press enter. To continue, press any other key.");

        			exit = bufferedInput.readLine();
        			exit = exit.toLowerCase();
                
        			//go back to main menu
        			if (exit.equals("y")){
        				strItem = "Empty";
        				state = false;
        			}
                
        		}

        		bufferedInput.mark(0);
        		bufferedInput.reset();

        	}
        }
        catch (IOException e){
            System.out.println("Error, please try again."); 
             }
        
        return strItem;
    }

    public static int verifyProductCount(String productName, Storage itemStorage){

        try{
        	InputStreamReader input=new InputStreamReader(System.in);  
        	BufferedReader bufferedInput=new BufferedReader(input);
		
        	boolean state = true;
        	getData item = itemStorage.getItems().get(productName);
        	String exit = "";

        	while (state) {
        		//The user enters the amount of a known item and it is validated in the code
        		System.out.println("Please enter the number of items followed by Enter/Return");
        		String selectedAmount = bufferedInput.readLine();
        		if(isInteger(selectedAmount)) {
        			int amountToPick = Integer.parseInt(selectedAmount);
        			if (amountToPick <= item.getCount()) {
        				return amountToPick;
        			} else {
        				System.out.println(item.getName() + " has less in stock than requested and cannot be ordered in this amount.");
        				System.out.println("Re-enter the item count required.");
        				}
        			}  else {
        			System.out.println("Please enter a positive, whole number");
        		}
            
            System.out.println("To go back to the main menu, type 'y' and press enter. To continue, press any other key.");

            exit = bufferedInput.readLine();
            exit = exit.toLowerCase();
            
            //back to main menu
            if (exit.equals("y")){
            	state = false;
            	break;
            }
        }
    }
        catch (IOException e){
            System.out.println("Error, please try again."); 
        }
        return 0;
    }

    public static int inventory(){
    	
    	 boolean state = true;
    	
    	try{
       
    		while (state) {
    			System.out.println("Please enter the number of items followed by Enter/Return");
        		InputStreamReader input=new InputStreamReader(System.in);  
        		BufferedReader bufferedInput=new BufferedReader(input);   
    			String amountToRestock = bufferedInput.readLine();
    			//enter amount to order and add it to the data structure
    			if(isInteger(amountToRestock)) {
    				return Integer.parseInt(amountToRestock);
    			} else {
    				System.out.println("Please enter a positive, whole number");
    			}
    		}
    	}
        catch (IOException e){
        	//error reading the text
            System.out.println("Error, please try again."); 
        }
        return 0;
    }

    public static boolean isInteger(String s) {
    	//check for int
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        if ( Integer.parseInt(s) < 0 ){
            return false;
        }
        return true;
    }

    
    
    public static Storage buildData(Storage itemStorage){
    	//creates a data structure to pick from
    	
    	Location buildingA = new Location("Warehouse Building A");
    	Location buildingB = new Location("Warehouse Building B");
        itemStorage.addLocation(buildingA);
        itemStorage.addLocation(buildingB);
        
        
        getData itemA = new getData("ItemA", buildingA, 8);
        getData itemB = new getData("ItemB", buildingB, 2);
        getData itemC = new getData("ItemC", buildingA, 23);
        getData itemD = new getData("ItemD", buildingB, 1);

        itemStorage.addItem(itemA);
        itemStorage.addItem(itemB);
        itemStorage.addItem(itemC);
        itemStorage.addItem(itemD);
      
        return itemStorage;
    }
}
