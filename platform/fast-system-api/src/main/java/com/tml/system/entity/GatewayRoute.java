package com.tml.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 网关动态路由
 * @Author TuMingLong
 * @Date 2020/5/10 16:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("gateway_route")
public class GatewayRoute extends Model<GatewayRoute> {

    /**
     * 路由ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long routeId;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 路径
     */
    private String path;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 完整地址
     */
    private String url;

    /**
     * 忽略前缀
     */
    private Integer stripPrefix;

    /**
     * 0-不重试 1-重试
     */
    private Integer retryable;

    /**
     * 状态:0-无效 1-有效
     */
    private Integer status;

    /**
     * 保留数据0-否 1-是 不允许删除
     */
    private Integer isPersist;

    /**
     * 路由说明
     */
    private String routeDesc;
}