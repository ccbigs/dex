package qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左定权
 * @date:日期:2021-07-20-时间:14:05
 * @message:订单表为主体的连表查询类，关联的表包括用户表，商品表，地址表，商家表，最后的订单表
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminOrderPojo {
    private Integer id;
    private Double  orders_totalprice;
    private Integer orders_number;
    private String logistics_status;
    private String orders_date;
    /*用户*/
    private String nickname;
    /*商品*/
    private String ware_name;
    private String ware_price;
    /*地址*/
    private String local;
    private String detail;
    private String phone;
    private String name;
    /*商家*/
    private String bus_name;
    private String bus_phone;
    private String bus_local;



    @Override
    public String toString() {
        return "qo.AdminOrderPojo{" +
                "id=" + id +
                ", orders_totalprice=" + orders_totalprice +
                ", orders_number=" + orders_number +
                ", logistics_status='" + logistics_status + '\'' +
                ", orders_date='" + orders_date + '\'' +
                ", nickname='" + nickname + '\'' +
                ", ware_name='" + ware_name + '\'' +
                ", ware_price='" + ware_price + '\'' +
                ", local='" + local + '\'' +
                ", detail='" + detail + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", bus_name='" + bus_name + '\'' +
                ", bus_phone='" + bus_phone + '\'' +
                ", bus_local='" + bus_local + '\'' +
                '}';
    }


}
