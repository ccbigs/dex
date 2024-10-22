package com.dex.serviceImpl;

import com.dex.dao.CategorieDao;
import com.dex.service.CategorieService;
import domain.categoriePojo;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:16:32
 * @message:
 */
@Service
public class CategorieServiceImpl implements CategorieService {

    @Resource
    private CategorieDao categorieDao;

    @Override
    public List<categoriePojo> adminGetAllCategorie() throws ParseException {
        List<categoriePojo> categories = categorieDao.adminGetAllCategorie();

        for (categoriePojo category : categories) {
            category.setCategorie_create_time(getTimeNow.dataBasicToYMDHMS(category.getCategorie_create_time()));
        }

        return categories;
    }

    @Override
    public int adminAddCategorie(categoriePojo categorie) {
        return categorieDao.adminAddCategorie(categorie);
    }

    @Override
    public int adminEditCategorie(categoriePojo categorie) {
        return categorieDao.adminEditCategorie(categorie);
    }

    @Override
    public int adminDeleteCategorie(int categorie_id) {
        return categorieDao.adminDeleteCategorie(categorie_id);
    }

}
