package sc.ustc.controller;

import sc.ustc.configs.ActionConfig;
import sc.ustc.configs.ResultConfig;
import sc.ustc.helpers.ScHelper;
import sc.ustc.proxy.ActionProxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author HitoM
 */
public class SimpleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String actionName = requestURI.substring(requestURI.lastIndexOf("/") + 1, requestURI.indexOf(".sc"));

        // 读取Action配置
        InputStream stream =
                SimpleController.class.getClassLoader().getResourceAsStream("/resources/controller.xml");

        ActionConfig actionConfig = ScHelper.findAction(actionName, stream);

        if (actionConfig == null) {
            noActionOrResult("不可识别的Action请求！", resp);
        } else {
            try {
                ActionProxy proxy = new ActionProxy(actionConfig);
                String resultName = proxy.execute(actionConfig.getMethod(), req.getParameterMap());

                // 获取Action的处理结果后，跳转相应的结果页面
                ResultConfig resultConfig = checkResult(actionConfig, resultName);
                if (resultConfig == null) {
                    noActionOrResult("没有请求的资源", resp);
                } else {
                    String newURL = resultConfig.getValue();
                    if (ResultConfig.FORWARD_TAG.equals(resultConfig.getType())) {
                        req.getRequestDispatcher(newURL).forward(req, resp);
                    } else if (ResultConfig.REDIRECT_TYPE.equals(resultConfig.getType())) {
                        resp.sendRedirect(newURL);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * @param actionConfig actionConfig
     * @param resultName   resultName
     * @return ResultConfig which in ActionList matching resultName
     */
    private ResultConfig checkResult(ActionConfig actionConfig, String resultName) {
        for (ResultConfig resultConfig : actionConfig.getResultConfigs()) {
            if (resultConfig.getName().equals(resultName)) {
                return resultConfig;
            }
        }
        return null;
    }

    /**
     * Print error msg
     *
     * @param msg  error message
     * @param resp HttpServletResponse
     */
    private void noActionOrResult(String msg, HttpServletResponse resp) {
        resp.setContentType("text/html;charset=UTF-8");
        try {
            resp.getWriter().println("<html>"
                    + "<head><title>Error</title></head>"
                    + "<body>" + msg + "</body>"
                    + "</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
