package com.dexUser.manager;

import dto.ESOrdersDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author:左泽林
 * @date:日期:2021-09-10-时间:11:01
 * @message:
 */
public interface ESOrderMag extends ElasticsearchRepository<ESOrdersDTO,Long> {
}
