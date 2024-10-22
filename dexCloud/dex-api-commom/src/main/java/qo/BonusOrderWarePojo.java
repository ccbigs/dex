package qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-09-10-时间:15:39
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonusOrderWarePojo {
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

    private String busman_local;
    private String busman_phone;
    private String busman_name;

    /*秒杀订单快递单号*/
    private String bonusorder_couriernumber;


    private String ware_name;
    private double ware_price;
    private String ware_img1;
}
