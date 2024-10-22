package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VSalesVolume {
    private Integer code;
    private Integer size;
    private String message;
    private Double[] sale1;
    private Double[] sale2;

    @Override
    public String toString() {
        return "VSalesVolume{" +
                "code=" + code +
                ", size=" + size +
                ", message='" + message + '\'' +
                ", sale1=" + Arrays.toString(sale1) +
                ", sale2=" + Arrays.toString(sale2) +
                '}';
    }

}
