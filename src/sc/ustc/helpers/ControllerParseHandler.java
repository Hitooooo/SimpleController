package sc.ustc.helpers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sc.ustc.configs.ActionConfig;
import sc.ustc.configs.InterceptorConfig;
import sc.ustc.configs.ResultConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取controller中的ActionList的XML解析帮助类。
 *
 * @author HitoM
 */
public class ControllerParseHandler extends DefaultHandler {

    private List<ActionConfig> actionConfigs;
    private List<InterceptorConfig> interceptorConfigs;
    private ActionConfig tempActionConfig;
    private ResultConfig resultConfig;
    private InterceptorConfig interceptorConfig;
    private String interceptor_ref;

    public ActionConfig getAction(String actionName) {
        for (ActionConfig actionConfig : actionConfigs) {
            if (actionName.contains(actionConfig.getName())) {
                return actionConfig;
            }
        }
        return null;
    }

    public InterceptorConfig getInterceptorConfig(String interceptorName) {
        for (InterceptorConfig config : interceptorConfigs) {
            if (ScHelper.equals(config.getName(), interceptorName)) {
                return config;
            }
        }
        return null;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        actionConfigs = new ArrayList<>();
        interceptorConfigs = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        switch (qName) {
            case ActionConfig.ACTION_TAG:
                tempActionConfig = new ActionConfig();
                tempActionConfig.setName(attributes.getValue(0));
                tempActionConfig.setClassPath(attributes.getValue(1));
                tempActionConfig.setMethod(attributes.getValue(2));
                break;
            case ResultConfig.RESULT_TAG:
                resultConfig = new ResultConfig();
                resultConfig.setName(attributes.getValue(0));
                resultConfig.setType(attributes.getValue(1));
                resultConfig.setValue(attributes.getValue(2));
                break;
            case InterceptorConfig.INTERCEPTOR_TAG:
                interceptorConfig = new InterceptorConfig();
                interceptorConfig.setName(attributes.getValue(0));
                interceptorConfig.setClasspath(attributes.getValue(1));
                interceptorConfig.setPredo(attributes.getValue(2));
                interceptorConfig.setAfterdo(attributes.getValue(3));
                break;
            case InterceptorConfig.INTERCEPTOR_REF_TAG:
                //开始解析Action内部的拦截器配置
                interceptor_ref = attributes.getValue(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        switch (qName) {
            case ActionConfig.ACTION_TAG:
                actionConfigs.add(tempActionConfig);
                tempActionConfig = null;
                break;
            case ResultConfig.RESULT_TAG:
                tempActionConfig.addResult(resultConfig);
                resultConfig = null;
                break;
            case InterceptorConfig.INTERCEPTOR_TAG:
                interceptorConfigs.add(interceptorConfig);
                interceptorConfig = null;
                break;
            case InterceptorConfig.INTERCEPTOR_REF_TAG:
                for (InterceptorConfig config : interceptorConfigs) {
                    if (ScHelper.equals(config.getName(), interceptor_ref)) {
                        tempActionConfig.addInterceptor(config);
                    }
                }
                interceptor_ref = null;
                break;
            default:
                break;
        }
    }
}
