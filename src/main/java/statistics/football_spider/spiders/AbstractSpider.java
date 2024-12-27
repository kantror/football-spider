package statistics.football_spider.spiders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import statistics.football_spider.exceptions.SpiderExceptionHandler;
import statistics.football_spider.exceptions.SpiderRequestException;

public abstract class AbstractSpider extends Thread implements Spider{

	private HttpClient httpClient;
	private HttpRequest httpRequest;
	private HttpResponse<String> httpSyncResponseS;
	private HttpResponse<Path> httpSyncResponseF;
	private CompletableFuture<String> httpAsyncResponseS;
	private CompletableFuture<Path> httpAsyncResponseF;
	
	public AbstractSpider() {
		
		httpClient = HttpClient.newHttpClient();
		
	}
	
	protected boolean createHttpRequest(String uri) {
		
		boolean res = false;
		
		try {
			
			this.httpRequest = HttpRequest.newBuilder().uri(URI.create(uri)).build();
			res = true;
			
		}catch(NullPointerException e) {
			
			SpiderExceptionHandler.handlerMessageException(e.getClass().getName(), e.getMessage());
			
		}catch(IllegalArgumentException e) {
			
			SpiderExceptionHandler.handlerMessageException(e.getClass().getName(), e.getMessage());
			
		}
		
		return res;
		
	}
	
	protected void makeSynchronousRequest(String uri) throws SpiderRequestException{
		
		if(createHttpRequest(uri)) {
			
			try {
				
				this.httpSyncResponseS = httpClient.send(httpRequest, BodyHandlers.ofString());
				
			} catch (IOException e) {
				
				SpiderExceptionHandler.handlerMessageException(e.getClass().getName(), e.getMessage());
				throw SpiderExceptionHandler.createSpiderRequestException("Peticion no se ha podido realizar correctamente");
				
			} catch (InterruptedException e) {
				
				SpiderExceptionHandler.handlerMessageException(e.getClass().getName(), e.getMessage());
				throw SpiderExceptionHandler.createSpiderRequestException("Peticion no se ha podido realizar correctamente");
				
			}
			
		}else {
			
			throw SpiderExceptionHandler.createSpiderRequestException("Peticion no se ha podido realizar correctamente");
			
		}
		
	}
	
	protected void makeSynchronousRequest(String uri, String path) throws SpiderRequestException{
		
		if(createHttpRequest(uri)) {
			
			try {
				
				this.httpSyncResponseF = httpClient.send(httpRequest, BodyHandlers.ofFile(Paths.get(path)));
				
			} catch (IOException e) {
				
				SpiderExceptionHandler.handlerMessageException(e.getClass().getName(), e.getMessage());
				throw SpiderExceptionHandler.createSpiderRequestException("Peticion no se ha podido realizar correctamente");
				
			} catch (InterruptedException e) {
				
				SpiderExceptionHandler.handlerMessageException(e.getClass().getName(), e.getMessage());
				throw SpiderExceptionHandler.createSpiderRequestException("Peticion no se ha podido realizar correctamente");
				
			}
			
		}else {
			
			throw SpiderExceptionHandler.createSpiderRequestException("Peticion no se ha podido realizar correctamente");
			
		}
		
	}
	
	protected void makeAsynchronousRequest(String uri) throws SpiderRequestException{
		
		if(createHttpRequest(uri)) {
			
			try {
				
				this.httpAsyncResponseS = httpClient.sendAsync(httpRequest, BodyHandlers.ofString()).thenApply(HttpResponse::body);
				
			} catch (IllegalArgumentException e) {
				
				SpiderExceptionHandler.handlerMessageException(e.getClass().getName(), e.getMessage());
				throw SpiderExceptionHandler.createSpiderRequestException("Peticion no se ha podido realizar correctamente");
				
			} 
			
		}else {
			
			throw SpiderExceptionHandler.createSpiderRequestException("Peticion no se ha podido realizar correctamente");
			
		}
		
	}
	
	protected void makeAsynchronousRequest(String uri, String path) throws SpiderRequestException{
		
		if(createHttpRequest(uri)) {
			
			try {
				
				this.httpAsyncResponseF = httpClient.sendAsync(httpRequest, BodyHandlers.ofFile(Paths.get(uri))).thenApply(HttpResponse::body);
				
			} catch (IllegalArgumentException e) {
				
				SpiderExceptionHandler.handlerMessageException(e.getClass().getName(), e.getMessage());
				throw SpiderExceptionHandler.createSpiderRequestException("Peticion no se ha podido realizar correctamente");
				
			} 
			
		}else {
			
			throw SpiderExceptionHandler.createSpiderRequestException("Peticion no se ha podido realizar correctamente");
			
		}
		
	}

	protected HttpClient getHttpClient() {
		return httpClient;
	}

	protected void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	protected HttpRequest getHttpRequest() {
		return httpRequest;
	}

	protected void setHttpRequest(HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	protected HttpResponse<String> getHttpSyncResponseS() {
		return httpSyncResponseS;
	}

	protected void setHttpSyncResponseS(HttpResponse<String> httpSyncResponseS) {
		this.httpSyncResponseS = httpSyncResponseS;
	}

	protected HttpResponse<Path> getHttpSyncResponseF() {
		return httpSyncResponseF;
	}

	protected void setHttpSyncResponseF(HttpResponse<Path> httpSyncResponseF) {
		this.httpSyncResponseF = httpSyncResponseF;
	}

	protected CompletableFuture<String> getHttpAsyncResponseS() {
		return httpAsyncResponseS;
	}

	protected void setHttpAsyncResponseS(CompletableFuture<String> httpAsyncResponseS) {
		this.httpAsyncResponseS = httpAsyncResponseS;
	}

	protected CompletableFuture<Path> getHttpAsyncResponseF() {
		return httpAsyncResponseF;
	}

	protected void setHttpAsyncResponseF(CompletableFuture<Path> httpAsyncResponseF) {
		this.httpAsyncResponseF = httpAsyncResponseF;
	}
	
	
	
}
