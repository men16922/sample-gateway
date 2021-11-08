package kr.co.danal.gw.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FilterUtil {
    private FilterUtil(){}

    public static Map returnCodeMapping(String str) throws Exception {
        log.info("=========================================================");
        log.info("returnCodeMapping");
        log.info("str : {}", str);

        Map<String, String> oMap = StringUtil.stringToMap(str, "=", "&");

        log.info("oMap : {}", oMap);

        Map<String, String> rMap = new HashMap<>();

        String returnCode = null;

        if(StringUtil.equals(oMap.get("RETURNCODE"), "0000")) {
            rMap.put("returncode", "00000");
            rMap.put("returnMsg", "SUCCESS");
        } else {
            rMap.put("returncode", "99999");
            rMap.put("returncode", "FAILED");
        }

        rMap.put("tid", "");
        rMap.put("start_url", oMap.get("STARTURL"));
        rMap.put("bill_flow_type", "1step");

        log.info("rMap : {}", rMap);

        return rMap;
//        return rMap.toString();

    }

    public static String maptoString(Map map) throws Exception {
        log.info("=========================================================");
        log.info("maptoString");
        log.info("map : {}", map);

        String answer = StringUtil.mapToString(map, "=", "&");
        log.info("str : {}" , answer);
        return answer;

    }

    public static Map authMapping(Map map){ // 추상화 필요
        log.info("=========================================================");
        log.info("authMapping");
        log.info("map : {}", map);

        Map<String, Object> authMap = new HashMap<>();

        authMap.put("TXTYPE", "<P>");
        authMap.put("VERSION", "1.0.0");
        authMap.put("MERCHANTID", ParamUtil.str(map.get("cp_id")));
        authMap.put("HMAC", "HMAC1234");
        authMap.put("ORDERSTAMP", "20211108084055");
        authMap.put("ORDERID", ParamUtil.str(map.get("order_id")));

        List<Map> itemList = new ArrayList<>();
        Map<String, String> itemMap = new HashMap<>();

        itemMap.put("CODE", "");
        itemMap.put("NAME", ParamUtil.str(map.get("item_name")));
        itemMap.put("AMT", ParamUtil.str(map.get("amount")));
        itemList.add(itemMap);

        authMap.put("ITEMINFO", itemList);
        authMap.put("TOTALAMOUNT", ParamUtil.str(map.get("amount")));
        authMap.put("COUNTRY", ParamUtil.str(map.get("country")));
        authMap.put("CURRENCY", ParamUtil.str(map.get("currency")));
        authMap.put("SERVICETYPE", "CREDIT");

        authMap.put("USERID", ParamUtil.str(map.get("user_id")));
        authMap.put("USEREMAIL", ParamUtil.str(map.get("user_email")));

        authMap.put("CALLBACKURL", ParamUtil.str(map.get("notify_url")));
        authMap.put("REDIRECTURL", ParamUtil.str(map.get("return_url")));
        authMap.put("ERRORURL", "ERRORURL");
        authMap.put("CANCELURL", "CANCELURL");
        authMap.put("NOTIFYEMAIL", ParamUtil.str(map.get("user_email")));

        return authMap;
    }

}
