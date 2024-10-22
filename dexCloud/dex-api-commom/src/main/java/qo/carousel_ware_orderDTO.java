package qo;
/*作用域商人查看自己的轮播图的数据对象*/


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class carousel_ware_orderDTO {
    private int id;
    private String img;
    private int ware_id;
    private int busman_id;
    private String createDate;
    private String endDate;
    private String carousel_state;
    private String ware_name;
    private String ware_img1;
    private double pay_price;
    private String order_time;
    private int month;
    private String order_type;
    private String type_time;

    private int quotient_order_id;


    @Override
    public String toString() {
        return "carousel_ware_orderDTO{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", ware_id=" + ware_id +
                ", busman_id=" + busman_id +
                ", createDate='" + createDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", carousel_state='" + carousel_state + '\'' +
                ", ware_name='" + ware_name + '\'' +
                ", ware_img1='" + ware_img1 + '\'' +
                ", pay_price=" + pay_price +
                ", order_time='" + order_time + '\'' +
                ", month=" + month +
                ", order_type='" + order_type + '\'' +
                ", type_time='" + type_time + '\'' +
                ", quotient_order_id=" + quotient_order_id +
                '}';
    }
}
