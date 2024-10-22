package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class userPojo {
    private int id;
    private String account;
    private String password;
    private String idcard;
    private String phone;
    private String email;
    private String birthday;
    private String nickname;
    private String headimage;
    private String area;
    private String sex;
    private String realname;
    private String user_state;

    @Override
    public String toString() {
        return "domain.userPojo{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", idcard='" + idcard + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headimage='" + headimage + '\'' +
                ", area='" + area + '\'' +
                ", sex='" + sex + '\'' +
                ", realname='" + realname + '\'' +
                ", user_state='" + user_state + '\'' +
                '}';
    }



}
