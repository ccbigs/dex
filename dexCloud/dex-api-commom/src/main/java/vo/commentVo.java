package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class commentVo {
    private int id;
    private int user_id;
    private int orders_id;
    private int ware_id;
    private String user_nickname;
    private String user_headimage;
    private int comment_rate;
    private String comment_connect;
    private String comment_date;
    private ArrayList<String> comment_image;

    @Override
    public String toString() {
        return "commentVo{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", orders_id=" + orders_id +
                ", ware_id=" + ware_id +
                ", user_nickname='" + user_nickname + '\'' +
                ", user_headimage='" + user_headimage + '\'' +
                ", comment_rate='" + comment_rate + '\'' +
                ", comment_connect='" + comment_connect + '\'' +
                ", comment_date='" + comment_date + '\'' +
                ", comment_image=" + comment_image +
                '}';
    }



}
