package main;

public class AuctionItem 
{
	private String name;
	private int startingPrice;
	private int minimumPrice;
	
	public AuctionItem(String name, int startingPrice, int minimumPrice)
	{
		this.name = name;
		this.startingPrice = startingPrice;
		this.minimumPrice = minimumPrice;
		
		if(minimumPrice >= startingPrice){
			System.out.println("Starting price was lower or equal to the minimum price!");
			System.out.println("Starting price: " + startingPrice + " Minimum price : " + minimumPrice);
			System.exit(-1);
		}
	}

	public int getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(int minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(int startingPrice) {
		this.startingPrice = startingPrice;
	}
}
