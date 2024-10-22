package qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class comment_warePojo {
    private int id;
    private int user_id;
    private int orders_id;
    private int ware_id;
    private String user_nickname;
    private String user_headimage;
    private int comment_rate;
    private String comment_connect;
    private String comment_date;
    private String comment_img1;
    private String comment_img2;
    private String comment_img3;
    private String ware_name;
    private String ware_img1;



    @Override
    public String toString() {
        return "qo.comment_warePojo{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", orders_id=" + orders_id +
                ", ware_id=" + ware_id +
                ", user_nickname='" + user_nickname + '\'' +
                ", user_headimage='" + user_headimage + '\'' +
                ", comment_rate=" + comment_rate +
                ", comment_connect='" + comment_connect + '\'' +
                ", comment_date='" + comment_date + '\'' +
                ", comment_img1='" + comment_img1 + '\'' +
                ", comment_img2='" + comment_img2 + '\'' +
                ", comment_img3='" + comment_img3 + '\'' +
                ", ware_name='" + ware_name + '\'' +
                ", ware_img1='" + ware_img1 + '\'' +
                '}';
    }


}
