package cn.edu.nju.cs.webservices.xsd;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cn.edu.nju.cs.webservices.flight.Flight;

public class Test {
	
	public static void main(String []args) throws ParserConfigurationException, SAXException, IOException{
		File file = new File("/Users/meeting/Learning/github/TCAO4BPEL-example/TravelProcessCase2/bpelContent/Travel.bpel");
		 DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		 Document flightDocument = domBuilder.parse(file);
	}

}
