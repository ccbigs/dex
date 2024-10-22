package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class loginPojo {
    private String account;
    private String password;


    @Override
    public String toString() {
        return "domain.loginPojo{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
