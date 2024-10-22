package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左定权
 * @date:日期:2021-07-17-时间:16:41
 * @message:这个是第四张地图的数据对象
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VFourVolume  {
    private String name;
    private double value=0.0;

    @Override
    public String toString() {
        return "VFourVolume{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

}
