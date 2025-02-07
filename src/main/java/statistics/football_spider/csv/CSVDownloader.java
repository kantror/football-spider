package statistics.football_spider.csv;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CSVDownloader {

	public void downloadAllCSVFiles(String url, String outputDirectory) throws IOException, ExecutionException, InterruptedException{
		
		List<String> csvLinks = extractCSVLinks(url);
		
		Path outputPath = Paths.get(outputDirectory);
        if (!Files.exists(outputPath)) {
            Files.createDirectories(outputPath);
        }
        
        List<CompletableFuture<Void>> downloadFutures = csvLinks.stream()
                .map(csvLink -> downloadCSVFileAsync(csvLink, outputPath))
                .collect(Collectors.toList());
        
        CompletableFuture.allOf(downloadFutures.toArray(new CompletableFuture[0])).get();
		
	}
	
	private List<String> extractCSVLinks(String url) throws IOException {
        List<String> csvLinks = new ArrayList<>();
        Document document = Jsoup.connect(url).get();
        Elements links = document.select("a[href]"); // Seleccionar todos los enlaces

        for (Element link : links) {
            String href = link.attr("href");
            if (href.endsWith(".csv")) { // Filtrar enlaces que terminan en .csv
                csvLinks.add(href);
            }
        }

        return csvLinks;
    }
	
	private CompletableFuture<Void> downloadCSVFileAsync(String csvUrl, Path outputDirectory) {
        return CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL(csvUrl);
                String fileName = Paths.get(url.getPath()).getFileName().toString();
                Path outputPath = outputDirectory.resolve(fileName);

                System.out.println("Descargando: " + csvUrl);
                Files.copy(url.openStream(), outputPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Descarga completada: " + outputPath);
            } catch (IOException e) {
                System.err.println("Error al descargar " + csvUrl + ": " + e.getMessage());
            }
        });
    }
	
}
