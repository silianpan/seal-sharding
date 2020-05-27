package com.seal.sharding.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: hsc
 * @date: 2018/7/25 16:49
 * @description
 */
@Data
public class BaseModel implements Serializable {
    /**
     * id
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    protected Object id;

    @ApiModelProperty("创建人")
    @TableField(value = "crt_user", fill = FieldFill.INSERT)
    protected Object crtUser;

    @ApiModelProperty("修改人")
    @TableField(value = "upd_user", fill = FieldFill.UPDATE)
    protected Object updUser;

    /**
     * 删除标识 逻辑删除字段
     */
    @ApiModelProperty("删除标识")
    @TableField("del_flag")
    @TableLogic
    protected Boolean delFlag;

    /**
     * 创建日期
     */
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(value = "crt_time", fill = FieldFill.INSERT)
    protected LocalDateTime crtTime;

    /**
     * 修改日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty("修改时间")
    @TableField(value = "upd_time", fill = FieldFill.UPDATE)
    protected LocalDateTime updTime;

    @ApiModelProperty("版本")
    @Version
    protected Integer version;

}
