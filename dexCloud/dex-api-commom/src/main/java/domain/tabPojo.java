package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:左泽林
 * @date:日期:2021-10-08-时间:15:51
 * @message:
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class tabPojo {
    private Integer id;
    private String tabName;
    private Integer infoId;
    private String tabType;
    private String tabChain;
    private String tabCreatetime;
}
