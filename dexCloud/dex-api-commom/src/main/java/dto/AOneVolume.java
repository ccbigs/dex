package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-09-06-时间:20:19
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AOneVolume {

    private Integer[] orders={0,0,0,0,0,0,0};
    private Integer[] bonusOrders={0,0,0,0,0,0,0};
    private BigDecimal[] ordersSale={new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)};
    private BigDecimal[] bonusOrdersSale={new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)};;

}
