package sc.ustc.helpers;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sc.ustc.configs.ActionConfig;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * SimpleController的工具类。
 *
 * @author HitoM
 * @date 2018/12/11 19:35
 */
public class ScHelper {

    private static SAXParserFactory factory;

    /**
     * @param actionName actionName from url
     * @return a new instance of Action
     */
    public static ActionConfig findAction(String actionName, String path, ServletContext
            servletContext) {
        return getXMLHandler(new ControllerParseHandler(), path, servletContext).getAction(actionName);
    }

    public static String getHtml(String path, ServletContext servletContext) {
        return getXMLHandler(new ViewParseHandler(),path,servletContext).getView().getHtmlView();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean equals(String s1, String s2) {
        return s1 != null && s1.equals(s2);
    }

    private static <T extends DefaultHandler> T getXMLHandler(T handler, String path, ServletContext
            servletContext) {
        if (factory == null) {
            factory = SAXParserFactory.newInstance();
        }
        InputStream stream = servletContext.getResourceAsStream(path);
        SAXParser parser = null;
        if (stream != null) {
            try {
                parser = factory.newSAXParser();
                parser.parse(stream, handler);
            } catch (ParserConfigurationException e) {
                System.out.println("SAX init fail!");
            } catch (SAXException | IOException e) {
                System.out.println("SAX parse fail!");
            }
        }
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return handler;
    }
}
