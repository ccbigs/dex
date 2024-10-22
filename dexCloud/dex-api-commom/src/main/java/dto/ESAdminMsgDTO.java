package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左泽林
 * @date:日期:2021-09-09-时间:17:31
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ESAdminMsgDTO {
    private String health;
    private String status;
    private String index;
    private String uuid;
    private Long count;
}
