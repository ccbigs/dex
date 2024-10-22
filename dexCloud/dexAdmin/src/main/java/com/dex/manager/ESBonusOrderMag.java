package com.dex.manager;

import dto.ESBonusOrderDTO;
import dto.ESWareDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author:左泽林
 * @date:日期:2021-09-09-时间:14:37
 * @message:
 */
public interface ESBonusOrderMag extends ElasticsearchRepository<ESBonusOrderDTO,Long> {
}
