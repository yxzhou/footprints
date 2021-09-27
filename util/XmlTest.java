package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlTest {

	public static Document createDOM(String strXML)
			throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setIgnoringComments(false);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		// db = dbf.newDocumentBuilder(); -- this seems duplicated
		// db.setEntityResolver(new NullResolver());
		// db.setErrorHandler(new SamlParserErrorHandler());
		InputSource sourceXML = new InputSource(new StringReader(strXML));
		Document xmlDoc = db.parse(sourceXML);

		System.out.println("Root element :"
				+ xmlDoc.getDocumentElement().getNodeName());
		return xmlDoc;
	}

	public static void main(String[] args) {
		//

		File fXmlFile = new File(
				"C:\\Users\\ivy\\Google Drive\\app\\gafa\\testData\\decodedResponseStr_2.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			// doc.getDocumentElement().normalize();

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		XmlTest sv = new XmlTest();
		Scanner in = null;
		try {
			in = new Scanner(
					new FileInputStream(
							"C:\\Users\\ivy\\Google Drive\\app\\gafa\\testData\\decodedResponseStr_2.xml"),
					"UTF-8");
			StringBuilder strXml = new StringBuilder();

			while (in.hasNext()) {
				// init

				// read
				strXml.append(in.nextLine());
			}

			Document doc = sv.createDOM(strXml.toString());

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			if (null != in) {
				in.close();
			}
		}

		try {
			in = new Scanner(
					new FileInputStream(
							"C:\\Users\\ivy\\Google Drive\\app\\gafa\\testData\\decodedResponseStr_zh.xml"),
					"UTF-8");
			StringBuilder strXml = new StringBuilder();

			while (in.hasNext()) {
				// init

				// read
				strXml.append(in.nextLine());
			}

			Document doc = sv.createDOM(strXml.toString());

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			if (null != in) {
				in.close();
			}
		}
	}

}
