package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class phimgPojo {

    private int id;
    private String img_url;
    private String img_type;

    @Override
    public String toString() {
        return "domain.phimgPojo{" +
                "id=" + id +
                ", img_url='" + img_url + '\'' +
                ", img_type='" + img_type + '\'' +
                '}';
    }

}
