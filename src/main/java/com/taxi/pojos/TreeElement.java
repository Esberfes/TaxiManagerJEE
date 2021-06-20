package com.taxi.pojos;

import org.primefaces.model.TreeNode;

public interface TreeElement extends TreeNode {
    TreeElement buildTree(TreeNode parent);
}
