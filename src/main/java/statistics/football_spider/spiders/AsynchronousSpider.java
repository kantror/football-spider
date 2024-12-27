package statistics.football_spider.spiders;

public class AsynchronousSpider extends AbstractSpider {
	
	String url;
	String path;
	
	public AsynchronousSpider(String url) {
			
			super();
			this.url = url;
			this.path = null;
			
	}
	
	public AsynchronousSpider(String url, String path) {
		
		super();
		this.url = url;
		this.path = path;
		
	}
	
	@Override
	public void makeRequestAndGetResponse() {
		// TODO Auto-generated method stub
		
	}

}
