package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左泽林
 * @date:日期:2021-08-13-时间:9:54
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVo {
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
    /*活动*/
    private double discount_detail;
    private double full_reduction;
    private double full_price;
    private String active_type;

    @Override
    public String toString() {
        return "CartVo{" +
                "id=" + id +
                ", busman_id=" + busman_id +
                ", ware_id=" + ware_id +
                ", ware_name='" + ware_name + '\'' +
                ", ware_number=" + ware_number +
                ", ware_describe='" + ware_describe + '\'' +
                ", ware_connect='" + ware_connect + '\'' +
                ", ware_price=" + ware_price +
                ", ware_img1='" + ware_img1 + '\'' +
                ", cart_number=" + cart_number +
                ", user_id=" + user_id +
                ", discount_detail=" + discount_detail +
                ", full_reduction=" + full_reduction +
                ", full_price=" + full_price +
                ", active_type='" + active_type + '\'' +
                '}';
    }
}
