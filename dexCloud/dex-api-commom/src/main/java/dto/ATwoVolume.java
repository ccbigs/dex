package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-09-06-时间:19:05
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ATwoVolume {
    private BigDecimal value;
    private String name;
}
