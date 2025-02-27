package com.example.userregistration.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationService {

	@Autowired
	private RestTemplate restTemplate;

	public String getIpAddress() {
		String url = "https://api.ipify.org?format=json";
		Map<String, String> response = restTemplate.getForObject(url, Map.class);
        return response != null ? response.get("ip") : "IP Not Found";	}
	
	public String getCountry(String ipAddress) {
        String url = "http://ip-api.com/json/" + ipAddress;
        
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        
        if (response != null) {
            return (String) response.get("country");
        } else {
            return "Country not found";
        } 
	}
}
