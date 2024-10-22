package vo;



import domain.carouselPojo;
import domain.warePojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class homeVo {
    private int code;
    private int size;
    private String message;
    private List<carouselPojo> carousel;
    private List<warePojo> hp_new;
    private List<warePojo> hp_hot;
    private List<warePojo> hp_recommend;


    @Override
    public String toString() {
        return "homeVo{" +
                "code=" + code +
                ", size=" + size +
                ", message='" + message + '\'' +
                ", carousel=" + carousel +
                ", hp_new=" + hp_new +
                ", hp_hot=" + hp_hot +
                ", hp_recommend=" + hp_recommend +
                '}';
    }

}
