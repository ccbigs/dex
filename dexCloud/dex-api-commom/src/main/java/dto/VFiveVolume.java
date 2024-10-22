package dto;

/*
 * @author: 左先生
 * @date: 2021-08-10 11:58
 * @description:：数据传输对象，Service 或 Manager 向外传输的对象,redis数据传输
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VFiveVolume {
    private Integer ware_id;
    private String ware_name;
    private Integer[] ware_number={0,0,0,0,0,0,0};


    @Override
    public String toString() {
        return "VFiveVolume{" +
                "ware_id=" + ware_id +
                ", ware_name='" + ware_name + '\'' +
                ", ware_number=" + Arrays.toString(ware_number) +
                '}';
    }
}
