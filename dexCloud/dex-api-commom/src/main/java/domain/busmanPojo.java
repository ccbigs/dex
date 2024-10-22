package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*商家Pojo*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class busmanPojo {
    private int id;
    private String bus_account;
    private String bus_password;
    private String bus_phone;
    private String bus_name;
    private String bus_idcard;
    private String bus_busman_state;
    private String bus_register_time;
    private String bus_lasttime;
    private String bus_local;


    @Override
    public String toString() {
        return "domain.busmanPojo{" +
                "id=" + id +
                ", bus_account='" + bus_account + '\'' +
                ", bus_password='" + bus_password + '\'' +
                ", bus_phone='" + bus_phone + '\'' +
                ", bus_name='" + bus_name + '\'' +
                ", bus_idcard='" + bus_idcard + '\'' +
                ", bus_busman_state='" + bus_busman_state + '\'' +
                ", bus_register_time='" + bus_register_time + '\'' +
                ", bus_lasttime='" + bus_lasttime + '\'' +
                ", bus_local='" + bus_local + '\'' +
                '}';
    }

}
