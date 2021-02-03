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
      List<Article>articleList = new ArrayList<Article>();
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node instanceof Element) {
          // We have encountered an <inproceedings> or <article> tag
          Article art = new Article();
          //initialize authors list.
          art.authors = new ArrayList<String>();
          //initialize editors list.
          art.editors = new ArrayList<String>();
          //initialize ee list.
          art.ee = new ArrayList<String>();
          //initialize and define mdate value
          art.mdate = node.getAttributes().getNamedItem("mdate").getNodeValue();
          //initialize and define key value
          art.key = node.getAttributes().getNamedItem("key").getNodeValue();
          NodeList childNodes = node.getChildNodes();
          for (int j = 0; j < childNodes.getLength(); j++) {
            Node cNode = childNodes.item(j);
            // Identifying the child tag of inproceedings encountered or article
            if (cNode instanceof Element) {
              String content = cNode.getLastChild().getTextContent().trim();
              switch (cNode.getNodeName()) {
                case "author":
                  art.authors.add(content);
                  break;
                case "editor":
                  art.editors.add(content);
                  break;
                case "publisher":
                  art.publisher = content;
                  break;
                case "title":
                  art.title = content;
                  break;
                case "series":
                  art.series =content;
                  break;
                case "journal":
                  art.journal = content;
                  break;
                case "number":
                  art.number = Integer.parseInt(content);
                  break;
                case "volume":
                  art.volume = Integer.parseInt(content);
                  break;
                case "pages":
                  art.pages = content;
                  break;
                case "year":
                  art.year = Integer.parseInt(content);
                  break;
                case "crossref":
                  art.cross_ref = content;
                  break;
                case "booktitle":
                  art.book_title = content;
                  break;
                case "ee":
                  art.ee.add(content);
                  break;
                case "isbn":
                  art.isbn = content;
                  break;
                case "url":
                  art.url = content;
                  break;
              }
            }
          }
          articleList.add(art);
        }
      }
      // Print the Article list
      for (Article e : articleList) {
        System.out.println(e);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
