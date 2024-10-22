package qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-08-16-时间:16:36
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonusorderWareBonusPojo {
    private Integer id;
    private Integer buy_number;
    private BigDecimal bonus_totalprice;
    private String  add_name;
    private String  add_phone;
    private String  add_local;
    private String add_postal;
    private String  order_time;
    private BigDecimal ware_bonusmoney;
    private String order_state;

    private String ware_name;
    private Double ware_price;

    private BigDecimal bonus_money;
    private String begindate;

    /*商品得规格*/
    private String standards_name;
    private BigDecimal standards_price;
}
