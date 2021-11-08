package kr.co.danal.gw.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.danal.gw.dto.AuthDto;
import kr.co.danal.gw.dto.ipn.PDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class FilterUtil {
    private FilterUtil(){}

    private static Validator validator;

    @Autowired
    private Validator valid;

    @PostConstruct
    private void initValidator() {
        validator = this.valid;
    }

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

        rMap.put("tid", oMap.get("TID"));
        rMap.put("start_url", oMap.get("STARTURL"));
        rMap.put("bill_flow_type", "1step");

        log.info("rMap : {}", rMap);

        return rMap;
//        return rMap.toString();

    }

    public static String maptoString(PDto pDto) throws Exception {
        log.info("=========================================================");
        log.info("maptoString");
        log.info("pDto : {}", pDto);

        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.convertValue(pDto, Map.class);

        String answer = StringUtil.mapToString(map, "=", "&");
        log.info("str : {}" , answer);
        return answer;

    }

    public static PDto authMapping(AuthDto authDto) throws JSONException { // 추상화 필요
        Set<ConstraintViolation<AuthDto>> violations = validator.validate(authDto);

        if(!violations.isEmpty()) {
            log.info("VALIDATION CHECK FAILED");
        } else {
            log.info("VALIDATION CHECK SUCCEED");
        }

        log.info("=========================================================");
        log.info("authMapping");
        log.info("authDto : {}", authDto);

        JSONObject jsonVar = new JSONObject();
        jsonVar.put("CODE", "");
        jsonVar.put("NAME", ParamUtil.str(authDto.getItemName()));
        jsonVar.put("AMT", ParamUtil.str(authDto.getAmount()));
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonVar);

        PDto pdto = PDto.builder()
                    .TXTYPE("<P>")
                    .VERSION("1.0.0")
                    .MERCHANTID(ParamUtil.str(authDto.getCpId()))
                    .HMAC("HMAC1234")
                    .ORDERSTAMP("20211108084055")
                    .ORDERID(ParamUtil.str(authDto.getOrderId()))
                    .ITEMINFO(jsonArray.toString())
                    .TOTALAMOUNT(ParamUtil.str(authDto.getAmount()))
                    .COUNTRY(ParamUtil.str(authDto.getCountry()))
                    .CURRENCY(ParamUtil.str(authDto.getCurrency()))
                    .SERVICETYPE("CREDIT")
                    .USERID(ParamUtil.str(authDto.getUserId()))
                    .USEREMAIL(ParamUtil.str(authDto.getUserEmail()))
                    .CALLBACKURL(ParamUtil.str(authDto.getNotifyUrl()))
                    .REDIRECTURL(ParamUtil.str(authDto.getReturnUrl()))
                    .ERRORURL("ERROURL")
                    .CANCELURL("CANCELURL")
                    .NOTIFYEMAIL(ParamUtil.str(authDto.getUserEmail()))
                    .build();

        Set<ConstraintViolation<PDto>> violations2 = validator.validate(pdto);

        if(!violations2.isEmpty()) {
            log.info("VALIDATION2 CHECK FAILED");
        } else {
            log.info("VALIDATION2 CHECK SUCCEED");
        }

        return pdto;
//        Map<String, Object> authMap = new HashMap<>();
//
//        authMap.put("TXTYPE", "<P>");
//        authMap.put("VERSION", "1.0.0");
//        authMap.put("MERCHANTID", ParamUtil.str(authDto.getCpId()));
//        authMap.put("HMAC", "HMAC1234");
//        authMap.put("ORDERSTAMP", "20211108084055");
//        authMap.put("ORDERID", ParamUtil.str(authDto.getOrderId()));
//
//        List<Map> itemList = new ArrayList<>();
//        Map<String, String> itemMap = new HashMap<>();
//
//        itemMap.put("CODE", "");
//        itemMap.put("NAME", ParamUtil.str(authDto.getItemName()));
//        itemMap.put("AMT", ParamUtil.str(authDto.getAmount()));
//        itemList.add(itemMap);
//
//        authMap.put("ITEMINFO", itemList);
//        authMap.put("TOTALAMOUNT", ParamUtil.str(authDto.getAmount()));
//        authMap.put("COUNTRY", ParamUtil.str(authDto.getCountry()));
//        authMap.put("CURRENCY", ParamUtil.str(authDto.getCurrency()));
//        authMap.put("SERVICETYPE", "CREDIT");
//
//        authMap.put("USERID", ParamUtil.str(authDto.getUserId()));
//        authMap.put("USEREMAIL", ParamUtil.str(authDto.getUserEmail()));
//
//        authMap.put("CALLBACKURL", ParamUtil.str(authDto.getNotifyUrl()));
//        authMap.put("REDIRECTURL", ParamUtil.str(authDto.getReturnUrl()));
//        authMap.put("ERRORURL", "ERRORURL");
//        authMap.put("CANCELURL", "CANCELURL");
//        authMap.put("NOTIFYEMAIL", ParamUtil.str(authDto.getUserEmail()));
//
//        return authMap;
    }

}
