package com.project.web_service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.project.model.Accommodation;
import com.project.model.AccommodationCategory;
import com.project.model.AccommodationType;
import com.project.model.AdditionalService;
import com.project.model.Agent;
import com.project.model.Apartment;
import com.project.model.BedType;
import com.project.model.City;
import com.project.model.Image;
import com.project.model.PricePlan;
import com.project.model.DTO.AccommodationDTO;
import com.project.model.DTO.ApartmentDTO;
import com.project.repository.AccommodationCategoryRepository;
import com.project.repository.AccommodationRepository;
import com.project.repository.AccommodationTypeRepository;
import com.project.repository.AdditionalServiceRepository;
import com.project.repository.AgentRepository;
import com.project.repository.ApartmentRepository;
import com.project.repository.ApartmentServiceRepository;
import com.project.repository.BedTypeRepository;
import com.project.repository.CityRepository;
import com.project.repository.ImageRepository;
import com.project.repository.PricePlanRepository;
import com.project.service.AccommodationCategoryService;
import com.project.service.AccommodationService;
import com.project.service.AccommodationTypeService;
import com.project.service.AdditionalServiceService;
import com.project.service.AgentService;
import com.project.service.ApartmentService;
import com.project.service.ApartmentServiceService;
import com.project.service.BedTypeService;
import com.project.service.CityService;
import com.project.service.ImageService;
import com.project.service.PricePlanService;
import com.project.service.impl.JpaAccommodationCategoryService;
import com.project.service.impl.JpaAccommodationService;
import com.project.service.impl.JpaAccommodationTypeService;
import com.project.service.impl.JpaAdditionalServiceService;
import com.project.service.impl.JpaAgentService;
import com.project.service.impl.JpaApartmentService;
import com.project.service.impl.JpaApartmentServiceService;
import com.project.service.impl.JpaBedTypeService;
import com.project.service.impl.JpaCityService;
import com.project.service.impl.JpaImageService;
import com.project.service.impl.JpaPricePlanService;

@CrossOrigin(origins = "http://localhost:4200")
@WebService(serviceName = "AccommodationWebService",
			targetNamespace = "http://com.project/web_service/wrappers")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public class AccommodationWebService {
	
//	instantiating repositories and services
	@Autowired
	private AccommodationRepository accRepository;
	
	@Autowired
	private AccommodationTypeRepository accTypeRepository;
	
	@Autowired
	private AccommodationCategoryRepository accCatRepository;
	
	@Autowired 
	private CityRepository cityRepository;
	
	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ApartmentRepository apartmentRepository;
	
	@Autowired
	private ApartmentServiceRepository apartmentServiceRepository;
	
	@Autowired
	private BedTypeRepository bedTypeRepository;
	
	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	
	@Autowired
	private PricePlanRepository pricePlanRepository;
	
	AccommodationService accService = new JpaAccommodationService(accRepository);
	
	AccommodationTypeService accTypeService = new JpaAccommodationTypeService(accTypeRepository);
	
	AccommodationCategoryService accCategoryService = new JpaAccommodationCategoryService(accCatRepository);
	
	CityService cityService = new JpaCityService(cityRepository);
	
	AgentService agentService = new JpaAgentService(agentRepository);
	
	ImageService imageService = new JpaImageService(imageRepository);
	
	ApartmentService apartmentService = new JpaApartmentService(apartmentRepository);
	
	ApartmentServiceService apartmentServiceService = new JpaApartmentServiceService(apartmentServiceRepository);
	
	BedTypeService bedTypeService = new JpaBedTypeService(bedTypeRepository);
	
	AdditionalServiceService additionalServiceService = new JpaAdditionalServiceService(additionalServiceRepository);
	
	PricePlanService pricePlanService = new JpaPricePlanService(pricePlanRepository);
	
//	instantiating repositories and services
	
	
	@RequestWrapper(className="com.project.web_service.wrappers.SayHello")
	@ResponseWrapper(className="com.project.web_service.wrappers.SayHelloResponse")
	public String sayHello(@WebParam(name = "firstName") String firstName,@WebParam(name = "lastName") String lastName) {
		System.out.println("Invoked HelloDocWrappedImpl sayHello() method");
		return "Hello world " + firstName + " " + lastName;
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.requests.AddAccommodationRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.responses.AddAccommodationResponse")
	public String addAccommodation(@WebParam(name = "name") String name, @WebParam(name = "type") String type,
			@WebParam(name = "city") String city, @WebParam(name = "street") String street, @WebParam(name = "description") String description,
			@WebParam(name = "category") String category, @WebParam(name = "image") String image) throws IOException
	{
		
		AccommodationType accType = accTypeService.findOne(Long.valueOf(type));
		AccommodationCategory accCategory = accCategoryService.findOne(Long.valueOf(category));
		City accCity = cityService.findOne(Long.valueOf(city));
		Agent agent = agentService.findOne(Long.valueOf("1"));
		Accommodation newAccommodation = new Accommodation(name, accType, accCity, street, description, accCategory, agent);

		System.out.println(image);
		String retVal = "";
		String[] splits = image.split("ovo-je-separator");
		// tokenize the data
//		String[] parts = image.split(",");
//		String imageString = parts[1];
		
		for(int i = 0; i < splits.length; i++)
		{
			System.out.println("sss " + splits[i]);
			String[] parts = splits[i].split(",");
			String imageString = parts[1];

		// create a buffered image
		BufferedImage img = null;
		byte[] imageByte;

		imageByte = Base64.getDecoder().decode(imageString);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		img = ImageIO.read(bis);
		bis.close();
		
		List<Image> images= imageService.findAll();
		int imgNameCounter = -1;
		
		if(images.size() != 0)
			for(int  j = 0; j < images.size() - 1; j++)
				if(images.get(j).getId() < images.get(j+1).getId())
					imgNameCounter = images.get(j+1).getId().intValue() + 1;
		
 		// write the image to a file
		File outputfile = new File("G:\\tanja\\slike\\out" + imgNameCounter + ".png");
		ImageIO.write(img, "png", outputfile);
		
		Accommodation saved = accService.save(newAccommodation);
		
		if(saved != null)
		{	
			Image addImg = new Image(outputfile.toString(), saved, null);
			imageService.save(addImg);
			retVal = "Accommodation successfully added";

		}
		}
		return retVal;
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.requests.AddApartmentRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.responses.AddApartmentResponse")
	public String addApartment(@WebParam(name="accommodationId") String accommodationId, @WebParam(name = "name") String name, @WebParam(name = "bedType") String bedType,
			@WebParam(name = "size") String size, 
			@WebParam(name = "numOfRooms") String numOfRooms, 
			@WebParam(name = "numOfGuests") String numOfGuests,
			@WebParam(name = "description") String description,
			@WebParam(name = "image") String image, @WebParam(name = "additionalService") String additionalJSON,
			@WebParam(name = "pricePlans") String pricePlansJSON) throws IOException, ParseException
	{
		
		BedType bt = bedTypeService.findOne(Long.valueOf(bedType));
		Accommodation accommodation = accService.findOne(Long.valueOf(accommodationId));
		Apartment newApartment = new Apartment(name, bt, description, accommodation, Integer.parseInt(size), Integer.parseInt(numOfGuests), Integer.parseInt(numOfRooms));

		List<AdditionalService> tempArray = new ArrayList<>();
		Optional<AdditionalService> additionalService = null;
		
		String temp = additionalJSON.substring(1, additionalJSON.length() - 1);
		String[] array = temp.split(",");
		
		JSONParser parser = new JSONParser();
		JSONArray json = (JSONArray) parser.parse(pricePlansJSON);

		List<PricePlan> pricePlans = new ArrayList<>();
		
		for(int i = 0; i < json.size(); i++)
		{
			String str = json.get(i).toString();
			String s = str.substring(1, str.length() - 1);
			String[] strs = s.split(",");
			String endDate = strs[0].split(":")[1];
			String price = strs[1].split(":")[1];
			String startDate = strs[2].split(":")[1];
			
			pricePlans.add(new PricePlan(startDate.substring(1, startDate.length() - 1), endDate.substring(1, endDate.length() - 1), Integer.parseInt(price)));
		}

		for(int i = 0; i < array.length; i++)
		{
			additionalService = additionalServiceService.findOne(Long.valueOf(array[i]));
			tempArray.add(additionalService.get());
		}
		
		// tokenize the data
		String retVal = "";
		String[] splits = image.split("ovo-je-separator");
		File outputfile = null;
		
		for(int i = 0; i < splits.length; i++)
		{
			String[] parts = splits[i].split(",");
			String imageString = parts[1];
	
			// create a buffered image
			BufferedImage img = null;
			byte[] imageByte;
	
			imageByte = Base64.getDecoder().decode(imageString);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			img = ImageIO.read(bis);
			bis.close();
		
			List<Image> images= imageService.findAll();
			int imgNameCounter = -1;
			
			if(images.size() != 0)
				for(int  j = 0; j < images.size() - 1; j++)
					if(images.get(j).getId() < images.get(j+1).getId())
						imgNameCounter = images.get(j+1).getId().intValue() + 1;
			
	 		// write the image to a file
			outputfile = new File("G:\\tanja\\slike\\out" + imgNameCounter + ".png");
			ImageIO.write(img, "png", outputfile);
		}
		
		Apartment saved = apartmentService.save(newApartment);
		
		if(saved != null)
		{	
			Image addImg = new Image(outputfile.toString(), saved);
			imageService.save(addImg);
			
			for(AdditionalService ads : tempArray)
				apartmentServiceService.save(new com.project.model.ApartmentService(saved, ads));
			
			for(PricePlan pp : pricePlans)
				pricePlanService.save(new PricePlan(saved, pp.getStartDate(), pp.getEndDate(), pp.getPrice()));
			
			retVal = "Apartment successfully added";
		}
		
		return retVal;
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.requests.GetAccommodationTypesRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAccommodationTypesResponse")
	public List<AccommodationType> getAccommodationTypes(){
		
		List<AccommodationType> accommodationTypes = accTypeService.findAll();
		
		return accommodationTypes;
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetAccommodationCategoriesRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAccommodationCategoriesResponse")
	public List<AccommodationCategory> getAccommodationCategories(){
		
		List<AccommodationCategory> accommodationCategories = accCategoryService.findAll();
		
		return accommodationCategories;
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetCitiesRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetCitiesResponse")
	public List<City> getCities(){
		
		List<City> cities = cityService.findAll();
		List<City> retVal = new ArrayList<>();
		
		for (City c : cities)
			retVal.add(new City(c.getId(), c.getName(), c.getZipcode()));
		
		return retVal;
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.requests.GetAccommodationsRequest")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAccommodationsResponse")
	public List<AccommodationDTO> getAllAccommodations()
	{
		List<Accommodation> accommodations = accService.findAll();
		List<AccommodationDTO> retVal = new ArrayList<>();
		
		for(Accommodation acc : accommodations)
		{
			List<Image> images = imageService.findAll();
			
			AccommodationDTO accDTO = new AccommodationDTO(acc.getId().toString(), acc.getName(), acc.getType().getName(), acc.getCity().getName(), 
					acc.getCity().getCountry().getName(), acc.getStreet(), acc.getDescription(), acc.getCategory().getName(), "");
			
			String temp = "";
			for(Image img : images)
			{
				if(img.getAccommodation() != null)
				{
					if(img.getAccommodation().getId().equals(acc.getId()))
					{	
						temp += img.getUrl();
					  	accDTO.setImage(temp);
					  	temp += "; ";
					}
				}
			}
			
			retVal.add(accDTO);
		}
		
		for(AccommodationDTO a : retVal)
			System.out.println("pppp " + a.getImage() + " " + a.getName() + a.getId());
		
		return retVal;
	}
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetBedTypes")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetBedTypesResponse")
	public List<BedType> getBedTypes(){
		
		List<BedType> bedTypes = bedTypeService.findAll();
		
		return bedTypes;
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetApartments")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetApartmentsResponse")
	public List<ApartmentDTO> getApartments(@WebParam(name = "id") String id)
	{
//		Accommodation accommodation = accService.findOne(Long.valueOf(id));
//		List<Apartment> all = apartmentService.findByAccommodationId(accommodation);
		List<Apartment> all = apartmentService.findAll();
		List<Apartment> temp = new ArrayList<>();
		
		for(Apartment a : all)
		{
			if(a.getAccommodation().getId().equals(Long.valueOf(id)))
				temp.add(a);
		}
		
		List<ApartmentDTO> retVal = new ArrayList<>();
		for(Apartment ap : temp)
		{	
//			TODO
//			pronacii odgovarajuce add service i price plans!!!
			System.out.println(ap.getName());
//			retVal.add(new ApartmentDTO(String.valueOf(ap.getId()), ap.getName(), ap.getType().getName(), String.valueOf(ap.getSize()), String.valueOf(ap.getNumberOfRooms()), 
//					String.valueOf(ap.getMaxNumberOfGuests()), ap.getDescription(), ""));
		}
		
		return retVal;
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetAdditionalServices")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetAdditionalServicesResponse")
	public List<AdditionalService> getAdditionalServices(){
		
		List<AdditionalService> additionalServices = additionalServiceService.findAll();
		
		return additionalServices;
	} 
	
	@RequestWrapper(className="com.project.web_service.wrappers.GetPricePlans")
	@ResponseWrapper(className="com.project.web_service.wrappers.GetPricePlansResponse")
	public List<?> getPricePlans(){
		
		List<PricePlan> pricePlans = pricePlanService.findAll();
		return pricePlans;
	} 
}
