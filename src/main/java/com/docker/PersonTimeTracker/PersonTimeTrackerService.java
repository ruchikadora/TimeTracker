package com.docker.PersonTimeTracker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PersonTimeTrackerService {
	
	public List<String> fetchPersonDetails(String aPersonEmail, String aNumRecordFetch)
	{
		List<String> myRetDetails = new ArrayList<String>();
		List<String> myFetchedRecords = new ArrayList<String>();
		
		String myDefaultRes = "No Record Founds!";

		if(!StringUtils.isNumeric(aNumRecordFetch) || StringUtils.isEmpty(aNumRecordFetch))
			aNumRecordFetch = "10";
		if(aNumRecordFetch.equalsIgnoreCase("0"))
			myDefaultRes = "Input for number of records to fetch is 0.";
		else
		{
			try
			{
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				HttpEntity <String> entity = new HttpEntity<String>(headers);
				String myUrl = "http://timetrackerdata:8080/records";
				//String myUrl = "http://0.0.0.0:8080/records";
				
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(myUrl)
				        .queryParam("email", aPersonEmail)
				        .queryParam("length", aNumRecordFetch);
				ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(),
				        HttpMethod.GET, 
				        entity, 
				        String.class);
				
				if(response.getStatusCode() == HttpStatus.OK)
				{
					Pattern myPattern = Pattern.compile(", ");
					myRetDetails = myPattern.splitAsStream(response.getBody()).collect(Collectors.toList());
					for(String myResDetails: myRetDetails)
					{
						if(!(StringUtils.isEmpty(myResDetails)||myResDetails.contains("[ null")||myResDetails.contains("null ]")
								|| myResDetails.equalsIgnoreCase("null")))
						{
							myFetchedRecords.add(myResDetails);
						}
					}
				} else
				{
					myDefaultRes = "Could not retieve data";
					if(response.getStatusCode() == HttpStatus.NOT_FOUND)
						myDefaultRes = "Could not retieve data: 404 NOT FOUND";
					else if(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
						myDefaultRes = "Could not retieve data: 500 Internal Server Error";
				}
			}catch(Exception e) 
			{
				myDefaultRes = "Error occured while fetching data";
			}
		}
		
		if(myFetchedRecords == null || (myFetchedRecords!= null && myFetchedRecords.isEmpty())|| myFetchedRecords.size() == 0)
		{
			myFetchedRecords.add(myDefaultRes);
		}
       return myFetchedRecords;
	}

	public String insertPersonDetails(String aPersonEmail, String aStartTs, String aEndTs) {
		String myResponse = "Data Insert Failed. Try again";
		RestTemplate restTemplate = new RestTemplate();
		
		//String myUrl = "http://0.0.0.0:8080/records";
		String myUrl = "http://timetrackerdata:8080/records";
		ResponseEntity<String> response = null;
	
		try {
			SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			SimpleDateFormat outputFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);

			Date startTs = inputFormatter.parse(aStartTs);
			String myStartTs = outputFormatter.format(startTs);
			
			Date endTs = inputFormatter.parse(aEndTs);
			String myEndTs = outputFormatter.format(endTs);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
		    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("email", aPersonEmail);
			map.add("start", myStartTs); 
			map.add("end", myEndTs); 
			
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			
			response = restTemplate.postForEntity(myUrl, request , String.class );
			
			if(response.getStatusCode() == HttpStatus.OK)
			{
				myResponse = response.getBody();
			}else
			{
				if(response.getStatusCode() == HttpStatus.NOT_FOUND)
					myResponse = "Data Insert Failed: 404 NOT FOUND";
				else if(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
					myResponse = "Data Insert Failed: 500 Internal Server Error";
			}
		} catch(Exception ex) {
			myResponse = "Error occured on insert";
		}
		return myResponse;
	}

}
