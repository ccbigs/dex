package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotientOrderPojo {
    private int id;
    private int busman_id;
    private double pay_price;
    private String order_time;
    private int month;
    private String order_type;
    private int ware_id;

    @Override
    public String toString() {
        return "domain.QuotientOrderPojo{" +
                "id=" + id +
                ", busman_id=" + busman_id +
                ", pay_price=" + pay_price +
                ", order_time='" + order_time + '\'' +
                ", month=" + month +
                ", order_type='" + order_type + '\'' +
                ", ware_id=" + ware_id +
                '}';
    }

}
