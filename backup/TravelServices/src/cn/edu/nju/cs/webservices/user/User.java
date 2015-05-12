package cn.edu.nju.cs.webservices.user;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@WebService(
	serviceName="userService", 
	portName="userPort"
)
@SOAPBinding(parameterStyle=ParameterStyle.BARE)

public class User {
	private static Map<String, String> authorizeMap = new HashMap<String, String>();
	private static Map<String, Double> balanceMap = new HashMap<String, Double>();
	
	static{
		try {
			 // parse the users document
			DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document userDocument = domBuilder.parse(User.class.getResource("authorize.xml").toExternalForm());
		      // iterate over the users
		    Element usersElem = userDocument.getDocumentElement();
		    NodeList userElems = usersElem.getElementsByTagName("account");
		    for (int i = 0, n = userElems.getLength(); i < n; i++) {
		    	Element accountElem = (Element) userElems.item(i);
		        // create users
		        String userName = accountElem.getAttribute("id");
		        String password = accountElem.getAttribute("password");
		        authorizeMap.put(userName, password);
		    }
			 // parse the users document
			domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			userDocument = domBuilder.parse(User.class.getResource("balance.xml").toExternalForm());
		      // iterate over the users
		    usersElem = userDocument.getDocumentElement();
		    userElems = usersElem.getElementsByTagName("account");
		    for (int i = 0, n = userElems.getLength(); i < n; i++) {
		        Element accountElem = (Element) userElems.item(i);
		        // create users
		        String userName = accountElem.getAttribute("id");
		        double balance = Double.parseDouble(accountElem.getAttribute("balance"));
		        balanceMap.put(userName, balance);
		    }
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	public String login(String username, String password) {
		
		if(username.isEmpty() || password.isEmpty()) {
			return "username or password can't be empty!";
		
		}
		else if(!authorizeMap.containsKey(username)) {
			return "username doesn't exist!";
			
		}
		else {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] thedigest =md.digest(password.getBytes("UTF-8"));
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j <thedigest.length; j++) {
				    sb.append(Integer.toHexString(0xff & thedigest[j]));
				}
				String md5 = sb.toString();				
				if(!authorizeMap.get(username).equals(md5)) {
					return "password error!";
					
				}
				else {
					return username;
				}
			} catch(Exception e) {
				return "service internal error: "+e.getMessage();
			}
		}
		
	}
	

	public synchronized double getBalance(@WebParam(name = "username")String username) {
		if(!balanceMap.containsKey(username)) {
			System.out.println("username doesn't exist!");
			return Double.NaN;
		}
		else
			return balanceMap.get(username);
	}
	

	public synchronized double updateBalance(@WebParam(name = "username")String username, 
			@WebParam(name = "change")double newBalance) {
		balanceMap.put(username, newBalance);
		return newBalance;
	}
}
