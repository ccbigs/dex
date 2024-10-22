package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左泽林
 * @date:日期:2021-08-18-时间:16:49
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class infoPojo {
    private Integer id;
    private String  title;
    private String  subtitle;
    private String  content;
    private String  auther;
    private String  public_time;
    private Integer approve_number;
    private Integer view_number;
    private String  title_type;
}
