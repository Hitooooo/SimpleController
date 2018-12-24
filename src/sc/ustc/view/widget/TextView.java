package sc.ustc.view.widget;


/**
 * @author HitoM
 */
public class TextView extends AbstractWidget {

    public static final String TEXTVIEW_TAG = "textView";

    @Override
    public String getHtmlView() {
        return "<input type=\"text\" value=\""+this.getValue()+"\""+" name=\""+this.getName()+"\"/>\n";
    }
}
