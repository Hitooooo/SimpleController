package sc.ustc.view.widget;


/**
 * @author HitoM
 */
public class CheckBox extends AbstractWidget {

    public static final String CHECKBOX_TAG = "checkBoxView";

    @Override
    public String getHtmlView() {
        return "<input type=\"checkbox\" value=\""+this.getValue()+"\""+" name=\""+this.getName()+"\"/>\n";
    }
}
