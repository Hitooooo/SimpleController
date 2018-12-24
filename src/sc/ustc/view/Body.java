package sc.ustc.view;

/**
 * @author HitoM
 * @date 2018/12/23 16:18
 */
public class Body implements IView {

    public static final String BODG_TAG = "body";

    private Form form;

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    @Override
    public String getHtmlView() {
        String header = "<body>\n";
        String footer = "</body>\n";
        return header + form.getHtmlView() + footer;
    }
}
