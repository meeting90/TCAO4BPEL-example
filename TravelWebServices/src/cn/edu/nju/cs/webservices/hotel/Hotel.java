package cn.edu.nju.cs.webservices.hotel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.edu.nju.cs.webservices.user.User;
import cn.edu.nju.cs.webservices.xsd.FlightInfo;
import cn.edu.nju.cs.webservices.xsd.HotelInfo;
import cn.edu.nju.cs.webservices.xsd.HotelList;

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
	


	


	public HotelList searchHotel(String city, Date checkinDate, Date checkoutDate) {
		
	HotelList result = new HotelList();
//		if(city.isEmpty()) {
//			System.out.println("departure city can't be empty!");
//			return result;
//		}
//		else if(checkoutDate.before(checkinDate)) {
//			System.out.println("checkout date must be after checkin date!");
//			return result;
//		}
//		else {
			ArrayList<HotelInfo> temp = new ArrayList<HotelInfo>();
			for(HotelInfo info : hotelInfo) {
				if(info.getCity().equals(city)) {
					temp.add(info);
				}
			}
			result.setChildren( temp.toArray(new HotelInfo[]{}));
			return result;
				
			
//		}
	}


	
	public HotelList sort(FlightInfo info, HotelList
			hotels) {
		List<HotelInfo> before= Arrays.asList(hotels.getChildren());
		
		Collections.shuffle(before);
		HotelInfo [] after= before.toArray(new HotelInfo[]{});
		HotelList result =new HotelList();
		result.setChildren(after);
		return result;
		
	}
	
	
	public HotelInfo selectHotel( HotelList  hotels) {
		if(hotels.getChildren().length>0)
			return hotels.getChildren()[0];
		return 
				new HotelInfo();
	}

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
			userInfo.updateBalance(username, balance);
			
			return true;
		
	}
}
