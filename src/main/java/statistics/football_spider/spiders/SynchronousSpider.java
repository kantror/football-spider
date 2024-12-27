package statistics.football_spider.spiders;

import java.net.http.HttpResponse;

import statistics.football_spider.exceptions.SpiderRequestException;

public class SynchronousSpider extends AbstractSpider {

	private String url;
	private String path;
	
	public SynchronousSpider(String url) {
		
		super();
		this.url = url;
		this.path = null;
		
	}
	
	public SynchronousSpider(String url, String path) {
		
		super();
		this.url = url;
		this.path = path;
		
	}
	
	@Override
	public void makeRequestAndGetResponse() {
		
		HttpResponse<?> httpResponse;
		
		try {
			
			if(path == null) {
				
				super.makeSynchronousRequest(url);
				
				httpResponse = super.getHttpSyncResponseS();
				System.out.println(httpResponse.body().toString());
				
			}else {
				
				super.makeSynchronousRequest(url, path);
				
				httpResponse = super.getHttpSyncResponseF();
				
			}
			
			
		} catch (SpiderRequestException e) {
			
			e.printStackTrace();
			
		}
		
	}

}
