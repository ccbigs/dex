package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-09-08-时间:9:58
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HOneVolume {
    private String[] name = {"","","","",""};
    private BigDecimal[] value={new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)};
}
