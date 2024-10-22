package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ordersPojo {
    private Integer id;
    private Integer user_id;
    private Integer busman_id;
    private Integer ware_id;
    private Integer address_id;
    private BigDecimal orders_totalprice;
    private Integer orders_number;
    private String logistics_status;
    private String orders_date;
    private String active_type;
    private Double active_detail;
    private Double active_reduction;

    /*2021-08-23新增*/
    private String standard_name;
    private BigDecimal unit_price;

    /*商品发货的快递单号*/
    private String order_couriernumber;

}
