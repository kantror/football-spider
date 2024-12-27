package statistics.football_spider.exceptions;

public class SpiderExceptionHandler {
	
	public static String handlerMessageException(String exception, String message) {
		
		String res = new String("");
		
		res.concat("\n" + "----------" + exception + "----------" + "\n");
		res.concat(message);
		res.concat("\n" + "-----------------------------" + "\n");
		
		return res;
		
	}
	
	public static SpiderRequestException createSpiderRequestException(String message) {
		
		return new SpiderRequestException(message);
		
	}

}
