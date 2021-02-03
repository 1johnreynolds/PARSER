import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class DomParser {

  public static void main(String[] args) {
    try {
      // Get the DOM Builder Factory
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      // Get the DOM Builder
      DocumentBuilder builder = factory.newDocumentBuilder();
      // Load and Parse the XML document
      // document contains the complete XML as a Tree
      Document document = builder.parse(ClassLoader.getSystemResourceAsStream("dblp-soc-papers.xml"));
      // Iterating through the nodes and extracting the data
      NodeList nodeList = document.getDocumentElement().getChildNodes();
      List<Publication> publicationList = new ArrayList<Publication>();
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node instanceof Element) {
          // We have encountered an <inproceedings> or <article> tag
          Publication pub = new Publication();
          //initialize authors list.
          pub.authors = new ArrayList<String>();
          //initialize editors list.
          pub.editors = new ArrayList<String>();
          //initialize ee list.
          pub.ee = new ArrayList<String>();
          //initialize and define mdate value
          pub.mdate = node.getAttributes().getNamedItem("mdate").getNodeValue();
          //initialize and define key value
          pub.key = node.getAttributes().getNamedItem("key").getNodeValue();
          NodeList childNodes = node.getChildNodes();
          for (int j = 0; j < childNodes.getLength(); j++) {
            Node cNode = childNodes.item(j);
            // Identifying the child tag of inproceedings encountered or article
            if (cNode instanceof Element) {
              String content = cNode.getLastChild().getTextContent().trim();
              switch (cNode.getNodeName()) {
                case "author":
                  pub.authors.add(content);
                  break;
                case "editor":
                  pub.editors.add(content);
                  break;
                case "publisher":
                  pub.publisher = content;
                  break;
                case "title":
                  pub.title = content;
                  break;
                case "series":
                  pub.series =content;
                  break;
                case "journal":
                  pub.journal = content;
                  break;
                case "number":
                  pub.number = Integer.parseInt(content);
                  break;
                case "volume":
                  pub.volume = Integer.parseInt(content);
                  break;
                case "pages":
                  pub.pages = content;
                  break;
                case "year":
                  pub.year = Integer.parseInt(content);
                  break;
                case "crossref":
                  pub.cross_ref = content;
                  break;
                case "booktitle":
                  pub.book_title = content;
                  break;
                case "ee":
                  pub.ee.add(content);
                  break;
                case "isbn":
                  pub.isbn = content;
                  break;
                case "url":
                  pub.url = content;
                  break;
              }
            }
          }
          publicationList.add(pub);
        }
      }
      // Print the Article list
      for (Publication e : publicationList) {
        System.out.println(e);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
