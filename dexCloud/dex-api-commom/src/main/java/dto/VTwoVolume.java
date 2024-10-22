package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-09-07-时间:19:32
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VTwoVolume {
    private String name;
    private BigDecimal value;
}
