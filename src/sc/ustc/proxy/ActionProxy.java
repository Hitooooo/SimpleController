package sc.ustc.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sc.ustc.configs.ActionConfig;
import sc.ustc.configs.InterceptorConfig;
import sc.ustc.controller.Action;
import sc.ustc.controller.Interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author HitoM
 * @date 2018/12/18 14:35
 */
public class ActionProxy implements MethodInterceptor {

    private Enhancer enhancer;
    private Class<Action> actionClass;
    private ActionConfig mActionConfig;
    private List<Interceptor> interceptors;

    public ActionProxy(ActionConfig actionConfig) {
        this.mActionConfig = actionConfig;
        actionClass = null;
        try {
            //noinspection unchecked
            actionClass = (Class<Action>) Class.forName(actionConfig.getClassPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 保存所有的interceptor
        interceptors = new ArrayList<>();
        List<InterceptorConfig> intercepts = mActionConfig.getInterceptorConfigs();
        if (intercepts != null) {
            for (InterceptorConfig intercept : intercepts) {
                String classpath = intercept.getClasspath();
                try {
                    @SuppressWarnings("unchecked")
                    Class<Interceptor> aClass = (Class<Interceptor>) Class.forName(classpath);
                    Interceptor interceptor = aClass.newInstance();
                    interceptors.add(interceptor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 传入的Action对应的处理方法，返回处理结果
     *
     * @param methodName method name
     * @return the result of method which you give the name
     */
    public String execute(String methodName, Map<String, String[]> params) {
        // Enhancer来做代理
        enhancer = new Enhancer();
        enhancer.setSuperclass(actionClass);
        enhancer.setCallback(this);
        Object object = enhancer.create();

        // 给Action的成员变量赋值
        Field[] fields = actionClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                field.set(object, params.get(field.getName())[0]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 调用Action中的处理方法
        Method method;
        String resultName = "";
        try {
            method = actionClass.getDeclaredMethod(methodName);
            resultName = (String) method.invoke(object);
        } catch (Exception e) {
            System.out.println("ActionPoxy execute fail");
        }
        return resultName;
    }


    /**
     *
     * @param o      cglib生成的代理对象
     * @param method 被代理的实体类方法
     * @param objects 方法的参数
     * @param methodProxy 生成的方法代理
     * @return 重写后返回的是Action的处理方法的返回值
     * @throws Throwable Exception
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        preAction();
        Object methodResult = methodProxy.invokeSuper(o, objects);
        afterAction((String) methodResult);
        return methodResult;
    }

    private void preAction() {
        // 如果没有设置拦截器，就return
        if (interceptors.size() == 0) {
            return;
        }
        for (Interceptor interceptor : interceptors) {
            try {
                Method method = interceptor.getClass().getDeclaredMethod(Interceptor.PRE_DO, String.class);
                method.invoke(interceptor, mActionConfig.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void afterAction(String result) {
        // 如果没有设置拦截器，就return
        if (interceptors.size() == 0) {
            return;
        }
        for (Interceptor interceptor : interceptors) {
            try {
                Method method = interceptor.getClass().getDeclaredMethod(Interceptor.AFTER_DO, String.class);
                method.invoke(interceptor, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
