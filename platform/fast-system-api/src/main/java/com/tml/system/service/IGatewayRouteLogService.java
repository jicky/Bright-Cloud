package com.tml.system.service;

import com.tml.common.web.service.IBaseService;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayRouteLogDto;
import com.tml.system.entity.GatewayRouteLog;

/**
 * @Description 网关日志服务类接口
 * @Author TuMingLong
 * @Date 2020/5/10 16:48
 */
public interface IGatewayRouteLogService extends IBaseService<GatewayRouteLog> {

    PageVo<GatewayRouteLog> pageList(GatewayRouteLogDto gatewayRouteLogDto);
}
