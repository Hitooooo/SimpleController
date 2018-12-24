package sc.ustc.helpers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sc.ustc.view.Body;
import sc.ustc.view.Form;
import sc.ustc.view.Header;
import sc.ustc.view.View;
import sc.ustc.view.widget.AbstractWidget;
import sc.ustc.view.widget.Button;
import sc.ustc.view.widget.CheckBox;
import sc.ustc.view.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HitoM
 * @date 2018/12/23 16:05
 */
public class ViewParseHandler extends DefaultHandler {
    private View view;
    private Header header;
    private Body body;
    private Form form;
    private List<AbstractWidget> widgetList;
    private AbstractWidget widget;
    private String tagName;

    public View getView() {
        return view;
    }

    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }

    public Form getForm() {
        return form;
    }

    public List<AbstractWidget> getWidgetList() {
        return widgetList;
    }

    @Override
    public void startDocument() throws SAXException {
        this.view = new View();
        this.widgetList = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        switch (qName) {
            case Header.HEADER_TAG:
                this.header = new Header();
                break;
            case Body.BODG_TAG:
                this.body = new Body();
                break;
            case Form.FORM_TAG:
                this.form = new Form();
                break;
            case TextView.TEXTVIEW_TAG:
                this.widget = new TextView();
                break;
            case Button.BUTTON_TAG:
                this.widget = new Button();
                break;
            case CheckBox.CHECKBOX_TAG:
                this.widget = new CheckBox();
                break;
            default:
                break;
        }
        this.tagName = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        switch (qName) {
            case Header.HEADER_TAG:
                view.setHeader(header);
                break;
            case Body.BODG_TAG:
                view.setBody(body);
                break;
            case Form.FORM_TAG:
                body.setForm(form);
                break;
            case TextView.TEXTVIEW_TAG:
            case Button.BUTTON_TAG:
            case CheckBox.CHECKBOX_TAG:
                form.addWidget(widget);
                widget = null;
                break;
            default:
                break;
        }
        this.tagName = null;
    }


    /**
     * 当解析节点内容时调用
     *
     * @param ch
     * @param start  start index
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String value = new String(ch, start, length);
        if (this.tagName == null) {
            return;
        }
        switch (this.tagName) {
            case "title":
                this.header.setTitle(value);
                break;
            case "name":
                if (this.widget != null) {
                    this.widget.setName(value);
                } else if (this.form != null) {
                    this.form.setName(value);
                }
                break;
            case "action":
                this.form.setAction(value);
                break;
            case "method":
                this.form.setMethod(value);
                break;
            case "label":
                this.widget.setLabel(value);
                break;
            case "value":
                this.widget.setValue(value);
                break;
            default:
                break;
        }
    }

}
