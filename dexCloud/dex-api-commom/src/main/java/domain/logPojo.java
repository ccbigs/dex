package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:17:02
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class logPojo {
    private Integer id;
    private Integer user_id;
    private String  user;
    private String  behavior;
    private String  user_type;
    private String  time;
    private String  InternetProtocol;

    @Override
    public String toString() {
        return "logPojo{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", user='" + user + '\'' +
                ", behavior='" + behavior + '\'' +
                ", user_type='" + user_type + '\'' +
                ", time='" + time + '\'' +
                ", InternetProtocol='" + InternetProtocol + '\'' +
                '}';
    }
}
