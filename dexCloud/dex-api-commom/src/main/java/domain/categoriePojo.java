package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:左定权
 * @date:日期:2021-07-22-时间:11:39
 * @message:商品分类的数据模型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class categoriePojo {
    private Integer id;
    private String categorie_name;
    private String categorie_create_time;
    private Integer categorie_create_admin;

    @Override
    public String toString() {
        return "domain.categoriePojo{" +
                "id=" + id +
                ", categorie_name='" + categorie_name + '\'' +
                ", categorie_create_time='" + categorie_create_time + '\'' +
                ", categorie_create_admin=" + categorie_create_admin +
                '}';
    }


}
