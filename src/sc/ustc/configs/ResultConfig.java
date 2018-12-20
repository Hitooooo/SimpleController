package sc.ustc.configs;

/**
 * controller.xml中的Result对应的Pojo类。
 *
 * @author HitoM
 * @date 2018/12/11 18:04
 */
public class ResultConfig {

    public static final String RESULT_TAG = "result";

    public static final String FORWORD_TYPE = "forward";

    public static final String REDIRECT_TYPE = "redirect";

    private String name;

    private String type;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
