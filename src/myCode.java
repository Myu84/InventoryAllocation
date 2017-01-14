

import java.io.*;
import java.util.*;

class myCode
{

	//YOUR CODE GOES IN THIS FUNCTION
	// Method Author: Menghan Yu
	// Date:   		  2017.01.12
	// Email:  		  yumenghan@gmail.com
	// Phone:  		  519-694-6027
	// Source:	   	  Internship test question(Inventory Allocation)
	public static Map<String, Map<Warehouse, Integer>> getInventoryAllocation(Map<String, Integer> shoppingCart, Address addressOfCustomer) {
		List<Warehouse> nearest = getNearestWarehouses(addressOfCustomer);
		Map<String, Map<Warehouse, Integer>> outerMap = new HashMap<>();

		// for every single product
		for(Map.Entry<String, Integer> entry : shoppingCart.entrySet())
		{
			Map<Warehouse, Integer> innerMap = new HashMap<>();// current innerMap
			String productId = entry.getKey();//return current Product ID
			Integer productNum = entry.getValue();//return current product desire number

			// for current product, build a current product inventory map and search
			Map<Warehouse, Integer> inventory = getInventory(productId);

			while(productNum > 0){

				int i = 0;
				while(i< nearest.size()){

					// if nearest location has an inventory of 0
					// move to the next nearest location
					if(inventory.get(nearest.get(i)) == 0){
						i = i+1;
					}

					if(inventory.get(nearest.get(i)) != 0 && productNum > inventory.get(nearest.get(i))){
						innerMap.put(nearest.get(i), inventory.get(nearest.get(i)));
						productNum = productNum - inventory.get(nearest.get(i));
						i = i + 1;
					}

					// if nearest location has enough inventory
					if(inventory.get(nearest.get(i)) != 0 && productNum < inventory.get(nearest.get(i))){
						innerMap.put(nearest.get(i), productNum);
						outerMap.put(productId, innerMap);
						productNum = productNum - inventory.get(nearest.get(i));
						i = nearest.size() + 1; // move to next location
					}
				}
			}
		}

		return outerMap;
	}



	public static void main(String[] args) throws java.lang.Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		Map<String, Integer> shoppingCart = new HashMap<>();
		while ((input = br.readLine()) != null) {
			String[] inputSplit = input.trim().split(" ");
			if (inputSplit.length == 2) {
				shoppingCart.put(inputSplit[0], Integer.parseInt(inputSplit[1]));
			} else if (inputSplit.length == 1) {
				Map<String, Map<Warehouse, Integer>> allocation = getInventoryAllocation(shoppingCart, new Address(inputSplit[0]));
				SortedSet<String> sortedSet = new TreeSet<>(allocation.keySet());
				for (String key : sortedSet) {
					System.out.println(key);
					Map<Warehouse, Integer> inventoryMapping = allocation.get(key);
					SortedSet<Warehouse> keySet = new TreeSet<>(inventoryMapping.keySet());
					for (Warehouse warehouse : keySet) {
						System.out.println(warehouse.toString() + " " + inventoryMapping.get(warehouse));
					}
				}
			}
		}

	}

	public static List<Warehouse> getNearestWarehouses(Address address) {
		switch (address.getCity()) {
		case "Toronto":
			return Arrays.asList(Warehouse.TORONTO, Warehouse.MONTREAL, Warehouse.EDMONTON, Warehouse.VANCOUVER);
		case "Vancouver":
			return Arrays.asList(Warehouse.VANCOUVER, Warehouse.EDMONTON, Warehouse.TORONTO, Warehouse.MONTREAL);
		case "Montreal":
			return Arrays.asList(Warehouse.MONTREAL, Warehouse.TORONTO, Warehouse.EDMONTON, Warehouse.VANCOUVER);
		case "Edmonton":
			return Arrays.asList(Warehouse.EDMONTON, Warehouse.VANCOUVER, Warehouse.TORONTO, Warehouse.MONTREAL);
		default:
			return Arrays.asList();
		}
	}

	public static Map<Warehouse, Integer> getInventory(String product) {
		Map<Warehouse, Integer> inventoryMap = new HashMap<>();
		switch (product) {
		case "Product1": {
			inventoryMap.put(Warehouse.TORONTO, 10);
			inventoryMap.put(Warehouse.VANCOUVER, 32);
			inventoryMap.put(Warehouse.MONTREAL, 32);
			inventoryMap.put(Warehouse.EDMONTON,52);
			break;
		}
		case "Product2": {
			inventoryMap.put(Warehouse.TORONTO, 0);
			inventoryMap.put(Warehouse.VANCOUVER, 10);
			inventoryMap.put(Warehouse.MONTREAL, 0);
			inventoryMap.put(Warehouse.EDMONTON, 10);
			break;
		}
		case "Product3": {
			inventoryMap.put(Warehouse.TORONTO, 25);
			inventoryMap.put(Warehouse.VANCOUVER,15);
			inventoryMap.put(Warehouse.MONTREAL, 16);
			inventoryMap.put(Warehouse.EDMONTON, 72);
			break;
		}
		case "Product4": {
			inventoryMap.put(Warehouse.TORONTO, 31);
			inventoryMap.put(Warehouse.VANCOUVER,11);
			inventoryMap.put(Warehouse.MONTREAL, 4);
			inventoryMap.put(Warehouse.EDMONTON, 6);
			break;
		}
		case "Product5": {
			inventoryMap.put(Warehouse.TORONTO, 55);
			inventoryMap.put(Warehouse.VANCOUVER, 36);
			inventoryMap.put(Warehouse.MONTREAL, 74);
			inventoryMap.put(Warehouse.EDMONTON, 11);
			break;
		}
		}
		return inventoryMap;
	}

	public enum Warehouse {
		EDMONTON, MONTREAL, TORONTO, VANCOUVER
	}

	public static class Address {
		private final String city;

		public Address(String city) {
			this.city = city;
		}

		public String getCity() {
			return city;
		}
	}
}






