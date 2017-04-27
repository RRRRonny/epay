package com.chao117.servlet;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chao117.dao.impl.*;
import com.chao117.model.*;
import org.json.JSONException;
import org.json.JSONObject;

import com.chao117.base.Utils;
import com.chao117.base.constant.APIHelper;
import com.chao117.base.constant.ErrorCode;
import com.chao117.dao.bak.ILoginDAOBAK;
import com.chao117.dao.bak.LoginDAOImplBAK;
import com.chao117.test.GsonTest;
import com.google.gson.Gson;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet({"/login", "/publish", "/register",
        "/requestGoodsDetail", "/requestUser", "/simpleBrowse",
        "/password", "/fav", "/requestFav",
        "/transRequest", "/transAccept", "/transCancel", "/transCheck", "/transUpdate",
        "/reqTransCheck", "/reqTransCancel",
        "/addShipAddress", "/requestShipAddress",
        "/addHistory", "/requestHistory",
        "/checkDatabaseVer", "/updateLocalDatabase",
        "/searchGoods", "/requestMessages", "/publishMessage",
        "/test", "/loginbak", "/forUser"})
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
     * response)
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
            out.println("<title>EPay API</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>request api by " + request.getContextPath() + "</h2>");
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
        // 任务分发,根据 preparedJson.getWhat() 匹配 APIHelper 中的请求码分发任务
        switch (preparedJson.getWhat()) {
            /*
                账户管理
             */
            case API_TYPE_LOGIN://登录
                result = login(preparedJson);
                break;
            case API_TYPE_REGISTER://注册
                result = register(preparedJson);
                break;
            case API_TYPE_REQ_USER://请求用户具体信息
                result = requestUser(preparedJson);
                break;
            case API_TYPE_PASSWORD://修改密码
                result = modifyPassword(preparedJson);
                break;
            /*
                收货地址
             */
            case API_TYPE_ADD_SHIP_ADDRESS:
                result = addShipAddress(preparedJson);
                break;
            case API_TYPE_REQ_SHIP_ADDRESS:
                result = requestShipAddress(preparedJson);
                break;
            /*
                收藏商品
             */
            case API_TYPE_FAV://收藏商品
                result = favGoods(preparedJson);
                break;
            case API_TYPE_REQ_FAV://请求收藏的商品
                result = requestFavGoods(preparedJson);
                break;
            /*
                交易相关 api
             */
            case API_TYPE_ACCEPT_TRANSACTION:
                result = acceptTransaction(preparedJson);
                break;
            case API_TYPE_CANCEL_TRANSACTION:
                result = cancelTransaction(preparedJson);
                break;
            case API_TYPE_CHECK_TRANSACTION:
                result = checkTransaction(preparedJson);
                break;
            case API_TYPE_REQUEST_TRANSACTION:
                result = requestTransaction(preparedJson);
                break;
            case API_TYPE_UPDATE_TRANSACTION:
                result = updateTransaction(preparedJson);
                break;
            case API_TYPE_CHECK_REQ_TRANSACTION:
                result = checkReqTrans(preparedJson);
                break;
            case API_TYPE_CANCEL_REQ_TRANSACTION:
                result = cancelReqTrans(preparedJson);
                break;
            /*
                商品相关
              */
            case API_TYPE_PUBLISH://发布商品
                result = publishGoods(preparedJson);
                break;
            case API_TYPE_BROWSE:
                result = simpleBrowse(preparedJson);
                //todo
                break;
            case API_TYPE_REQ_GOODS_DETAIL:
                result = requestGoodsDetail(preparedJson);
                break;
             /*
                浏览历史
                 */
            case API_TYPE_ADD_HISTORY:
                result = addHistory(preparedJson);
                break;
            case API_TYPE_REQ_HISTORY:
                result = requestHistory(preparedJson);
                break;
            /*
                数据库更新
             */
            case API_TYPE_CHECK_DB_VERSION:
                //todo
                break;
            case API_TYPE_UPDATE_LOCAL_DB:
                //todo
                break;
            /*
                message
             */
            case API_TYPE_PUBLISH_MSG:
                //todo
                break;
            case API_TYPE_REQUEST_MSGS:
                //todo
                break;

            /**
             * 重要,搜索 api
             */
            //todo 实现搜索api


             /*
                old api
                 */
            case API_TYPE_TEST://测试
                User user = new GsonTest(preparedJson.getJsonObject().toString()).getUser();
                System.out.println(user.toString());
                break;
            case API_TYPE_LOGIN_BAK://api 1.0 注册
                result = loginBAK(preparedJson);
                break;
            default:
                System.out.println("为匹配到命令");
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
        ServerResult serverResult = doLoginImpl.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 修改密码
     *
     * @param preparedJson
     * @return
     */
    private String modifyPassword(PreparedJson preparedJson) {
        show("执行 password");
        String result = null;
        User user = new Gson().fromJson(preparedJson.getJsonObject().toString(), User.class);
        //todo 修改密码
        ModifyPasswordImpl doModifyPassword = new ModifyPasswordImpl();
        doModifyPassword.init(user);
        doModifyPassword.doOperate();
        ServerResult serverResult = doModifyPassword.getServerResult();
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
        ServerResult serverResult = doRegisterImpl.getServerResult();
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
        ServerResult serverResult = doPublishImpl.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 请求用户的具体信息
     *
     * @param preparedJson
     * @return
     */
    private String requestUser(PreparedJson preparedJson) {
        show("执行 requestUserInfo");
        String result = null;
        User user = new Gson().fromJson(preparedJson.getJsonObject().toString(), User.class);
        //todo 具体实现
        RequestUserImpl doRequestUser = new RequestUserImpl();
        doRequestUser.init(user);
        doRequestUser.doOperate();
        ServerResult<User> serverResult = doRequestUser.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 收藏商品
     *
     * @param preparedJson
     * @return
     */
    private String favGoods(PreparedJson preparedJson) {
        show("执行 requestUserInfo");
        String result = null;
        Follow follow = new Gson().fromJson(preparedJson.getJsonObject().toString(), Follow.class);
        FavGoodsImpl doFavGoods = new FavGoodsImpl();
        doFavGoods.init(follow);
        doFavGoods.doOperate();
        ServerResult serverResult = doFavGoods.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 请求收藏的商品
     *
     * @param preparedJson
     * @return
     */
    private String requestFavGoods(PreparedJson preparedJson) {
        show("执行 requestFavGoods");
        String result = null;
        User user = new Gson().fromJson(preparedJson.getJsonObject().toString(), User.class);
        RequestFavGoodsImpl doRequestFavGoods = new RequestFavGoodsImpl();
        doRequestFavGoods.init(user);
        doRequestFavGoods.doOperate();
        ServerResult serverResult = doRequestFavGoods.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 请求商品详情
     *
     * @param preparedJson
     * @return
     */
    private String requestGoodsDetail(PreparedJson preparedJson) {
        show(" 执行 requestGoodsDetail");
        String result = null;
        Goods goods = new Gson().fromJson(preparedJson.getJsonObject().toString(), Goods.class);
        RequestGoodsDetailImpl doRequestGoodsDetail = new RequestGoodsDetailImpl();
        doRequestGoodsDetail.init(goods);
        doRequestGoodsDetail.doOperate();
        ServerResult serverResult = doRequestGoodsDetail.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 简单浏览
     *
     * @param preparedJson
     * @return
     */
    private String simpleBrowse(PreparedJson preparedJson) {
        show(" 执行 simpleBrowse");
        String result = null;
        Goods goods = new Gson().fromJson(preparedJson.getJsonObject().toString(), Goods.class);
        SimpleBrowseImpl doSimpleBrowse = new SimpleBrowseImpl();
        doSimpleBrowse.init(goods);
        doSimpleBrowse.doOperate();
        ServerResult serverResult = doSimpleBrowse.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);

        return result;
    }


    /**
     * 增加收货地址
     *
     * @param preparedJson
     * @return
     */
    private String addShipAddress(PreparedJson preparedJson) {
        show("执行 addShipAddress");
        String result = null;
        ShipAddress shipAddress = new Gson().fromJson(preparedJson.getJsonObject().toString(), ShipAddress.class);
        //todo
        AddShipAddImpl doAddShipAddress = new AddShipAddImpl();
        doAddShipAddress.init(shipAddress);
        doAddShipAddress.doOperate();
        ServerResult serverResult = doAddShipAddress.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 请求收货地址
     *
     * @param preparedJson
     * @return
     */
    private String requestShipAddress(PreparedJson preparedJson) {
        show("执行 addShipAddress");
        String result = null;
        User user = new Gson().fromJson(preparedJson.getJsonObject().toString(), User.class);
        RequestShipAddressImpl doRequestShipAddress = new RequestShipAddressImpl();
        doRequestShipAddress.init(user);
        doRequestShipAddress.doOperate();
        ServerResult serverResult = doRequestShipAddress.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 新增浏览历史
     *
     * @param preparedJson
     * @return
     */
    private String addHistory(PreparedJson preparedJson) {
        show("执行 addShipAddress");
        String result = null;
        History history = new Gson().fromJson(preparedJson.getJsonObject().toString(), History.class);
        AddHistoryImpl doAddHistory = new AddHistoryImpl();
        doAddHistory.init(history);
        doAddHistory.doOperate();
        ServerResult serverResult = doAddHistory.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;

    }

    /**
     * 请求浏览历史
     *
     * @param preparedJson
     * @return
     */
    private String requestHistory(PreparedJson preparedJson) {
        show("执行 addShipAddress");
        String result = null;
        User user = new Gson().fromJson(preparedJson.getJsonObject().toString(), User.class);
        RequestHistoryImpl doRequestHistory = new RequestHistoryImpl();
        doRequestHistory.init(user);
        doRequestHistory.doOperate();
        ServerResult serverResult = doRequestHistory.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;

    }

    /**
     * 请求交易,实际在 table_req_trans 表中操作
     *
     * @param preparedJson
     * @return
     */
    private String requestTransaction(PreparedJson preparedJson) {
        show(" 执行 requestTransaction");
        String result = null;
        RequestTrans requestTrans = new Gson().fromJson(preparedJson.getJsonObject().toString(), RequestTrans.class);
        AddRequestTransImpl doRequestTrans = new AddRequestTransImpl();
        doRequestTrans.init(requestTrans);
        doRequestTrans.doOperate();
        ServerResult serverResult = doRequestTrans.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 接受交易
     *
     * @param preparedJson
     * @return
     */
    private String acceptTransaction(PreparedJson preparedJson) {
        show(" 执行 acceptTransaction");
        String result = null;
        RequestTrans requestTrans = new Gson().fromJson(preparedJson.getJsonObject().toString(), RequestTrans.class);
        AcceptRequestTransImpl doAcceptReqTransImpl = new AcceptRequestTransImpl();
        doAcceptReqTransImpl.init(requestTrans);
        doAcceptReqTransImpl.doOperate();
        ServerResult serverResult = doAcceptReqTransImpl.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 查看交易请求
     *
     * @param preparedJson
     * @return
     */
    private String checkReqTrans(PreparedJson preparedJson) {
        show("执行 checkReqTrans");
        String result = null;
        User user = new Gson().fromJson(preparedJson.getJsonObject().toString(), User.class);
        CheckReqTransImpl doCheckReqTrans = new CheckReqTransImpl();
        doCheckReqTrans.init(user);
        doCheckReqTrans.doOperate();
        ServerResult serverResult = doCheckReqTrans.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }


    /**
     * 更新交易
     *
     * @param preparedJson
     * @return
     */
    private String updateTransaction(PreparedJson preparedJson) {
        show(" 执行 updateTransaction");
        String result = null;
        Transaction transaction = new Gson().fromJson(preparedJson.getJsonObject().toString(), Transaction.class);
        UpdateTransImpl doUpdateTrans = new UpdateTransImpl();
        doUpdateTrans.init(transaction);
        doUpdateTrans.doOperate();
        ServerResult serverResult = doUpdateTrans.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 取消交易
     *
     * @param preparedJson
     * @return
     */
    private String cancelTransaction(PreparedJson preparedJson) {
        show(" 执行 cancelTransaction");
        String result = null;
        Transaction transaction = new Gson().fromJson(preparedJson.getJsonObject().toString(), Transaction.class);
        CancelTransImpl doCancelTrans = new CancelTransImpl();
        doCancelTrans.init(transaction);
        doCancelTrans.doOperate();
        ServerResult serverResult = doCancelTrans.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 取消交易请求
     *
     * @param preparedJson
     * @return
     */
    private String cancelReqTrans(PreparedJson preparedJson) {
        show(" 执行 cancelReqTrans");
        String result = null;
        RequestTrans requestTrans = new Gson().fromJson(preparedJson.getJsonObject().toString(), RequestTrans.class);
        CancelReqTransImpl doCancelReqTrans = new CancelReqTransImpl();
        doCancelReqTrans.init(requestTrans);
        doCancelReqTrans.doOperate();
        ServerResult serverResult = doCancelReqTrans.getServerResult();
        result = Utils.gsonBuilder(serverResult, ServerResult.class);
        return result;
    }

    /**
     * 查看交易
     *
     * @param preparedJson
     * @return
     */
    private String checkTransaction(PreparedJson preparedJson) {
        show("执行 checkTransaction");
        String result = null;
        User user = new Gson().fromJson(preparedJson.getJsonObject().toString(), User.class);
        CheckTransImpl doCheckTrans = new CheckTransImpl();
        doCheckTrans.init(user);
        doCheckTrans.doOperate();
        ServerResult serverResult = doCheckTrans.getServerResult();
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
