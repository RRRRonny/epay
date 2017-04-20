package com.chao117.servlet;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.chao117.base.Utils;
import com.chao117.base.constant.APIHelper;
import com.chao117.base.constant.ErrorCode;
import com.chao117.dao.BaseDAO;
import com.chao117.dao.bak.ILoginDAOBAK;
import com.chao117.dao.bak.LoginDAOImplBAK;
import com.chao117.dao.impl.LoginImpl;
import com.chao117.dao.impl.PublishImpl;
import com.chao117.dao.impl.RegisterImpl;
import com.chao117.model.Goods;
import com.chao117.model.PreparedJson;
import com.chao117.model.ServerResult;
import com.chao117.model.User;
import com.chao117.test.GsonTest;
import com.google.gson.Gson;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet({ "/login", "/publish", "/register", "/test", "/loginbak","/forUser" })
public class EPayServlet extends HttpServlet implements ErrorCode, APIHelper {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EPayServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("接受到 post 请求");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		String json = request.getParameter("json");
		String what = request.getRequestURI();
		what = what.substring(what.lastIndexOf("/") + 1);
		System.out.println("获取到请求地址,转换成请求码:" + what);
		System.out.println("获取到的完整请求json串:" + json);
		// 读取完成,生成预处理对象
		PreparedJson preparedJson = new PreparedJson(json, what);
		// 任务分发&生成返回数据,doServlet()是核心方法
		String responseString = doServlet(preparedJson);
		System.out.println("responseString:" + responseString);// 打印返回数据
		// 发送数据
		output(response, responseString);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>MyFirstServlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h2>Servlet MyFirstServlet at " + request.getContextPath() + "</h2>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close();
		}
	}

	/**
	 * 任务分发,任务流程处理 每条 switch 后更新 result
	 * 
	 * @param preparedJson
	 * @return
	 */
	private String doServlet(PreparedJson preparedJson) {
		String result = null;// 返回给客户端的数据
		//	JSONObject jsonObject;
		// 任务分发,根据 preparedJson.getWhat() 匹配 APIHelper 中的请求码分发任务
		switch (preparedJson.getWhat()) {
		case API_TYPE_LOGIN:
			result = login(preparedJson);
			break;
		case API_TYPE_PUBLISH:
			result = publishGoods(preparedJson);
			break;
		case API_TYPE_REGISTER:
			result = register(preparedJson);
			break;
		case API_TYPE_TEST:
			User user = new GsonTest(preparedJson.getJsonObject().toString()).getUser();
			System.out.println(user.toString());
			break;
		case API_TYPE_LOGIN_BAK:
			result = loginBAK(preparedJson);
			break;
		case API_TYPE_CHECK_USER:
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * 新 api 结构下的 login api 2.0
	 * 
	 * @param preparedJson
	 * @return
	 */
	private String login(PreparedJson preparedJson) {
		show("执行 login");
		String result = null;
		// User user =(User)
		// Utils.objectBuilder(preparedJson.getJsonObject().toString(),
		// User.class);
		User user = new Gson().fromJson(preparedJson.getJsonObject().toString(), User.class);
		LoginImpl doLoginImpl = new LoginImpl();
		doLoginImpl.init(user);
		doLoginImpl.doOperate();
		ServerResult serverResult = doLoginImpl.getserServerResult();
		result = Utils.gsonBuilder(serverResult, ServerResult.class);
		return result;
	}
	
	

	/**
	 * 简单注册 api api 2.0
	 * 
	 * @param preparedJson
	 * @return
	 */
	private String register(PreparedJson preparedJson) {
		show("执行 register");
		String result = null;
		User user = new Gson().fromJson(preparedJson.getJsonObject().toString(), User.class);
		RegisterImpl doRegisterImpl = new RegisterImpl();
		doRegisterImpl.init(user);
		doRegisterImpl.doOperate();
		ServerResult serverResult = doRegisterImpl.getserServerResult();
		result = Utils.gsonBuilder(serverResult, ServerResult.class);
		return result;
	}

	/**
	 * 发布商品 api 2.0
	 * 
	 * @param preparedJson
	 * @return
	 */
	private String publishGoods(PreparedJson preparedJson) {
		show("执行 publish");
		String result = null;
		Goods goods = new Gson().fromJson(preparedJson.getJsonObject().toString(), Goods.class);
		PublishImpl doPublishImpl = new PublishImpl();
		doPublishImpl.init(goods);
		doPublishImpl.doOperate();
		ServerResult serverResult = doPublishImpl.getserServerResult();
		result = Utils.gsonBuilder(serverResult, ServerResult.class);
		return result;
	}

	/*
	 * 以下为没有重大更新不需要维护的代码
	 */

	/**
	 * 旧 login 方法
	 * 
	 * @param preparedJson
	 * @return
	 */
	private String loginBAK(PreparedJson preparedJson) {
		show("执行 login_bak");
		String result;
		JSONObject jsonObject;
		ILoginDAOBAK doLogin = new LoginDAOImplBAK();
		doLogin.init(preparedJson.getJsonObject());
		jsonObject = new JSONObject();
		boolean isMath = doLogin.isMath();
		if (isMath) {
			try {
				jsonObject.put(ERROR_CODE, ERROR_NET);
				jsonObject.put(RESULT, RESULT_SUCCESS);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			try {
				jsonObject.put(ERROR_CODE, ERROR_PASSWORD);
				jsonObject.put(RESULT, RESULT_FAILURE);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		result = jsonObject.toString();
		return result;
	}

	/**
	 * 给客户端发送数据
	 * 
	 * @param response
	 * @param responseString
	 */
	private void output(HttpServletResponse response, String responseString) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
			writer.write(responseString);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void show(String string) {
		System.out.println(string);
	}
}
