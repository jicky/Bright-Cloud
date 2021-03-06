package com.tml.server.system.controller;

import com.tml.api.system.entity.GatewayBlockListLog;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.system.service.IGatewayBlockListLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 黑名单日志 Controller
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:31
 */
@Slf4j
@Validated
@RestController
@RequestMapping("gatewayBlockListLog")
@RequiredArgsConstructor
public class GatewayBlockListLogController {

    private final IGatewayBlockListLogService gatewayBlockListLogService;

    @GetMapping("list")
    @PreAuthorize("hasAuthority('gatewayBlockListLog:list')")
    public CommonResult gatewayBlockListLogList(QueryRequest request, GatewayBlockListLog gatewayBlockListLog) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.gatewayBlockListLogService.pageGatewayBlockListLog(request, gatewayBlockListLog));
        return new CommonResult().data(dataTable);
    }

    @DeleteMapping("{ids}")
    @PreAuthorize("hasAuthority('gatewayBlockListLog:delete')")
    public void deleteGatewayBlackListLog(@NotBlank(message = "{required}") @PathVariable String ids) throws BrightException {
        try {
            String[] idArray = ids.split(StringConstant.COMMA);
            this.gatewayBlockListLogService.deleteGatewayBlockListLog(idArray);
        } catch (Exception e) {
            String message = "删除GatewayBlackListLog失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }
}
