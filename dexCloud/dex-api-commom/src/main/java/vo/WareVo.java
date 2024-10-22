package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-08-13-时间:15:33
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WareVo {
    private int id;
    private int busman_id;
    private String ware_name;
    private int ware_totalnumber;
    private int ware_number;
    private String ware_describe;
    private String ware_connect;
    private double ware_price;
    private String ware_img1;
    private String ware_img2;
    private String ware_img3;
    private String ware_img4;
    private String ware_img5;
    private String ware_state;
    private String ware_active;
    private String ware_date;
    private String categorie_name;
    private Integer categorie_id;

    private Double ware_activeDiscount;
    private BigDecimal ware_activeFullReduction;
    private BigDecimal ware_activeFullPrice;

    /*新增*/
    private Integer address_id;
    private Integer user_id;
    private Integer buy_numbers;

    /*继续新增*/
    private String standard_name;

    @Override
    public String toString() {
        return "WareVo{" +
                "id=" + id +
                ", busman_id=" + busman_id +
                ", ware_name='" + ware_name + '\'' +
                ", ware_totalnumber=" + ware_totalnumber +
                ", ware_number=" + ware_number +
                ", ware_describe='" + ware_describe + '\'' +
                ", ware_connect='" + ware_connect + '\'' +
                ", ware_price=" + ware_price +
                ", ware_img1='" + ware_img1 + '\'' +
                ", ware_img2='" + ware_img2 + '\'' +
                ", ware_img3='" + ware_img3 + '\'' +
                ", ware_img4='" + ware_img4 + '\'' +
                ", ware_img5='" + ware_img5 + '\'' +
                ", ware_state='" + ware_state + '\'' +
                ", ware_active='" + ware_active + '\'' +
                ", ware_date='" + ware_date + '\'' +
                ", categorie_name='" + categorie_name + '\'' +
                ", categorie_id=" + categorie_id +
                ", ware_activeDiscount=" + ware_activeDiscount +
                ", ware_activeFullReduction=" + ware_activeFullReduction +
                ", ware_activeFullPrice=" + ware_activeFullPrice +
                ", address_id=" + address_id +
                ", user_id=" + user_id +
                ", buy_numbers=" + buy_numbers +
                '}';
    }
}
