package sc.ustc.view.widget;


/**
 * @author HitoM
 */
public class Button extends AbstractWidget {

    public static final String BUTTON_TAG = "buttonView";

    @Override
    public String getHtmlView() {
        return "<input type=\"submit\" value=\""+this.getValue()+"\"/>\n";
    }
}
