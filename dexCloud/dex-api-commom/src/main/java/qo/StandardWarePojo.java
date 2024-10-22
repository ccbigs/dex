package qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-09-11-时间:17:00
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardWarePojo {
    private Integer id;
    private Integer ware_id;
    private Integer busman_id;
    private String  standards_name;
    private Integer standards_number;
    private Integer standards_total;
    private BigDecimal standards_price;
    private String  create_time;

    private String ware_name;
}
