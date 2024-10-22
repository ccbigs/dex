package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class phRecommendPojo {
    private Integer id;
    private Integer ware_id;
    private Integer busman_id;
    private String createdate;
    private String enddate;
    private String recommend_state;
    private int quotient_order_id;
    private String type_time;



    @Override
    public String toString() {
        return "domain.phRecommendPojo{" +
                "id=" + id +
                ", ware_id=" + ware_id +
                ", busman_id=" + busman_id +
                ", createdate='" + createdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", recommend_state='" + recommend_state + '\'' +
                ", quotient_order_id=" + quotient_order_id +
                ", type_time='" + type_time + '\'' +
                '}';
    }


}
