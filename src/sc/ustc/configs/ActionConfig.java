package sc.ustc.configs;

import java.util.ArrayList;
import java.util.List;

/**
 * controller.xml中的Action对应的Pojo类。
 *
 * @author HitoM
 * @date 2018/12/10 20:58
 */
public class ActionConfig {

    public static final String ACTION_TAG = "action";

    private String name;

    private String classPath;

    private String method;

    private List<InterceptorConfig> interceptorConfigs;

    private List<ResultConfig> resultConfigs;

    public void addInterceptor(InterceptorConfig interceptorConfig) {
        if (interceptorConfigs == null) {
            interceptorConfigs = new ArrayList<>();
        }
        interceptorConfigs.add(interceptorConfig);
    }

    public void addResult(ResultConfig resultConfig) {
        if (resultConfigs == null) {
            resultConfigs = new ArrayList<>();
        }
        resultConfigs.add(resultConfig);
    }

    public List<ResultConfig> getResultConfigs() {
        return resultConfigs;
    }

    public void setResultConfigs(List<ResultConfig> resultConfigs) {
        this.resultConfigs = resultConfigs;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<InterceptorConfig> getInterceptorConfigs() {
        return interceptorConfigs;
    }

    public void setInterceptorConfigs(List<InterceptorConfig> interceptorConfigs) {
        this.interceptorConfigs = interceptorConfigs;
    }
}
