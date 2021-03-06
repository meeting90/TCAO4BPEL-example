package cn.edu.nju.cs.webservices.flight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.jws.WebService;
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
import cn.edu.nju.cs.webservices.xsd.ObjectFactory;

@WebService()
public class Flight {
	private static ArrayList<FlightInfo> flightInfo = new ArrayList<FlightInfo>();
	private static final String namespace="http://flight.webservices.cs.nju.edu.cn";
	static{
		try {
			 // parse the users document
			 DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			 Document flightDocument = domBuilder.parse(Flight.class.getResource("flight.xml").toExternalForm());
		      // iterate over the users
		      Element flightsElem = flightDocument.getDocumentElement();
		      NodeList flightElems = flightsElem.getElementsByTagName("flight");
		      for (int i = 0, n = flightElems.getLength(); i < n; i++) {
		        Element flightElem = (Element) flightElems.item(i);
		        // create flight info
		        String airline = flightElem.getAttribute("airline");
		        String flightId = flightElem.getAttribute("flightId");
		        String departure = flightElem.getAttribute("departure");
		        String arrival = flightElem.getAttribute("arrival");
		        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		        Date departureTime = sdf.parse(flightElem.getAttribute("departureTime"));
		        Date arrivalTime = sdf.parse(flightElem.getAttribute("arrivalTime"));
		      
		        
		        String departureAirport = flightElem.getAttribute("departureAirport");
		        String arrivalAirport = flightElem.getAttribute("arrivalAirport");
		        int punctuality = Integer.parseInt(flightElem.getAttribute("punctuality"));
		        int delayTime = Integer.parseInt(flightElem.getAttribute("delayTime"));
		        double price = Double.parseDouble(flightElem.getAttribute("price"));
		        
		     
		        FlightInfo info = new FlightInfo();
		        info.setAirline(airline);
		        info.setFlightId(flightId);
		        info.setDeparture(departure);
		        info.setArrival(arrival);
		        info.setDepartureTime(departureTime);
		        info.setArrivalTime(arrivalTime);
		        info.setDepartureAirport(departureAirport);
		        info.setArrivalAirport(arrivalAirport);
		        info.setPunctuality(punctuality);
		        info.setDeloyTime(delayTime);
		        info.setPrice(price);
		        
		        flightInfo.add(info);
		      }
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
   
	
	protected FlightInfo autoSelectFlight(String departure, 
	String arrival,Date date){
	   
	   FlightInfo[] list= searchFlight(departure, arrival, date);
	   return selectFlight(list);

   }
   
	private static OMFactory factory = OMAbstractFactory.getOMFactory();
	
	private static  OMNamespace omNs =factory.createOMNamespace(namespace, "flight");
	
	private static QName FLIGHT_departure=new QName(namespace, "departure");
	private static QName FLIGHT_arrival=new QName(namespace,"arrival");
	private static QName FLIGHT_date=new QName(namespace, "date");
	
	private static QName FLIGHT_flight = new QName(namespace, "flight");
	private static QName FLIGHT_username=new QName(namespace, "username");
	private static QName FLIGHT_phoneNumber= new QName(namespace,"phoneNumber");
	
	

	/*private OMElement searchFlight(OMElement request){
		request.build();
		request.detach();
		System.out.println(request.toString());
		String departure= request.getFirstChildWithName(FLIGHT_departure).getText();
		String arrival = request.getFirstChildWithName(FLIGHT_arrival).getText();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  	
		Date date=null;
		try {
			date = format.parse(request.getFirstChildWithName(FLIGHT_date).toString());
		} catch (Exception e) {
			date=new Date();
		}
		FlightInfo [] result= searchFlight(departure, arrival, date);
		System.out.println(result);
		
		ObjectFactory xsdFactory= new ObjectFactory();
		OMElement method = factory.createOMElement("searchFlightResponse", omNs);
		
		
		OMElement response= factory.createOMElement("return", omNs);
		OMElement flights = xsdFactory.createFlightList(result, "flights");
		
		response.addChild(flights);
		method.addChild(response);
		
		return method;
		
	}*/



	public FlightInfo[] searchFlight( String departure, 
	String arrival, Date date) {
		FlightInfo [] empty={};
		if(departure.isEmpty() || arrival.isEmpty()) {
			System.out.println("departure city or arrival city can't be empty!");
			return empty;
		}
		else if(departure.equals(arrival)) {
			System.out.println("departure city can't be same as arrival city!");
			return empty;
		}
		else {
			ArrayList<FlightInfo> result = new ArrayList<FlightInfo>();
			for(FlightInfo info : flightInfo) {
				if(info.getDeparture().equals(departure) && info.getArrival().equals(arrival)) {
					result.add(info);
				}
			}
			if(result.isEmpty()) {
				System.out.println("no airline found!");
				return empty;
			}
			else {
				FlightInfo [] children=result.toArray(new FlightInfo[]{});
				System.out.println(children);
				return children;
			}
		}
	}
	

	/*private OMElement log(OMElement request){
		request.build();
		request.detach();
		OMElement  flightList = request.getFirstElement();
		ObjectFactory xsdFactory= new ObjectFactory();
		FlightInfo [] flights=xsdFactory.getFligtList(flightList);
		boolean result = log(flights);
		
		OMElement method = factory.createOMElement("logResponse", omNs);
		OMElement response= factory.createOMElement("return", omNs);
		response.addChild(factory.createOMText(response, result+""));
		
		method.addChild(response);
		
		return method;
		
	}*/
	
	public boolean log(FlightInfo[] flights) {
			
			
			try {
				System.out.println("Start writing log file");
				BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt"));
				bw.write("search result log:");
				for(FlightInfo info : flights) {
					bw.newLine();
					bw.write("airline : " + info.getAirline());
					bw.newLine();
					bw.write("flightId : " + info.getFlightId());
					bw.newLine();
					bw.write("departure : " + info.getDeparture());
					bw.newLine();
					bw.write("arrival : " + info.getArrival());
					bw.newLine();
					bw.write("departureTime : " + info.getDepartureTime());
					bw.newLine();
					bw.write("arrivalTime : " + info.getArrivalTime());
					bw.newLine();
					bw.write("departureAirport : " + info.getDepartureAirport());
					bw.newLine();
					bw.write("arrivalAirport : " + info.getArrivalAirport());
					bw.newLine();
					bw.write("punctuality : " + info.getPunctuality());
					bw.newLine();
					bw.write("delayTime : " + info.getDeloyTime());
					bw.newLine();
					bw.write("price : " + info.getPrice());
				}
				bw.close();
				System.out.println("Finish writing log file");
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	
		
	}
	/*public OMElement selectFlight(OMElement request){
		
		request.build();
		request.detach();
		OMElement filghtlist=request.getFirstElement();
		ObjectFactory xsdFactory = new ObjectFactory();
		FlightInfo [] flights=xsdFactory.getFligtList(filghtlist);
		FlightInfo result = selectFlight(flights);
		
		OMElement method = factory.createOMElement("selectFlightResponse", omNs);
		OMElement response= xsdFactory.createFlightInfo(result, "return");
		method.addChild(response);
		System.out.println(method);
		return method;
		
		
		
	}*/
	
	public FlightInfo selectFlight(FlightInfo [] flights) {
		if(flights.length >0)
			return flights[0];
		return new FlightInfo();
	}
	
	/*
	private OMElement bookFlight(OMElement request){
		request.build();
		request.detach();
		OMElement flightEle= request.getFirstChildWithName(FLIGHT_flight);
		String username = request.getFirstChildWithName(FLIGHT_username).getText();
		String phoneNumber= request.getFirstChildWithName(FLIGHT_phoneNumber).getText();
		ObjectFactory xsdFactory = new ObjectFactory();
		FlightInfo flight = xsdFactory.getFlightInfo(flightEle);
		boolean result = bookFlight(flight, username, phoneNumber);
		
		OMElement method = factory.createOMElement("bookFlightResponse", omNs);
		OMElement response= factory.createOMElement("return", omNs);
		response.addChild(factory.createOMText(response, result+""));
		method.addChild(response);
		return method;
		
	}*/

	public synchronized boolean bookFlight(FlightInfo flight, 
			String username,String phoneNumber) {
	

		User userInfo = new User();
		double balance = userInfo.getBalance(username);
		double price = flight.getPrice();
		if(Double.isNaN(balance))
			return false;
		if(balance < price) {
			System.out.println("balance is less than need!");
			return false;
		}
		userInfo.updateBalance(username, balance - flight.getPrice());
		return true;
		
		
		
	}
}
