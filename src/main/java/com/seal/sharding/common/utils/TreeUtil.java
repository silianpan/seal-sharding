package com.seal.sharding.common.utils;

import com.seal.sharding.common.vo.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: hsc
 * @date: 2018/8/17 17:25
 * @description
 */
public class TreeUtil {

    /**
     * 将 List 转化为tree 型结构数据
     *
     * @return java.utils.List<T>
     * @description
     * @author: hsc
     * @date: 2018/9/13 23:41
     */
    public static <T extends TreeNode> List<T> bulid(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        String  r = String.format("%s",root);
        for (T treeNode : treeNodes) {
            String pid = String.format("%s",treeNode.getParentId());
            String cid = String.format("%s",treeNode.getId());
            if (r.equals(pid)) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                String cId = String.format("%s",it.getParentId());
                if (cId.equals(cid)) {
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

}
