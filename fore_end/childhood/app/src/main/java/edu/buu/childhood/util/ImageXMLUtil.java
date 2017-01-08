package edu.buu.childhood.util;

import android.graphics.Bitmap;
import android.util.Log;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;

public class ImageXMLUtil {
	
	public static final String JPEG = "image/jpeg";
	public static final String PNG = "image/png";
	
	private static Document document = DocumentHelper.createDocument();
	private static final Element image = document.addElement("image");
	private static final Element binval = image.addElement("binval");
	
	public static void flush() {
		binval.clearContent();
	}

	private static Document setXML(String xml){
		try {
			return DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			Log.e("error","parse xml string failed.detail:"+e.getMessage());
			return null;
		}
	}

	public static String getXML(Bitmap image) {
		flush();
		binval.setText(BitmapUtil.bitmapToString(image));
		return document.getRootElement().asXML();
	}

	public static String getXML(String imageBase64Str) {
		flush();
		binval.setText(imageBase64Str);
		Log.d("binvalText",imageBase64Str);
		return document.getRootElement().asXML();
	}

	public static String getBase64Image(String xml) {
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
	
	public static Bitmap getImage(String xml){
		Document parseFromStr = setXML(xml);
		if(parseFromStr != null){
			Element root = parseFromStr.getRootElement();
			for (Iterator<?> iter = root.elementIterator(); iter.hasNext();) {
				Element element=(Element) iter.next();
				if(element.getName().equals("binval")){
					return BitmapUtil.stringToBitmap(element.getText());
				}
			}
		}
		return BitmapUtil.stringToBitmap("");
	}
}
