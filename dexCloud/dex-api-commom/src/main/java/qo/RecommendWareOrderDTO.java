package qo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendWareOrderDTO {
    private Integer id;
    private Integer ware_id;
    private Integer busman_id;
    private String createDate;
    private String endDate;
    private String recommend_state;
    private Integer quotient_order_id;
    private String ware_name;
    private String ware_img1;
    private Double pay_price;
    private String order_time;
    private Integer month;
    private String order_type;
    private String type_time;


    @Override
    public String toString() {
        return "RecommendWareOrderDTO{" +
                "id=" + id +
                ", ware_id=" + ware_id +
                ", busman_id=" + busman_id +
                ", createDate='" + createDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", recommend_state='" + recommend_state + '\'' +
                ", quotient_order_id=" + quotient_order_id +
                ", ware_name='" + ware_name + '\'' +
                ", ware_img1='" + ware_img1 + '\'' +
                ", pay_price=" + pay_price +
                ", order_time='" + order_time + '\'' +
                ", month=" + month +
                ", order_type='" + order_type + '\'' +
                ", type_time='" + type_time + '\'' +
                '}';
    }
}
