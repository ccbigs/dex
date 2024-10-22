package dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VThreeVolume {
    private Integer orderNumber;
    private Double allSale;
    private Integer listings;
    private Integer allSaleNumber;
    private Integer code;
    private Integer size;
    private String message;

}
