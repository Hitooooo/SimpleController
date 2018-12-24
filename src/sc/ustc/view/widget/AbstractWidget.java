package sc.ustc.view.widget;


import sc.ustc.view.IView;

/**
 * @author HitoM
 */
public abstract class AbstractWidget implements IView {
    private String name;
    private String label;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
