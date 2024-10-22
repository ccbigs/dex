package qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-08-17-时间:14:55
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonusBonusOrderPojo {
    private Integer id;
    private Integer ware_id;
    private Integer user_id;
    private String  result_url;
    private String  begindate;
    private String  enddate;
    private BigDecimal bonus_money;
    private String  bonus_state;

    private Integer bonus_id;
    private Integer sale_number;
    private BigDecimal ware_bonusmoney;
}
