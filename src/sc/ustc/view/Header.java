package sc.ustc.view;

/**
 * @author HitoM
 * @date 2018/12/23 16:19
 */
public class Header implements IView {

    public static final String HEADER_TAG = "header";

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getHtmlView() {
        String header = "<head>\n<title>";
        String footer = "</title>\n</head>\n";
        return header + this.getTitle() + footer;
    }
}
