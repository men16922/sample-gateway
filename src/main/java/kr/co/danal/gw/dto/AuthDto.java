package kr.co.danal.gw.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.ToString;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

@Getter
@Setter
@ToString
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AuthDto {

    @NotBlank @Size(max = 30) @Required
    private String cpId;

    @Size(max = 30)
    private String subCpId;

    @NotBlank @Size(min = 2, max = 2) @Required
    private String country;

    @NotBlank @Size(max = 20) @Required
    private String pgCode;

    @NotBlank @Size(max = 20) @Required
    private String paymethod;

    @NotBlank @Size(max = 30) @Required
    private String itemName;

    @NotBlank @Size(min = 3, max = 3) @Required
    private String currency;

    @NotNull
    @Positive
    @Required
    private Float amount;

    @NotBlank @Size(max = 30) @Required
    private String orderId;

    @NotBlank @Size(max = 30) @Required
    private String userId;

    @Size(max = 50)
    private String userEmail;

    private Boolean mobileUi;

    @NotBlank @Size(max = 100) @Required
    private String returnUrl;

    private Boolean issueBillkeyOnly;

    @Size(max = 200)
    private String byPassValue;

    @Size(max = 100)
    private String notifyUrl;
}
