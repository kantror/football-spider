package statistics.football_spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import statistics.football_spider.spiders.*;

@SpringBootApplication
public class FootballSpiderApplication {

	public static void main(String[] args) {
		//SpringApplication.run(FootballSpiderApplication.class, args);
		
		Spider spider1 = new SynchronousSpider("https://www.google.es");
		spider1.makeRequestAndGetResponse();
		Spider spider2 = new SynchronousSpider("https://www.google.es", "prueba.txt");
		spider2.makeRequestAndGetResponse();
		
	}

}
