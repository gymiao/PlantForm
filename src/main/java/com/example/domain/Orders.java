package com.example.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author miaogy
 * @since 2022-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单号
     */
    private String number;

    /**
     * 订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
     */
    private Integer status;

    /**
     * 下单用户
     */
    private Long user_id;

    /**
     * 地址id
     */
    private Long address_book_id;

    /**
     * 下单时间
     */
    private LocalDateTime order_time;

    /**
     * 结账时间
     */
    private LocalDateTime checkout_time;

    /**
     * 支付方式 1微信,2支付宝
     */
    private Integer pay_method;

    /**
     * 实收金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    private String phone;

    private String address;

    private String user_name;

    private String consignee;


}
