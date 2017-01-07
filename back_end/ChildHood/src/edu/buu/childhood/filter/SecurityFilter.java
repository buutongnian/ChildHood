package edu.buu.childhood.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.login.service.LoginService;
import edu.buu.childhood.util.AppKey;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/SecurityFilter")
public class SecurityFilter implements Filter {

	private LoginService loginService;
	private Gson json = new Gson();

	// 可匿名访问api接口
	private static List<String> anonymity = new ArrayList<String>() {
		private static final long serialVersionUID = 8061037757087758622L;

		{
			add("login.do");
			add("gameHead.do");
			add("gameContent.do");
			add("register.do");
			add("perfectInfo.do");
			add("amUserList.do");
			add("feedback.do");
		}
	};

	/**
	 * Default constructor.
	 */
	public SecurityFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * 通过doFiler判断当前API请求是否合法请求
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Date now = new Date();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String encryptedKey = (String) req.getParameter("appKey");
		String userName = (String) req.getParameter("userName");
		String uri = req.getRequestURI();
		if (uri.indexOf("return.jsp") == -1) {
			if (encryptedKey != null) {
				boolean anonymous = false;
				for (String apiName : anonymity) {
					if (uri.indexOf(apiName) != -1) {
						anonymous = true;
						break;
					}
				}
				if (!anonymous) {
					if (AppKey.check(encryptedKey, now)) {
						if (loginService == null) {
							System.out.println("loginService is null!");
						}
						String SecurityMode = loginService
								.statusCheck(userName);
						if (C.security.LEGAL.equals(SecurityMode)) {
							chain.doFilter(request, response);
						} else if (C.security.USER_NOT_EXIST
								.equals(SecurityMode)) {
							session.setAttribute("JsonString", json.toJson(
									new Message<String>(C.security.ILLEGAL,
											C.security.USER_NOT_EXIST),
									new TypeToken<Message<String>>() {
									}.getType()));
							res.sendRedirect("return.jsp");
						} else if (C.security.USER_NOT_LOGIN
								.equals(SecurityMode)) {
							session.setAttribute("JsonString", json.toJson(
									new Message<String>(C.security.ILLEGAL,
											C.security.USER_NOT_LOGIN),
									new TypeToken<Message<String>>() {
									}.getType()));
							res.sendRedirect("return.jsp");
						}
					} else {
						session.setAttribute("JsonString", json.toJson(
								new Message<String>(C.security.ILLEGAL,
										C.security.APPKEY_INCORRECT),
								new TypeToken<Message<String>>() {
								}.getType()));
						res.sendRedirect("return.jsp");
					}
				} else {
					chain.doFilter(request, response);
				}
			} else {
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.security.ILLEGAL,
								C.security.REQUEST_INCORRECT),
						new TypeToken<Message<String>>() {
						}.getType()));
				res.sendRedirect("return.jsp");
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ServletContext context = fConfig.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(context);
		loginService = (LoginService) ctx.getBean("loginService");
	}

}
