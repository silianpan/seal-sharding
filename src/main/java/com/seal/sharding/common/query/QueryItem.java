package com.seal.sharding.common.query;

import com.seal.sharding.common.kit.StrKit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * @author: panliu
 * @date: 2018/8/8 17:02
 * @description
 */
@Data
@ApiModel(value = "参数条件", description = "参数对象")
public class QueryItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @NonNull
    @ApiModelProperty(value = "查询类型", name = "queryType", example = "like")
    private String queryType = "like";

    @NonNull
    @ApiModelProperty(value = "字段名", name = "field", example = "name")
    private String field;

    @NonNull
    @ApiModelProperty(value = "字段值", name = "value", example = "value")
    private Object value;


    public String getField() {
        return StrKit.camel2Underline(this.field).toLowerCase();
    }
}
