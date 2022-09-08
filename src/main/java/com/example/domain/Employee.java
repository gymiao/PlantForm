package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工信息
 * </p>
 *
 * @author miaogy
 * @since 2022-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号
     */
    private String id_number;

    /**
     * 状态 0:禁用，1:正常
     */
    private Integer status;

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


}
