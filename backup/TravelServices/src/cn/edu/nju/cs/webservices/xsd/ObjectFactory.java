//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.07 at 10:58:33 PM CST 
//


package cn.edu.nju.cs.webservices.xsd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jws.Oneway;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMException;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the web package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {
	
	
	private final static String namespace="http://webservices.cs.nju.edu.cn/xsd";
	
	
	private OMNamespace omNs;
	private OMFactory factory;
	
	
	private final static QName HOTEL_city=new QName(namespace,"city");
	private final static QName HOTEL_position=new QName(namespace,"position");
	private final static QName HOTEL_price=new QName(namespace,"price");
	
	
	
	private final static QName FLIGHT_airline=new QName(namespace,"airline");
	
	private final static QName FLIGHT_arrival=new QName(namespace,"arrival");
	private final static QName FLIGHT_arrivalAirport=new QName(namespace,"arrivalAirport");
	private final static QName FLIGHT_arrivalTime=new QName(namespace,"arrivalTime");
	private final static QName FLIGHT_deployTime=new QName(namespace,"deployTime");
	private final static QName FLIGHT_departure=new QName(namespace,"departure");
	private final static QName FLIGHT_departureAirport=new QName(namespace,"departureAirport");
	private final static QName FLIGHT_departureTime=new QName(namespace,"departureTime");
	private final static QName FLIGHT_flightId=new QName(namespace,"flightId");
	private final static QName FLIGHT_price=new QName(namespace,"price");
	private final static QName FLIGHT_punctuality=new QName(namespace,"punctuality");
	
  
    public ObjectFactory() {
    	factory= OMAbstractFactory.getOMFactory();
    	omNs= factory.createOMNamespace(namespace, "common");
    }

    

    public OMElement createHotelInfo(HotelInfo info, String  name) {
        
    	OMElement element= factory.createOMElement(name, omNs);
    	element.addChild(createBasicNode("city", info.getCity()));
    	element.addChild(createBasicNode("position", info.getPosition()));
    	element.addChild(createBasicNode("price", info.getPrice().toString()));
        
        return element;
        
        
    }
    
    public OMElement createHotelList(HotelInfo[] list, String name){
    	OMElement  container = factory.createOMElement(name, omNs);
    	for(HotelInfo hotel: list){
    		OMElement element= createHotelInfo(hotel, "children");
    		container.addChild(element);
    	}
    	return container;
    	
    	
    
    }
    
    
    public OMElement createFlightInfo(FlightInfo info, String name){
    	OMElement container= factory.createOMElement(name,omNs);
    	container.addChild(createBasicNode("airline", info.getAirline()));
    	container.addChild(createBasicNode("arrival",info.getArrival()));
    	container.addChild(createBasicNode("arrivalAirport", info.getArrivalAirport()));
    	container.addChild(createBasicNode("departure", info.getDeparture()));
    	container.addChild(createBasicNode("departureAirport", info.getDepartureAirport()));
    	container.addChild(createBasicNode("flightId", info.getFlightId()));
    	container.addChild(createBasicNode("arrivalTime", info.getArrivalTime().toString()));
    	container.addChild(createBasicNode("deloyTime", info.getDeloyTime().toString()));
    	container.addChild(createBasicNode("departureTime", info.getDepartureTime().toString()));
    	container.addChild(createBasicNode("price", info.getPrice().toString()));
    	container.addChild(createBasicNode("punctuality", info.getPunctuality().toString()));
    	return container;
    }
    
    public OMElement createFlightList(FlightInfo[] list, String name){
    	OMElement container = factory.createOMElement(name, omNs);
    	for(FlightInfo flight: list){
    		
    		OMElement element =createFlightInfo(flight, "children");
    		container.addChild(element);
    	}
    	
    	return container;
    	
    	
    }
    
    
    
    public FlightInfo[] getFligtList(OMElement element){
    	List<FlightInfo> flights =new ArrayList<FlightInfo> ();
    	@SuppressWarnings("unchecked")
		Iterator<OMElement> children= element.getChildElements();
    	while(children.hasNext()){
    		OMElement next= children.next();
    		flights.add(getFlightInfo(next));
    		
    	}
    	return flights.toArray(new FlightInfo[]{});
    	
    }
    
    public FlightInfo getFlightInfo(OMElement element) {
		FlightInfo result = new FlightInfo();
		result.setAirline(element.getFirstChildWithName(FLIGHT_airline).getText());
		result.setArrival(element.getFirstChildWithName(FLIGHT_arrival).getText());
		result.setArrivalAirport(element.getFirstChildWithName(FLIGHT_arrivalAirport).getText());
		result.setDeloyTime(Integer.getInteger(element.getFirstChildWithName(FLIGHT_deployTime).getText()));
		result.setDeparture(element.getFirstChildWithName(FLIGHT_departure).getText());
		result.setDepartureAirport(element.getFirstChildWithName(FLIGHT_departureAirport).getText());
		result.setFlightId(element.getFirstChildWithName(FLIGHT_flightId).getText());
		result.setPrice(Double.valueOf(element.getFirstChildWithName(FLIGHT_price).getText()));
		result.setPunctuality(Integer.getInteger(element.getFirstChildWithName(FLIGHT_punctuality).getText()));
		SimpleDateFormat sdf= new SimpleDateFormat("hh:mm");
		try {
			Date arrivalTime= sdf.parse(element.getFirstChildWithName(FLIGHT_arrivalTime).getText());
			result.setArrivalTime(arrivalTime);
			Date departureTime= sdf.parse(element.getFirstChildWithName(FLIGHT_departureTime).getText());
			result.setDepartureTime(departureTime);
		} catch (OMException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
		
	}


    
    public HotelInfo[] getHotelList(OMElement element){
    	List<HotelInfo> hotelInfos= new ArrayList<HotelInfo>();
    	@SuppressWarnings("unchecked")
		Iterator<OMElement> children= element.getChildElements();
    	while(children.hasNext()){
    		OMElement next = children.next();
    		hotelInfos.add(getHotelInfo(next));
    	}
    	return hotelInfos.toArray(new HotelInfo[]{});
    }

	public HotelInfo getHotelInfo(OMElement element) {
		HotelInfo hotelInfo= new HotelInfo();
		hotelInfo.setCity(element.getFirstChildWithName(HOTEL_city).getText());
		hotelInfo.setPosition(element.getFirstChildWithName(HOTEL_position).getText());
		hotelInfo.setPrice(Double.valueOf(element.getFirstChildWithName(HOTEL_price).getText()));
		return hotelInfo;
	}



	private OMElement createBasicNode(String key,String value){
    	 OMElement element= factory.createOMElement(key,omNs);
    	 element.addChild(factory.createOMText(element,value));
        
         return element;
         
    }

}
