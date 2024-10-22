package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class carouselPojo {
    private int id;
    private String img;
    private int ware_id;
    private int busman_id;
    private String createDate;
    private String endDate;
    private String carousel_state;
    private int quotient_order_id;
    private String type_time;



    @Override
    public String toString() {
        return "domain.carouselPojo{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", ware_id=" + ware_id +
                ", busman_id=" + busman_id +
                ", createDate='" + createDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", carousel_state='" + carousel_state + '\'' +
                ", quotient_order_id=" + quotient_order_id +
                ", type_time='" + type_time + '\'' +
                '}';
    }


}
