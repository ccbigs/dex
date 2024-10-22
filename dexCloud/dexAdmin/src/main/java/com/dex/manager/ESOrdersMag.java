package com.dex.manager;

import dto.ESBonusOrderDTO;
import dto.ESOrdersDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author:左泽林
 * @date:日期:2021-09-09-时间:14:38
 * @message:
 */
public interface ESOrdersMag extends ElasticsearchRepository<ESOrdersDTO,Long> {
}
