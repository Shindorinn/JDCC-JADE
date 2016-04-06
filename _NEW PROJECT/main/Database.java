package main;
import java.util.Random;

public class Database 
{
	public static class AuctionDatabase
	{
		private final int ITEMSNUMBER = 10;
		private final int BIDDERSNUMBER = 2;
		private final int ITEMSMINPRICE = 1000;
		private final int ITEMSMAXPRICE = 9000;
		private AuctionItem[] items;
		
		public AuctionItem[] getItems() {
			return items;
		}

		public void setItems(AuctionItem[] items) {
			this.items = items;
		}

		public AuctionDatabase()
		{
			Random rn = new Random();
			items = new AuctionItem[ITEMSNUMBER];
			
			for(int i=0; i < ITEMSNUMBER; i++)
			{
				items[i] = new AuctionItem("item" + i, ITEMSMINPRICE + rn.nextInt(ITEMSMAXPRICE));
			}
		}
	}
}