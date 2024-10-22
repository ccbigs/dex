package qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左定权
 * @date:日期:2021-07-20-时间:20:50
 * @message:管理提供推荐的商品信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRecommendPojo {

    private Integer id;
    private Integer ware_id;
    private Integer busman_id;
    private String createdate;
    private String enddate;
    private String recommend_state;
    private Integer quotient_order_id;
    private String type_time;

    private String bus_name;
    private String ware_name;




    @Override
    public String toString() {
        return "qo.AdminRecommendPojo{" +
                "id=" + id +
                ", ware_id=" + ware_id +
                ", busman_id=" + busman_id +
                ", createdate='" + createdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", recommend_state='" + recommend_state + '\'' +
                ", quotient_order_id=" + quotient_order_id +
                ", type_time='" + type_time + '\'' +
                ", bus_name='" + bus_name + '\'' +
                ", ware_name='" + ware_name + '\'' +
                '}';
    }


}
