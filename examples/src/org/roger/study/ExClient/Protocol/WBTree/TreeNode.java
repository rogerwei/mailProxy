package org.roger.study.ExClient.Protocol.WBTree;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-23
 * Time: 上午10:36
 * To change this template use File | Settings | File Templates.
 */
public class TreeNode {
    private TreeNode parent;
    private ArrayList<TreeNode> children =null;
    private NodeData data;

    public TreeNode(TreeNode parent, NodeData data) {
        this.parent = parent;
        this.data = data;
    }

    public void addChild(TreeNode child)  {
        if (children == null)
            children = new ArrayList<TreeNode>();
        children.add(child);
    }

    public ArrayList<TreeNode> getChildren()  {
        return children;
    }

    public boolean isRoot()  {
        return parent == null;
    }

    public boolean isLeaf()  {
        return (children == null || children.size() == 0);
    }
    public TreeNode getParent() {
        return parent;  //To change body of created methods use File | Settings | File Templates.
    }

    public NodeData data() {
        return data;
    }
}
