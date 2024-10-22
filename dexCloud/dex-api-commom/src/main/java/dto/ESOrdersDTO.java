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
 * @date:日期:2021-09-09-时间:14:19
 * @message:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(indexName = "orders")
public class ESOrdersDTO {
    @Id
    private Integer id;

    @Field(type = FieldType.Integer)
    private Integer user_id;
    @Field(type = FieldType.Integer)
    private Integer busman_id;
    @Field(type = FieldType.Integer)
    private Integer ware_id;
    @Field(type = FieldType.Integer)
    private Integer address_id;

    @Field(type = FieldType.Double)
    private BigDecimal orders_totalprice;

    @Field(type = FieldType.Integer)
    private Integer orders_number;

    @Field(type = FieldType.Text)
    private String logistics_status;

    @Field(type = FieldType.Date)
    private Date orders_date;

    @Field(type = FieldType.Text)
    private String active_type;

    @Field(type = FieldType.Double)
    private Double active_detail;
    @Field(type = FieldType.Double)
    private Double active_reduction;

    /*2021-08-23新增*/
    @Field(type = FieldType.Text)
    private String standard_name;
    @Field(type = FieldType.Double)
    private BigDecimal unit_price;


    /*商品发货的快递单号*/
    @Field(type = FieldType.Text)
    private String order_couriernumber;
}
