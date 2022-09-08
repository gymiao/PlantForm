package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 地址管理
 * </p>
 *
 * @author miaogy
 * @since 2022-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Address_book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long user_id;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 性别 0 女 1 男
     */
    private Integer sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 省级区划编号
     */
    private String province_code;

    /**
     * 省级名称
     */
    private String province_name;

    /**
     * 市级区划编号
     */
    private String city_code;

    /**
     * 市级名称
     */
    private String city_name;

    /**
     * 区级区划编号
     */
    private String district_code;

    /**
     * 区级名称
     */
    private String district_name;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 标签
     */
    private String label;

    /**
     * 默认 0 否 1是
     */
    private Boolean is_default;

    /**
     * 创建时间
     */
    private LocalDateTime create_time;

    /**
     * 更新时间
     */
    private LocalDateTime update_time;

    /**
     * 创建人
     */
    private Long create_user;

    /**
     * 修改人
     */
    private Long update_user;

    /**
     * 是否删除
     */
    private Integer is_deleted;


}
