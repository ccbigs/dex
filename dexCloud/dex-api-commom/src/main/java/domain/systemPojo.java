package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-10-09-时间:22:19
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class systemPojo {
    private Integer id;
    private String dexName;
    private String dexMessage;
    private BigDecimal dexCarousel;
    private BigDecimal dexRecommend;
    private String dexPhone;
    private String dexSearch;
    private String dexRecord;
    private String dexLogo;
    private String dexEmail;
    private String dexUserPhone;
    private String dexCreateTime;
    private String dexBeginTime;
    private String dexState;
    private String dexRemarks;
}
