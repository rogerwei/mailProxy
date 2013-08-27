package org.roger.study.ExClient.Protocol.WBTree;

import java.util.ArrayList;

import static org.roger.study.ExClient.Util.GetData.subBytes;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-23
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class WbXMLTree {
    private byte[] wbXml;
    private int length;
    private TreeNode root = null;

    public WbXMLTree(byte[] wbXml) {
        this.wbXml = wbXml;
        length = wbXml.length;
    }

    public TreeNode init()  {
        if (root != null)
            return root;
        root = new TreeNode(null, null);
        if (length > 0)  {
            build(root, 0x4, (byte)0x0);
        }

        return root;
    }

    public byte[] value(byte pageId, byte cmd)  {
        TreeNode node= searchNode(root, pageId,cmd);

        return node == null? null:node.data().Valule();
    }

    public boolean isThisRoot(byte pageId, byte cmd) {
        TreeNode rootXml = XmlRoot();
        if (rootXml == null)  {
            return false;
        }

        return (rootXml.data().cmd() == cmd && rootXml.data().PageId() == pageId);
    }

    public byte rootPageId()  {
        TreeNode rootXml = XmlRoot();
        if (rootXml == null)  {
            System.err.println("get root Page Id occur error.");
            return -1;
        }

        return rootXml.data().PageId();
    }

    private TreeNode XmlRoot()  {
        if (root == null || root.getChildren().size() != 1)  {
            System.err.println("get XML root occur error.");
            return null;
        }

        return  root.getChildren().get(0);
    }

    public byte[] value(byte parentCmd, byte pageId, byte childCmd)  {
        TreeNode upNode= searchNode(root, pageId, parentCmd);
        TreeNode node= searchNode(upNode, pageId, childCmd);

        return node == null? null:node.data().Valule();
    }

    private TreeNode searchNode(TreeNode node, byte pageId, byte cmd) {
        if (node == null)
            return null;

        ArrayList<TreeNode>  children= node.getChildren();

        //check this
        if (node.data() != null)  {
            if (node.data().cmd() == cmd && node.data().PageId() == (byte)(pageId & 0xff))
                return node;
        }

        //check children
        if (children == null)
            return null;
        for(TreeNode n: children)  {
            TreeNode child = searchNode(n, pageId, cmd);
            if (child != null)
                return child;
        }

        return null;
    }

    private void build(TreeNode parent, int start, byte pageId) {
        if (start > length)
            return;
        TreeNode newParent = null;
        boolean isEndFather = isEndFather(parent);

        //occur switch page
        if (wbXml[start] == 0x0)  {
            pageId = wbXml[++start];
            start++;
        }

        //new data, input cmd and the id of page
        NodeData data = new NodeData(wbXml[start], pageId);
        TreeNode node = new TreeNode(parent, data);

        //set value of data
        if (wbXml[++start] == 0x3)  { //has data
            int end = ++start;  //move to the start pos of data.
            while (wbXml[end] != 0x0 && wbXml[end +1] != 0x1 && (end +1) < length) //move to the end of data
                end++;
            data.setValue(subBytes(wbXml, start, end - start));

            //move to the end symbol
            start = end + 1;
            if (isEndFather)   //esc the end of symbol
                start ++;
        }else
            data.setValue(null);

        // insert child node.
        parent.addChild(node);

       //for next cmd if need
        newParent = isEndFather ? parent: node;
        while (wbXml[start] == 0x1 && (start + 1) < length && !newParent.isRoot()) {
            newParent = newParent.getParent();
            start++;
        }

        if (start +1 >= length)  //the end
            return;

        if (!newParent.isRoot())  {
            build(newParent, start, pageId);
        }
    }

    private boolean isEndFather(TreeNode parent) {
        if (parent != null && parent.data() != null)  {
            return EndFather.isEndFather(parent.data().PageId(), parent.data().cmd());
        }
        return false;  //To change body of created methods use File | Settings | File Templates.
    }
}
