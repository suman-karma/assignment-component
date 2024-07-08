package io.mhe.assignmentcomponent.common.util;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jodie_wu
 * Date: Mar 30, 2005
 * Time: 3:01:59 PM
 * To change this template use File | Settings | File Templates.
 */
public final class XmlUtils {
	
	private XmlUtils() {
	}
	
    private static final String DEFAULT_SAX_DRIVER_CLASS = "org.apache.xerces.parsers.SAXParser";

    public static Element buildDOMTree(String xmlString) throws Exception {
        return (new SAXBuilder(DEFAULT_SAX_DRIVER_CLASS)).build(new StringReader(xmlString)).getRootElement();
    }

    public static Element buildDOMTree(InputStream inputStream) throws Exception {
        return (new SAXBuilder(DEFAULT_SAX_DRIVER_CLASS)).build(inputStream).getRootElement();
    }

    public static Element buildDOMTree(Reader reader) throws Exception {
        return (new SAXBuilder(DEFAULT_SAX_DRIVER_CLASS)).build(reader).getRootElement();
    }

    public static Element getOnlyAttributes(Element fullElement) throws Exception {
        Element onlyAttributesElement = new Element(fullElement.getName());
        onlyAttributesElement.setAttributes(fullElement.getAttributes());
        return onlyAttributesElement;
    }

    public static String getChildText(Element parent, String childName) throws Exception {
        return getChildText(parent, childName, true);
    }

    public static String getChildText(Element parent, String childName, boolean required) throws Exception {
    	String text = parent.getChildTextTrim(childName);
        if (text == null && required) {
        	throw new Exception("required element \"<" + childName + ">\" missing in element \"<" + parent.getName() +
                    ">\"");
        }
        return text;
    }

    public static Element[] getChildElements(Element parent, String childName) {
    	List children = parent.getChildren(childName);
        return (Element[]) children.toArray(new Element[0]);
    }

    public static Element getChildElement(Element parent, String childName) throws Exception {
        return getChildElement(parent, childName, true);
    }

    public static Element getChildElement(Element parent, String childName, boolean required) throws Exception {
        Element child = parent.getChild(childName);
        if (child == null && required) {
            throw new Exception("required element \"<" + childName + ">\" missing in element \"<" + parent.getName() +
                    ">\"");
        }
        return child;
    }

    public static Element addElement(Element parent, String name, String text) {
        Element child = new Element(name);
        child.setText(text);
        parent.addContent(child);
        return child;
    }

    public static Element addElement(Element parent, String name) {
        Element child = new Element(name);
        parent.addContent(child);
        return child;
    }

    public static String buildXmlElement(String tag, String text) {
        return "<" + tag + ">" + text + "</" + tag + ">";
    }

    public static String toString(Element root, boolean includeHeader) {
        return toString(root, null, includeHeader);
    }

    public static String toString(Element root, String docType) {
        return toString(root, docType, true);
    }

    public static String toString(Element root, String docType, boolean includeHeader) {
        XMLOutputter xmlOutputter = new XMLOutputter();
        StringBuffer buffer = new StringBuffer();
        if (includeHeader) {
            buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            if (docType != null) {
                buffer.append("<!DOCTYPE " + docType + ">\n");
            }
        }
        return buffer.append(xmlOutputter.outputString(root)).toString();
    }

    public static String toString(Element root) {
        return toString(root, false);
    }

    public static Element buildElement(String name, String value) {
        Element element = new Element(name);
        element.setText(value);
        return element;
    }

    public static Element replaceChild(Element root, Element newChild) {
        root.removeChildren(newChild.getName());
        root.addContent(newChild);
        return newChild;
    }
    
    public static String cdata(String original) {
        return "<![CDATA[" + original + "]]>";
    }  
}