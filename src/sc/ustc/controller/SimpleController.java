package sc.ustc.controller;

import sc.ustc.configs.ActionConfig;
import sc.ustc.configs.ResultConfig;
import sc.ustc.helpers.ScHelper;
import sc.ustc.proxy.ActionProxy;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

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
        ServletContext servletContext = getServletContext();

        ActionConfig actionConfig = ScHelper.findAction(actionName, "/WEB-INF/classes/resources/controller.xml", servletContext);

        if (actionConfig == null) {
            noActionOrResult("不可识别的Action请求！", resp);
        } else {
            try {
                ActionProxy proxy = new ActionProxy(actionConfig);
                String resultName = proxy.execute(actionConfig.getMethod(), req.getParameterMap());
                handleResult(req, resp, servletContext, actionConfig, resultName);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }


    private void handleResult(HttpServletRequest req, HttpServletResponse resp, ServletContext servletContext,
                              ActionConfig actionConfig, String resultName) throws IOException, ServletException {
        // 获取Action的处理结果后，跳转相应的结果页面
        ResultConfig result = checkResult(actionConfig, resultName);
        if (result == null) {
            noActionOrResult("没有请求的资源", resp);
        } else {
            String newResource = result.getValue();
            if (ResultConfig.FORWARD_TAG.equals(result.getType())) {
                // 如果是forward
                if (newResource.endsWith("_view.xml")) {
                    // result结尾是需要解析的xml时，打印对应的html
                    resp.setContentType("text/html;charset=UTF-8");
                    String html = ScHelper.getHtml(newResource, servletContext);
                    PrintWriter out = resp.getWriter();
                    out.println(html);
                } else {
                    // 使用RequestDispatcher进行页面跳转
                    req.getRequestDispatcher(newResource).forward(req, resp);
                }
            } else if (ResultConfig.REDIRECT_TYPE.equals(result.getType())) {
                resp.sendRedirect(newResource);
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
