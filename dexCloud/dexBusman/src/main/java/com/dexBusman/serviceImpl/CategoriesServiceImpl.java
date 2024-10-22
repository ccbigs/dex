package com.dexBusman.serviceImpl;

import com.dexBusman.dao.CategoriesDao;
import com.dexBusman.service.CategoriesService;
import domain.categoriePojo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:19:53
 * @message:
 */
@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Resource
    private CategoriesDao categoriesDao;

    @Override
    public List<categoriePojo> getAllCategorie() {
        return categoriesDao.getAllCategorie();
    }
}
