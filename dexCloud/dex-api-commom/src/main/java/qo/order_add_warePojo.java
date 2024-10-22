package qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class order_add_warePojo {
    private int id;
    private int user_id;
    private int busman_id;
    private int ware_id;
    private int address_id;
    private double orders_totalprice;
    private int orders_number;
    private BigDecimal unit_price;
    private String logistics_status;
    private String orders_date;
    private String local;
    private String detail;
    private String phone;
    private String name;
    private String postal;
    private String ware_name;
    private double ware_price;
    private String ware_img1;
    private String order_couriernumber;

    /*商品规格*/
    private String standard_name;
}
