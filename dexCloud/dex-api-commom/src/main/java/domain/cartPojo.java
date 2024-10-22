package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class cartPojo {

    private int id;
    private int busman_id;
    private int ware_id;
    private String ware_name;
    private int ware_number;
    private String ware_describe;
    private String ware_connect;
    private double ware_price;
    private String ware_img1;
    private int cart_number;
    private int user_id;
    /*商品规格*/
    private Integer standard_id;
    private String standards_name;
    /*活动*/
    private double ware_activeDiscount;
    private BigDecimal ware_activeFullPrice;
    private BigDecimal ware_activeFullReduction;
    private String ware_active;

}
