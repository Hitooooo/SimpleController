package sc.ustc.configs;

/**
 * 拦截器配置
 *
 * @author HitoM
 * @date 2018/12/19 16:10
 */
public class InterceptorConfig {

    public static final String INTERCEPTOR_TAG = "interceptor";
    public static final String INTERCEPTOR_REF_TAG = "interceptor-ref";

    private String name;
    private String classpath;
    private String predo;
    private String afterdo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public String getPredo() {
        return predo;
    }

    public void setPredo(String predo) {
        this.predo = predo;
    }

    public String getAfterdo() {
        return afterdo;
    }

    public void setAfterdo(String afterdo) {
        this.afterdo = afterdo;
    }
}
