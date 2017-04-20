package com.chao117.base.constant;

public interface APIHelper {
	//服务器通信用标记
    String JSON = "json";
    String REQUEST_CODE = "requestCode";

    //返回 json 串必须有的类型
    String KIND = "kind";
    String ERROR_CODE = "errorCode";
    String RESULT = "result";


    String FIELD_WHAT = "what";

    String OBJECT = "object";
    String OBJECT_ARRY = "objectArray";

    //kind
    String KIND_OBJ = "kindObj";
    String KIND_ARR = "kindArr";

    //result
    String RESULT_SUCCESS = "resultSuccess";
    String RESULT_FAILURE = "resultFailure";

 
    //请求的 api 功能
    String API_TYPE_TEST = "test";
    String API_TYPE_LOGIN_BAK ="loginbak";
    //api 2.0:使用 ServerResult 返回结果
    String API_TYPE_LOGIN = "login";
    String API_TYPE_REGISTER = "register";
    String API_TYPE_PUBLISH = "publish";
    String API_TYPE_CHECK_USER = "forUser";
}
