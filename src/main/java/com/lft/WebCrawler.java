package com.lft;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;

public class WebCrawler {
	
	public void start() throws Exception {
		
		Timer timer = new Timer();
		timer.start();
		
		ConcurrentHashMap<String, Integer> wordMap = new ConcurrentHashMap<>();
		final String[] words = { "java", "oracle" };
		final String[] urls = getFileContent("/urls.txt").split("\n");
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		CompletionService<String> completionService = new ExecutorCompletionService<>(executor);
		
		for (String url : urls) {
			completionService.submit(()-> {
				return new HTMLParser(
						new URLUtility(url).getContent()
						).getText();
			});
		}
		
		int received = 0;
		boolean error = false;
		String content = "";
		while (received < urls.length && !error) {
			Future<String> future = completionService.take();
			try {
				content += " " + future.get();
				received++;
			} catch (Exception e) {
				error = true;
				System.out.println(e.getMessage());
			}
		}
	
		executor.shutdown();
		while (!executor.awaitTermination(1, TimeUnit.MINUTES));
		
		WordFrequencyMapper wordMapper = new WordFrequencyMapper(words, content);
		wordMap = wordMapper.getCountOfEachWord();
		
		for (final Map.Entry<String, Integer> entry : wordMap.entrySet()) {
			System.out.println(entry.getKey() + " count: " + entry.getValue());
		}
		
		timer.stop();
		System.out.println(
				"\nTotal time elapsed: " +
				TimeUnit.NANOSECONDS.toMillis(timer.getElapsedTime()) +
				"ms"
			);
	}
	
	public String getFileContent(String path) throws Exception {
		File file = new File(this.getClass().getResource(path).getPath());
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String inputLine;
		String stringBuffer = "";
		while((inputLine = bufferedReader.readLine()) != null) {
			stringBuffer += inputLine + "\n";
		}
		bufferedReader.close();
		return stringBuffer;
	}
}
