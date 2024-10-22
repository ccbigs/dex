package qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*商品和订单的连表*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ware_orderPojo {
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
    private Double totalSale;

    @Override
    public String toString() {
        return "qo.ware_orderPojo{" +
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
                ", totalSale=" + totalSale +
                '}';
    }



}
