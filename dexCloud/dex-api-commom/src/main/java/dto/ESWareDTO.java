package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author:左泽林
 * @date:日期:2021-09-09-时间:10:11
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "ware")
public class ESWareDTO {
    @Id
    private int id;
    @Field(type = FieldType.Integer)
    private int busman_id;
    @Field(type = FieldType.Text)
    private String ware_name;
    @Field(type = FieldType.Double)
    private int ware_totalnumber;
    @Field(type = FieldType.Integer)
    private int ware_number;
    @Field(type = FieldType.Text)
    private String ware_describe;
    @Field(type = FieldType.Text)
    private String ware_connect;
    @Field(type = FieldType.Double)
    private double ware_price;
    @Field(type = FieldType.Text)
    private String ware_img1;
    @Field(type = FieldType.Text)
    private String ware_img2;
    @Field(type = FieldType.Text)
    private String ware_img3;
    @Field(type = FieldType.Text)
    private String ware_img4;
    @Field(type = FieldType.Text)
    private String ware_img5;
    @Field(type = FieldType.Text)
    private String ware_state;
    @Field(type = FieldType.Text)
    private String ware_active;
    @Field(type = FieldType.Date)
    private Date ware_date;
    @Field(type = FieldType.Text)
    private String categorie_name;
    @Field(type = FieldType.Integer)
    private Integer categorie_id;

    @Field(type = FieldType.Double)
    private Double ware_activeDiscount;
    @Field(type = FieldType.Double)
    private BigDecimal ware_activeFullReduction;
    @Field(type = FieldType.Double)
    private BigDecimal ware_activeFullPrice;

    @Field(type = FieldType.Text)
    private String ware_bonus;
    @Field(type = FieldType.Double)
    private BigDecimal ware_bonusmoney;

}
