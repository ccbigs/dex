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
 * @date:日期:2021-09-09-时间:14:08
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "bonusorder")
public class ESBonusOrderDTO {
    @Id
    private Integer id;
    @Field(type = FieldType.Integer)
    private Integer bonus_id;
    @Field(type = FieldType.Integer)
    private Integer user_id;
    @Field(type = FieldType.Integer)
    private Integer busman_id;
    @Field(type = FieldType.Integer)
    private Integer ware_id;
    @Field(type = FieldType.Integer)
    private Integer standard_id;
    @Field(type = FieldType.Integer)
    private Integer buy_number;

    @Field(type = FieldType.Double)
    private BigDecimal bonus_totalprice;

    @Field(type = FieldType.Text)
    private String  add_name;
    @Field(type = FieldType.Text)
    private String  add_phone;
    @Field(type = FieldType.Text)
    private String  add_local;
    @Field(type = FieldType.Text)
    private String add_postal;

    @Field(type = FieldType.Date)
    private Date order_time;

    @Field(type = FieldType.Double)
    private BigDecimal ware_bonusmoney;

    @Field(type = FieldType.Text)
    private String order_state;

    @Field(type = FieldType.Text)
    private String busman_local;
    @Field(type = FieldType.Text)
    private String busman_phone;
    @Field(type = FieldType.Text)
    private String busman_name;

    @Field(type = FieldType.Text)
    /*秒杀订单快递单号*/
    private String bonusorder_couriernumber;
}
