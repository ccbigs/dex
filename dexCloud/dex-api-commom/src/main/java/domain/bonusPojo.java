package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-08-13-时间:17:07
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class bonusPojo {

    private Integer id;
    private Integer ware_id;
    private Integer user_id;
    private String  result_url;
    private String  begindate;
    private String  enddate;
    private BigDecimal bonus_money;
    private String  bonus_state;

    @Override
    public String toString() {
        return "bonusPojo{" +
                "id=" + id +
                ", ware_id=" + ware_id +
                ", user_id=" + user_id +
                ", result_url='" + result_url + '\'' +
                ", begindate='" + begindate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", bonus_money=" + bonus_money +
                ", bonus_state='" + bonus_state + '\'' +
                '}';
    }
}
