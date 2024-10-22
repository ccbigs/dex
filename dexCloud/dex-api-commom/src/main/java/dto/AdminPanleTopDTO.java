package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:11:01
 * @message:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPanleTopDTO {
    private Integer AllUser;
    private Integer AllBusman;
    private Integer AllOrder;
    private Integer AllComment;
    private BigDecimal AllSumTurnover;
    private BigDecimal AllActuallyTurnover;
    private Integer LimitQutient;
    private BigDecimal AllQutientTurnover;

    @Override
    public String toString() {
        return "AdminPanleTopDTO{" +
                "AllUser=" + AllUser +
                ", AllBusman=" + AllBusman +
                ", AllOrder=" + AllOrder +
                ", AllComment=" + AllComment +
                ", AllSumTurnover=" + AllSumTurnover +
                ", AllActuallyTurnover=" + AllActuallyTurnover +
                ", LimitQutient=" + LimitQutient +
                ", AllQutientTurnover=" + AllQutientTurnover +
                '}';
    }
}
