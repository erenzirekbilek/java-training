package com.training.client.xml;

import javax.xml.parsers.*;
import org.xml.sax.*;
import java.io.*;
import org.w3c.dom.*;

public class UserXmlService {
    
    public Document createDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    public Document parseXml(String xmlString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlString)));
    }

    public Element createUserElement(Document doc, Long id, String name) {
        Element user = doc.createElement("user");
        user.setAttribute("id", id.toString());
        
        Element username = doc.createElement("username");
        username.setTextContent(name);
        user.appendChild(username);
        
        return user;
    }

    public String getElementText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return null;
    }
}