package edu.buu.childhood.util;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ImageXMLUtil {
	
	private static Logger logger = Logger.getLogger(ImageXMLUtil.class);
	
	public static final String JPEG = "image/jpeg";
	public static final String PNG = "image/png";
	
	private static Document document = DocumentHelper.createDocument();
	private static final Element image = document.addElement("image");
	private static final Element type = image.addElement("type");
	private static final Element binval = image.addElement("binval");
	
	public static void flush() {
		type.clearContent();
		binval.clearContent();
	}

	public static void setType(String imageType) {
		type.setText(imageType);
	}

	public static void setBinval(String imageBinval) {
		binval.setText(imageBinval);
	}
	
	private static Document setXML(String xml){
		try {
			return DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			logger.error(e.getStackTrace());
			return null;
		}
	}

	public static String getXML() {
		return document.getRootElement().asXML();
	}
	
	public static String getBase64Image(String xml){
		flush();
		Document parseFromStr = setXML(xml);
		if(parseFromStr != null){
			Element root = parseFromStr.getRootElement();
			for (Iterator<?> iter = root.elementIterator(); iter.hasNext();) {
				Element element=(Element) iter.next();
				if(element.getName().equals("binval")){
					return element.getText();
				}
			}
		}
		return "";
	}
}
