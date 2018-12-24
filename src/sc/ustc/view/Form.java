package sc.ustc.view;

import sc.ustc.view.widget.AbstractWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HitoM
 * @date 2018/12/23 16:18
 */
public class Form implements IView {
    public static final String FORM_TAG = "form";

    private String name;
    private String action;
    private String method;
    private List<AbstractWidget> widgets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<AbstractWidget> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<AbstractWidget> widgets) {
        this.widgets = widgets;
    }

    public void addWidget(AbstractWidget widget) {
        if (this.widgets == null) {
            this.widgets = new ArrayList<>();
        }
        this.widgets.add(widget);
    }

    @Override
    public String getHtmlView() {
        StringBuilder builder = new StringBuilder();
        String header = "<form name=\"" + this.getName() + "\" action=\"" + this.action + "\" method=\"" + this.method + "\">\n";
        builder.append(header);
        for (AbstractWidget widget : widgets) {
            builder.append(widget.getHtmlView());
        }
        String footer = "</form>\n";
        builder.append(footer);

        return builder.toString();
    }
}
