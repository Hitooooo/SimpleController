package sc.ustc.view;

/**
 * View接口，实现类需要以字符串的形式返回一个Html组件
 *
 * @author HitoM
 * @date 2018/12/23 16:12
 */
public interface IView {
    /**
     * 实现类需要以字符串的形式返回一个Html组件
     *
     * @return 字符串的形式的Html中的组件
     */
    String getHtmlView();
}
