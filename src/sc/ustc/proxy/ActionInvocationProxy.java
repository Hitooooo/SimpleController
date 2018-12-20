package sc.ustc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 记录每次客户端请求的 action 名称<name>、访问开始时间<s-time>，访问结束时间
 * * <e-time>、请求返回结果<result>，并将信息追加至日志文件 log.xml，保存在 PC 磁盘上
 *
 * @author HitoM
 * @date 2018/12/16 10:45
 */
public class ActionInvocationProxy<T> implements InvocationHandler {
    private T t;

    public  ActionInvocationProxy(T t) {
        this.t = t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 这里做
        return method.invoke(t, args);
    }
}
