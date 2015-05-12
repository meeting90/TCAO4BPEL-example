package cn.edu.nju.cs.webservices.hotel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import cn.edu.nju.cs.webservices.user.User;
import cn.edu.nju.cs.webservices.xsd.FlightInfo;
import cn.edu.nju.cs.webservices.xsd.HotelInfo;
import cn.edu.nju.cs.webservices.xsd.ObjectFactory;

public class Hotel {
	private static ArrayList<HotelInfo> hotelInfo = new ArrayList<HotelInfo>();
	
	static{
		try {
			 // parse the users document
			 DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			 Document hotelDocument = domBuilder.parse(Hotel.class.getResource("hotel.xml").toExternalForm());
		      // iterate over the users
		      Element hotelsElem = hotelDocument.getDocumentElement();
		      NodeList hotelElems = hotelsElem.getElementsByTagName("hotel");
		      for (int i = 0, n = hotelElems.getLength(); i < n; i++) {
		        Element hotelElem = (Element) hotelElems.item(i);
		        // create flight info
		        String city = hotelElem.getAttribute("city");
		        String position = hotelElem.getAttribute("position");
		        double price = Double.parseDouble(hotelElem.getAttribute("price"));
		        HotelInfo info = new HotelInfo();
		       
		        info.setCity(city);
		        info.setPosition(position);
		        info.setPrice(price);
		        hotelInfo.add(info);
		      }
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected HotelInfo autoSelectHotel(String city, 
	Date checkinDate, Date checkoutDate){
		HotelInfo [] list= searchHotel(city, checkinDate, checkoutDate);
		return selectHotel(list);
	}
	
	
	private static final String namespace="http://hotel.webservices.cs.nju.edu.cn";
	
	private static final QName HOTEL_city= new QName(namespace, "city");
	private static final QName HOTEL_checkinDate= new QName(namespace, "checkinDate");
	private static final QName HOTEL_checkoutDate= new QName(namespace, "checkoutDate");
	
	
	
	
	private static final QName SORT_flight= new QName(namespace, "flight");
	private static final QName SORT_hotels= new QName(namespace, "hotels");
	
	
	private static final QName HOTEL_hotel= new QName(namespace, "hotel");
	private static final QName HOTEL_username= new QName(namespace, "username");
	private static final QName HOTEL_phoneNumber= new QName(namespace, "phoneNumber");
	
	
	
	private static OMFactory factory = OMAbstractFactory.getOMFactory();
	
	private static  OMNamespace omNs =factory.createOMNamespace(namespace, "hotel");
	
	/*private OMElement searchHotel(OMElement request){
		request.build();
		request.detach();
		String city = request.getFirstChildWithName(HOTEL_city).getText();
		String checkin= request.getFirstChildWithName(HOTEL_checkinDate).getText();
		String checkout= request.getFirstChildWithName(HOTEL_checkoutDate).getText();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-mm-hh");
		Date checkinDate = new Date();
		Date checkoutDate= new Date();
		try {
			checkinDate=sdf.parse(checkin);
			checkoutDate=sdf.parse(checkout);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HotelInfo[] result =searchHotel(city,checkinDate, checkoutDate);
		
		ObjectFactory xsdFactory= new ObjectFactory();
		OMElement method = factory.createOMElement("searchHotelResponse", omNs);
		OMElement response= xsdFactory.createHotelList(result, "return");
	
		method.addChild(response);
		return method;
		
	}*/

	public HotelInfo[] searchHotel(String city, Date checkinDate, Date checkoutDate) {
		
	HotelInfo [] result={};
		if(city.isEmpty()) {
			System.out.println("departure city can't be empty!");
			return result;
		}
		else if(checkoutDate.before(checkinDate)) {
			System.out.println("checkout date must be after checkin date!");
			return result;
		}
		else {
			ArrayList<HotelInfo> temp = new ArrayList<HotelInfo>();
			for(HotelInfo info : hotelInfo) {
				if(info.getCity().equals(city)) {
					temp.add(info);
				}
			}
			return  temp.toArray(new HotelInfo[]{});
				
			
		}
	}


	
	/*private OMElement sort(OMElement request){
		OMElement flightEle=request.getFirstChildWithName(SORT_flight);
		OMElement hotelsEle= request.getFirstChildWithName(SORT_hotels);
		ObjectFactory xsdFactory= new ObjectFactory();
		FlightInfo flight =xsdFactory.getFlightInfo(flightEle);
		HotelInfo [] hotels= xsdFactory.getHotelList(hotelsEle);
		HotelInfo [] result = sort(flight,hotels);
		
		
		OMElement method = factory.createOMElement("sortResponse", omNs);
		OMElement response= xsdFactory.createHotelList(result, "return");
		method.addChild(response);
		
		return method;
		
		
	}*/
	
	public HotelInfo [] sort(FlightInfo info, HotelInfo[]
			hotels) {
		List<HotelInfo> before= Arrays.asList(hotels);
		
		Collections.shuffle(before);
		return before.toArray(new HotelInfo[]{});
		
	}
	
	
	/*private OMElement selectHotel(OMElement request){
		OMElement hotelsEle = request.getFirstElement();
		
		ObjectFactory xsdFactory= new ObjectFactory();
	    
		HotelInfo [] hotels = xsdFactory.getHotelList(hotelsEle);
		
		HotelInfo result = selectHotel(hotels);
		
		OMElement method = factory.createOMElement("selectHotelResponse", omNs);
		OMElement response= xsdFactory.createHotelInfo(result, "return");
		method.addChild(response);
		
		return method;
		
	}*/
	
	public HotelInfo selectHotel( HotelInfo [] hotels) {
		if(hotels.length>0)
			return hotels[0];
		return 
				new HotelInfo();
	}
	
	
	
	/*private OMElement reserveHotel(OMElement request){
		
		OMElement hotelEle = request.getFirstChildWithName(HOTEL_hotel);
		String username = request.getFirstChildWithName(HOTEL_username).getText();
		String phoneNumber = request.getFirstChildWithName(HOTEL_phoneNumber).getText();
		
		ObjectFactory xsdFactory= new ObjectFactory();
	    
		HotelInfo hotel = xsdFactory.getHotelInfo(hotelEle);
		boolean result = reserveHotel(hotel, username, phoneNumber);
		
		
		OMElement method = factory.createOMElement("reserveHotelResponse", omNs);
		OMElement response= factory.createOMElement("return", omNs);
		response.addChild(factory.createOMText(response, result+""));
		
		method.addChild(response);
		
		return method;
		
		
		
	}*/

	public synchronized boolean reserveHotel( HotelInfo hotel, 
			String username,  String phoneNumber) {

		
		
			User userInfo = new User();
			double balance = userInfo.getBalance(username);
			
			double price = hotel.getPrice();
			if(Double.isNaN(balance))
				return false;
			if(balance < price) {
				System.out.println("balance is less than need!");
				return false;
			}
			userInfo.updateBalance(username, balance - hotel.getPrice());
			
			return true;
		
	}
}
