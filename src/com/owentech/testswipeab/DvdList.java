package com.owentech.testswipeab;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DvdList {
	private ArrayList<DvdListItem> dvdList;
	private DvdListItem tmp;
	private String tmpValue;
	private StringBuffer buffer = new StringBuffer();
	public DvdList(){
		dvdList = new ArrayList<DvdListItem>();
	}
	
	public ArrayList<DvdListItem> getDvdList(){
		return dvdList;
	}
	
	public ArrayList<String> getNameList(){
		ArrayList<String> result = new ArrayList<String>();
		for (Object object :dvdList){
			result.add(((DvdListItem) object).getName());
		}
		return result;
	}
	
	public void readFromXml(InputSource in) throws IOException {
        class QuestionHandler extends BasicHandler {
            @Override
            public void startElement(String uri, String localName,
                    String qName, Attributes attributes) throws SAXException {
	                super.startElement(uri, localName, qName, attributes);
	                if(qName.equals("film")){
	                	tmp = new DvdListItem();
	                	String id = attributes.getValue("id");
	                	if(!id.equals(null))
	                		tmp.setFilmId(id);
                }
            }
            @Override
            public void endElement(String uri, String localName, String qName)
                    throws SAXException {
                if (qName.equals("title")) {
                	tmp.setName(buffer.toString()); 
                }
                if (qName.equals("film"))
                	dvdList.add(tmp);
                buffer = new StringBuffer();
            }
            
            @Override
                public void characters(char[] ac, int i, int j) throws SAXException {
            	buffer.append(new String(ac, i, j));
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
