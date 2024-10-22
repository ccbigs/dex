package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-08-23-时间:9:41
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class standardsPojo {
    private Integer id;
    private Integer ware_id;
    private Integer busman_id;
    private String  standards_name;
    private Integer standards_number;
    private Integer standards_total;
    private BigDecimal standards_price;
    private String  create_time;

}
