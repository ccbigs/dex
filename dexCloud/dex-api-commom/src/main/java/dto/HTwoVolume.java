package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左泽林
 * @date:日期:2021-09-08-时间:14:45
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HTwoVolume {
    private Long[] userActive={0L,0L,0L,0L,0L,0L,0L};
    private Long[] busmanActive={0L,0L,0L,0L,0L,0L,0L};
    private Long[] touristActive={0L,0L,0L,0L,0L,0L,0L};
}
