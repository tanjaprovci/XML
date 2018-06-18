 package com.project.web_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.DTO.AccommodationDTO;
import com.project.model.DTO.ApartmentDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value="/restIntercepter")
public class IntercepterWebService {

	public static String url = "http://localhost:9090/Agent-backend/AccommodationWebService";
	
	public static String URL_ENCODING = "UTF-8";
	
	public static String CONTENT_TYPE = "text/xml";
	
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	
	@OPTIONS
	public String message() {
		System.out.println("Invoking HTTP GET method");
		return "Hi REST!";
	}
	
	@POST
	 
	public String lowerCase(@RequestBody String soap) throws HttpException, IOException {
		
		String url = "http://localhost:8090/Agent-backend/AccommodationWebService";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("Content-Type", CONTENT_TYPE);

		post.setEntity(new StringEntity(soap, URL_ENCODING));

		HttpResponse response = client.execute(post);
		
		System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}
	
	@POST
	@RequestMapping(value="/addAccommodation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addAccommodation(@RequestBody AccommodationDTO accommodation/*String name, @RequestParam String type, @RequestParam String city, 
			@RequestParam String street, @RequestParam String description, @RequestParam String category*/ ) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
				+ "<soap:Body>\r\n" + 
				" <ns2:addAccommodation xmlns:ns2=\"http://com.project/web_service/wrappers\">\r\n" + 
				"<name>" + accommodation.getName() + "</name>\r\n" + 
				"<type>" + accommodation.getType() + "</type>\r\n" + 
				"<city>" + accommodation.getCity() + "</city>\r\n" + 
				"<street>" + accommodation.getStreet() + "</street>\r\n" + 
				"<description>" + accommodation.getDescription() + "</description>\r\n" + 
				"<category>" + accommodation.getCategory() + "</category>\r\n" +
				"<image>"  + accommodation.getImage() + "</image>" +
				"</ns2:addAccommodation>\r\n" + 
				"</soap:Body>\r\n" + 
				"</soap:Envelope>";
		// add header
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:addAccommodationResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
    
	@POST
	@RequestMapping(value="/addApartment/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addApartment(@PathVariable String accommodationId, @RequestBody ApartmentDTO apartment) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		System.out.println("rest " + apartment.getAdditionalService());
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
				+ "<soap:Body>\r\n" + 
				" <ns2:addApartment xmlns:ns2=\"http://com.project/web_service/wrappers\">\r\n" + 
				"<accommodationId>" + accommodationId + "</accommodationId>\r\n" +
				"<name>" + apartment.getName() + "</name>\r\n" + 
				"<bedType>" + apartment.getBedType() + "</bedType>\r\n" + 
				"<size>" + apartment.getSize() + "</size>\r\n" + 
				"<numOfRooms>" + apartment.getNumOfRooms() + "</numOfRooms>\r\n" + 
				"<numOfGuests>" + apartment.getNumOfGuests() + "</numOfGuests>\r\n" +
				"<description>" + apartment.getDescription() + "</description>\r\n" + 
				"<image>"  + apartment.getImage() + "</image>" +
				"<additionalService>"  + apartment.getAdditionalService() + "</additionalService>" +
				"<pricePlans>"  + apartment.getPricePlans() + "</pricePlans>" +
				"</ns2:addApartment>\r\n" + 
				"</soap:Body>\r\n" + 
				"</soap:Envelope>";
		// add header
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        System.out.println("x" + xmlJSONObj.toString());
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:addApartmentResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@GET
	@RequestMapping(value = "/getAccommodationTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAccommodationTypes() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getAccommodationTypes xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getAccommodationTypes>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getAccommodationTypesResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@GET
	@RequestMapping(value = "/getAccommodationCategories", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAccommodationCategories() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getAccommodationCategories xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getAccommodationCategories>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
        
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getAccommodationCategoriesResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@GET
	@RequestMapping(value = "/getCities", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getCities() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getCities xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getCities>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);

		JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getCitiesResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@GET
	@RequestMapping(value = "/getAllAccommodations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllAccommodations() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getAllAccommodations xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getAllAccommodations>\r\n</soap:Body>\r\n</soap:Envelope>";

		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getAllAccommodationsResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@GET
	@RequestMapping(value = "/getBedTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getBedTypes() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getBedTypes xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getBedTypes>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
        
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getBedTypesResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@GET
	@RequestMapping(value = "/getApartments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getApartments(@PathVariable String id) throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getApartments xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "<id>" + id + "</id>"
				+ "\r\n</ns2:getApartments>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getApartmentsResponse")).get("return"));
        else
        	retVal.put("return", "This accommodation has no apartments.");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	 
	@GET
	@RequestMapping(value = "/getAdditionalServices", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAdditionalServices() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getAdditionalServices xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getAdditionalServices>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getAdditionalServicesResponse")).get("return"));
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	@GET
	@RequestMapping(value = "/getPricePlans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getPricePlans() throws ClientProtocolException, IOException, JSONException, SOAPException, JAXBException, ParseException
	{
		
		String soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n<soap:Body>\r\n"
				+ "<ns2:getPricePlans xmlns:ns2=\"http://com.project/web_service/wrappers\">"
				+ "\r\n</ns2:getPricePlans>\r\n</soap:Body>\r\n</soap:Envelope>";
		
		JSONObject xmlJSONObj = httpClientExecute(soap);
		
        JSONObject retVal =  new JSONObject();
        
        if(xmlJSONObj.toString().contains("return"))
        	retVal.put("return", ((JSONObject) ((JSONObject) ((JSONObject) xmlJSONObj.get("S:Envelope")).get("S:Body")).get("ns2:getPricePlansResponse")).get("return"));
        else
        	retVal.put("return", "No price plan");
        
        return new ResponseEntity<String>(retVal.toString(), HttpStatus.OK);
	}
	
	public static JSONObject httpClientExecute(String soap) throws UnsupportedOperationException, IOException
	{
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		
		// add header
		post.setHeader("Content-Type", CONTENT_TYPE);

		post.setEntity(new StringEntity(soap, URL_ENCODING));

		HttpResponse response = client.execute(post);
		
		System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		
		while ((line = rd.readLine()) != null) {
			result.append(line);
//			System.out.println("res " + result);
 		}

//		String json = "";
		JSONObject xmlJSONObj = null;
		
        try {
            xmlJSONObj = XML.toJSONObject(result.toString());
//            json = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
        
        return xmlJSONObj;
	}
}
