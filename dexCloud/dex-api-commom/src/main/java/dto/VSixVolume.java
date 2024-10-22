package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左定权
 * @date:日期:2021-07-17-时间:16:02
 * @message:该类是传输给分析表盘的对象数据，是给第六个盘商品库存的数据,这里的数据模式切合前端饼图的数据模式
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VSixVolume {

    private Integer value;
    private String name;



    @Override
    public String toString() {
        return "VSixVolume{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }


}
