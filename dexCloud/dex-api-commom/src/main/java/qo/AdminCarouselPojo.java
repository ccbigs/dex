package qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左定权
 * @date:日期:2021-07-20-时间:19:59
 * @message:实体对象，接收轮播图的返回参数
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCarouselPojo {

    private int id;
    private String img;
    private int ware_id;
    private int busman_id;
    private String createDate;
    private String endDate;
    private String carousel_state;
    private int quotient_order_id;
    private String type_time;

    private String ware_name;
    private String bus_name;


    @Override
    public String toString() {
        return "qo.AdminCarouselPojo{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", ware_id=" + ware_id +
                ", busman_id=" + busman_id +
                ", createDate='" + createDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", carousel_state='" + carousel_state + '\'' +
                ", quotient_order_id=" + quotient_order_id +
                ", type_time='" + type_time + '\'' +
                ", ware_name='" + ware_name + '\'' +
                ", bus_name='" + bus_name + '\'' +
                '}';
    }

}
