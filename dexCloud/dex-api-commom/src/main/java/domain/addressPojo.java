package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class addressPojo {
    private int id;
    private int user_id;
    private String local;
    private String detail;
    private String phone;
    private String name;
    private String postal;


    @Override
    public String toString() {
        return "address{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", local='" + local + '\'' +
                ", detail='" + detail + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", postal='" + postal + '\'' +
                '}';
    }


}
