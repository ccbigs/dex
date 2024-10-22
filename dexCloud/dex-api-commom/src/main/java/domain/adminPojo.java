package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左定权
 * @date:日期:2021-07-24-时间:11:55
 * @message:管理员的实体类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class adminPojo {
    private Integer id;
    private String account;
    private String password;
    private String admin_type;
    private String create_time;
    private String last_time;
    private String remarks;
    private String admin_state;


    @Override
    public String toString() {
        return "domain.adminPojo{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", admin_type='" + admin_type + '\'' +
                ", create_time='" + create_time + '\'' +
                ", last_time='" + last_time + '\'' +
                ", remarks='" + remarks + '\'' +
                ", admin_state='" + admin_state + '\'' +
                '}';
    }
}
