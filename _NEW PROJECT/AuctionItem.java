
public class AuctionItem 
{
	private String name;
	private int startingPrice;
	
	public AuctionItem(String name, int startingPrice)
	{
		this.name = name;
		this.startingPrice = startingPrice;
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
