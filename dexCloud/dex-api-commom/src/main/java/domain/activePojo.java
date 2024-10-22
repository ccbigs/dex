package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class activePojo {
    private int id;
    private int busman_id;
    private int ware_id;
    private double discount_detail;
    private double full_reduction;
    private double full_price;
    private String type;

    @Override
    public String toString() {
        return "domain.activePojo{" +
                "id=" + id +
                ", busman_id=" + busman_id +
                ", ware_id=" + ware_id +
                ", discount_detail=" + discount_detail +
                ", full_reduction=" + full_reduction +
                ", full_price=" + full_price +
                ", type='" + type + '\'' +
                '}';
    }

}
