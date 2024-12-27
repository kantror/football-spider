package statistics.football_spider.spiders;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public interface Spider {

	public void makeRequestAndGetResponse();
	
	
}
