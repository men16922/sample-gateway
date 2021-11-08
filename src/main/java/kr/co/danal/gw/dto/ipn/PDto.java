package kr.co.danal.gw.dto.ipn;

import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PDto {

    @NotBlank
    @Size(max = 20) @Required
    private String TXTYPE;

    @NotBlank
    @Size(max = 10) @Required
    private String VERSION;

    @NotBlank
    @Size(max = 30) @Required
    private String MERCHANTID;

    @Size(max = 30)
    private String SUBMERCHANTID;

    @NotBlank
    @Size(max = 80) @Required
    private String HMAC;

    @NotBlank
    @Size(max = 14) @Required
    private String ORDERSTAMP;

    @NotBlank
    @Size(max = 80) @Required
    private String ORDERID;

    @NotBlank
    @Size(max = 256) @Required
    private String ITEMINFO;

    @NotBlank
    @Size(max = 8) @Required
    private String TOTALAMOUNT;

    @NotBlank
    @Size(max = 2) @Required
    private String COUNTRY;

    @NotBlank
    @Size(max = 3) @Required
    private String CURRENCY;

    @NotBlank
    @Size(max = 10) @Required
    private String SERVICETYPE;

    @Size(max = 13)
    private String MOBILENUMBER;

    @Size(max = 5)
    private String OPERATOR;

    @Size(max = 23)
    private String USERIP;

    @Size(max = 80)
    private String USERID;

    @Size(max = 80)
    private String USEREMAIL;

    @NotBlank
    @Size(max = 255) @Required
    private String CALLBACKURL;

    @NotBlank
    @Size(max = 255) @Required
    private String REDIRECTURL;

    @NotBlank
    @Size(max = 255) @Required
    private String ERRORURL;

    @NotBlank
    @Size(max = 255) @Required
    private String CANCELURL;

    @NotBlank
    @Size(max = 255) @Required
    private String NOTIFYEMAIL;

    @Size(max = 255)
    private String OFFER_TYPE;

    @Size(max = 255)
    private String CUSTOM1;

    @Size(max = 255)
    private String CUSTOM2;

    @Size(max = 255)
    private String CUSTOM3;


}
