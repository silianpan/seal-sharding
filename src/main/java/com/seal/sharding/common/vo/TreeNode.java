package com.seal.sharding.common.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hsc
 * @date 2017/6/12
 */
@Data
public class TreeNode implements Serializable {
    private static final long serialVersionUID = 5180056021489867883L;
    protected Object id;
    protected Object parentId;
    List<TreeNode> children;

    public void add(TreeNode node) {
        if (children == null) {
            children = Lists.newArrayList();
        }
        children.add(node);
    }
}
