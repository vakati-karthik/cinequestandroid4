package com.owentech.testswipeab;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.owentech.testswipeab.Forum;;

public class ForumsList {
	private boolean isForumSchedule = false;
	private static ArrayList<Forum> forumsList;
	private Forum forum;
	public ForumsList(){
		forumsList = new ArrayList<Forum>();
	}
	
	public ArrayList<Forum> getForumList(){
		return forumsList;
	}
	
	public ArrayList<String> getNameList(){
		ArrayList<String> result = new ArrayList<String>();
		for (Object object :forumsList){
			result.add(((Forum) object).getName());
		}
		return result;
	}
	
	public void readFromXml(InputSource in) throws IOException {
        class QuestionHandler extends BasicHandler {
            @Override
            public void startElement(String uri, String localName,
                String qName, Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);
                if (qName.equals("forums"))
                	isForumSchedule = true;
                if(qName.equals("schedule") && isForumSchedule == true){
                	forum = new Forum();
                	String id = attributes.getValue("id");
                	if(!id.equals(null))
                		forum.setSchedule_Id(id);
                	String mob_id = attributes.getValue("mobile_item_id");
                	if(!mob_id.equals(null))
                		forum.setSchedule_mob_Id(mob_id);
                	String title = attributes.getValue("title");
                	if(!title.equals(null))
                		forum.setName(title);
                	forumsList.add(forum);
                }
            }
            @Override
            public void endElement(String uri, String localName, String qName)
                    throws SAXException {
                if (qName.equals("forums"))
                	isForumSchedule = false;
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
