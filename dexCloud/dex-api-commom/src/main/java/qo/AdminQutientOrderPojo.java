package qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左定权
 * @date:日期:2021-07-20-时间:16:52
 * @message:作为管理员中的订单信息管理存储的数据对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminQutientOrderPojo {

    private Integer id;

    private Double pay_price;
    private String order_time;
    private Integer month;
    private String order_type;

    private String busman_id;
    private String bus_name;

    private Integer ware_id;
    private String ware_name;


    @Override
    public String toString() {
        return "qo.AdminQutientOrderPojo{" +
                "id=" + id +
                ", pay_price=" + pay_price +
                ", order_time='" + order_time + '\'' +
                ", month=" + month +
                ", order_type='" + order_type + '\'' +
                ", busman_id='" + busman_id + '\'' +
                ", bus_name='" + bus_name + '\'' +
                ", ware_id=" + ware_id +
                ", ware_name='" + ware_name + '\'' +
                '}';
    }

}
