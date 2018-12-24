package sc.ustc.view;

/**
 * @author HitoM
 * @date 2018/12/23 16:20
 */
public class View implements IView {
    private Header header;
    private Body body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String getHtmlView() {
        String header = "<!DOCTYPE html>\n<html>\n";
        String footer = "</html>\n";
        return header + this.header.getHtmlView()+body.getHtmlView()+footer;
    }
}
