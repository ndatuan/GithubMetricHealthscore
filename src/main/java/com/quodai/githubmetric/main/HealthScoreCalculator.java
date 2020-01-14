package com.quodai.githubmetric.main;

import java.io.IOException;
import java.util.TreeMap;

import com.quodai.githubmetric.service.CsvResultPrintingService;
import com.quodai.githubmetric.service.FileUnzipService;
import com.quodai.githubmetric.service.GithubEventDownloadingService;
import com.quodai.githubmetric.service.GithubEventUrlBuildingService;
import com.quodai.githubmetric.service.HealthScoreCalculationService;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;
import com.quodai.githubmetric.util.DateUtils;

public class HealthScoreCalculator {
	
	public static void main(String[] args) throws IOException {
		String startDateInput = "2019-01-01T00:00:00Z";
		String endDateInput = "2019-01-02T00:00:00Z";
		String requestUrl = GithubEventUrlBuildingService.newInstance().buildUrl(DateUtils.convertIso8601Format(startDateInput), DateUtils.convertIso8601Format(endDateInput));
		System.out.println(requestUrl);
		requestUrl = "https://data.gharchive.org/2019-01-01-15.json.gz";
		String filePath = GithubEventDownloadingService.newInstance().downloadFileAndReturnFilePath(requestUrl);
		String jsonFilePath = FileUnzipService.newInstance().unzipToJsonFile(filePath);
		TreeMap<Integer, GitRepositoryOverview> results = HealthScoreCalculationService.newInstance().calculate(jsonFilePath);
		CsvResultPrintingService.newInstance().printResult(results);
	}
}
