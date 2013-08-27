package org.roger.study.ExClient.Protocol.WBTree;

/**
 * Created with IntelliJ IDEA.
 * User: next
 * Date: 13-8-23
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */
public class NodeData {
    private byte cmd;
    private byte pageId;
    private byte[] valule;

    public NodeData(byte cmd, byte pageId)  {
        this.cmd = cmd;
        this.pageId = pageId;
    }

    public void setValue(byte[] valule)  {
        this.valule = valule;
    }

    public byte[] Valule() {
        return valule;
    }

    public byte cmd() {
        return cmd;
    }

    public byte PageId() {
        return pageId;
    }
}
