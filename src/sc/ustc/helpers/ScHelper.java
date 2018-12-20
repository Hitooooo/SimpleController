package sc.ustc.helpers;

import org.xml.sax.SAXException;
import sc.ustc.configs.ActionConfig;

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
     * @param stream     a controller xml path
     * @return a new instance of Action
     */
    public static ActionConfig findAction(String actionName, InputStream stream) {
        if (factory == null) {
            factory = SAXParserFactory.newInstance();
        }

        SAXParser parser = null;
        ControllerParseHandler handler = new ControllerParseHandler();
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
        return handler.getAction(actionName);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean equals(String s1, String s2) {
        return s1 != null && s1.equals(s2);
    }
}
