package main;
import java.util.Random;

public class Database 
{
	public static class AuctionDatabase
	{
		public static final int ENGLISH_AUCTION = 1;
		public static final int DUTCH_AUCTION = 2;
		public static final int SECOND_PRICE_AUCTION = 3;
		
		private final int ITEMSNUMBER = 10;
		private final int BIDDERSNUMBER = 2;
		private final int ITEMSMINPRICE = 1000;
		private final int ITEMSMAXPRICE = 9000;
		private final int BIDJUMPPERCENTAGE = 10;

		private AuctionItem[] items;
		// 1=english, 2=dutch, 3=secondprice
		private int auctionType;

		public int getBidJumpPercentage() {
			return BIDJUMPPERCENTAGE;
		}
		
		public int getBidderNumber() {
			return BIDDERSNUMBER;
		}
		
		public AuctionItem[] getItems() {
			return items;
		}

		public void setItems(AuctionItem[] items) {
			this.items = items;
		}
		
		public int getAuctionType() {
			return auctionType;
		}
		
		public void setAuctionType(int auctionType) {
			this.auctionType = auctionType;
		}

		public AuctionDatabase()
		{
			auctionType = 2;
			
			Random rn = new Random();
			items = new AuctionItem[ITEMSNUMBER];
			
			for(int i=0; i < ITEMSNUMBER; i++)
			{
				int startingPrice = ITEMSMINPRICE + rn.nextInt(ITEMSMAXPRICE);
				int minimumPrice = ITEMSMINPRICE + rn.nextInt(startingPrice-ITEMSMINPRICE);
								
				items[i] = new AuctionItem("item" + i, startingPrice, minimumPrice);
			}
		}
	}
}
