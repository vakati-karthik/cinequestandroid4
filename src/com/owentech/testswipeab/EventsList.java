package com.owentech.testswipeab;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class EventsList {
	private boolean isEventSchedule = false;
	private static ArrayList<Event> eventsList;
	private Event event;
	public EventsList(){
		eventsList = new ArrayList<Event>();
	}
	
	public ArrayList<Event> getEventList(){
		return eventsList;
	}
	
	public ArrayList<String> getNameList(){
		ArrayList<String> result = new ArrayList<String>();
		for (Object object :eventsList){
			result.add(((Event) object).getName());
		}
		return result;
	}
	
	public void readFromXml(InputSource in) throws IOException {
        class QuestionHandler extends BasicHandler {
            @Override
            public void startElement(String uri, String localName,
                String qName, Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);
                if (qName.equals("special_events"))
                	isEventSchedule = true;
                if(qName.equals("schedule") && isEventSchedule == true){
                	event = new Event();
                	String id = attributes.getValue("id");
                	if(!id.equals(null))
                		event.setSchedule_Id(id);
                	String mob_id = attributes.getValue("mobile_item_id");
                	if(!mob_id.equals(null))
                		event.setSchedule_mob_Id(mob_id);
                	String title = attributes.getValue("title");
                	if(!title.equals(null))
                		event.setName(title);
                	eventsList.add(event);
                }
            }
            @Override
            public void endElement(String uri, String localName, String qName)
                    throws SAXException {
                if (qName.equals("events"))
                	isEventSchedule = false;
            }
        }
        
        try {       
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser(); 
            parser.parse(in, new QuestionHandler());
        }catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }catch (Exception ex) {
            IOException ioEx = new IOException(ex.getMessage());
            ioEx.initCause(ex);
            throw ioEx;
        }       
    }
}
