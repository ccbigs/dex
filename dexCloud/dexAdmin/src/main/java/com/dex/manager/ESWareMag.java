package com.dex.manager;


import dto.ESWareDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:左泽林
 * @date:日期:2021-09-09-时间:10:24
 * @message:
 */
@Repository
public interface ESWareMag extends ElasticsearchRepository<ESWareDTO,Long> {
}
