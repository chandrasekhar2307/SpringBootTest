package com.example.demo.controller;

import java.util.HashSet;
import java.util.TreeMap;

import javax.ws.rs.GET;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/ingTest")
public class APIsCotroller {
	
	@RequestMapping("/getfactsCats")
	@GET
	public ResponseEntity getFactsCats() {
		RestTemplate factsCats = new RestTemplate();
		
		JSONObject factscatsObj = factsCats.getForObject("https://cat-fact.herokuapp.com/facts", JSONObject.class);
		
		JSONArray factscatsArray = JSONObject.get("all");
		HashSet<String> finalNames = new HashSet<String>();
		for(JSONObject factcatobj : factscatsArray) {
			String fullname = factcatobj.get("first")+factcatobj.get("last");
			finalNames.add(fullname);
		}
		
		JSONObject jsonCount = factsCats.getForObject("https://agify.io/", JSONObject.class);
		JSONArray countriesList = jsonCount.get("countryList");
		
		JSONObject jsonCovidcount = factsCats.getForObject("https://covid19api.com/", JSONObject.class);
		JSONArray covidDataList = jsonCovidcount.get("covidData");
		TreeMap<String,String> coviddata = new TreeMap<String,String>();
		
		for(JSONObject covidData : covidDataList) {
			String countryName = covidData.get("country");
			String covidCases = covidData.get("cases");
			coviddata.put(countryName,covidCases);
		}
		
		return new ResponseEntity(coviddata,HttpStatus.OK);
	}	
}
