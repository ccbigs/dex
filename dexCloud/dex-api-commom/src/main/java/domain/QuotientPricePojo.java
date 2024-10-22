package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotientPricePojo {
    private int id;
    private double carousel_price;
    private double recommend_price;

    @Override
    public String toString() {
        return "domain.QuotientPricePojo{" +
                "id=" + id +
                ", carousel_price=" + carousel_price +
                ", recommend_price=" + recommend_price +
                '}';
    }


}
