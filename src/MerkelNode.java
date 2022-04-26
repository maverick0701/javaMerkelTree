import javax.management.Query;
import java.util.*;
public class MerkelNode {
    MerkelNode left;
    MerkelNode right;
    String hashText;
    MerkelNode head;
    Queue<MerkelNode>merkelQueue;
    MerkelNode()
    {}
    public static int log2(int size)
    {
        int result = (int)(Math.log(size) / Math.log(2));
        return result;
    }
    MerkelNode(String data)
    {
        this.hashText=HashAlgo.encryptThisString(data);
        this.left=null;
        this.right=null;
        this.head=null;
    }   
    public void ProcessQueue(int parentQueueSize)
    {
        for(int j=0;j<parentQueueSize;j++)
            {
                MerkelNode ltNode=this.merkelQueue.poll();
                MerkelNode rtNode=this.merkelQueue.poll();
                String NewData=ltNode.hashText+rtNode.hashText;
                MerkelNode prNode=new MerkelNode(NewData);
                prNode.left=ltNode;
                prNode.right=rtNode;
                ltNode.head=prNode;
                rtNode.head=prNode;
                this.merkelQueue.add(prNode);
            }

    }
    public  MerkelNode BuildTree(ArrayList<MerkelNode> block)
    {
        this.merkelQueue= new LinkedList<>();
        block.forEach((MerkelNode node)->this.merkelQueue.add(node));
        while(this.merkelQueue.size()!=1)
        {
            int parentQueueSize=this.merkelQueue.size()/2;
            if(parentQueueSize!=1 && parentQueueSize%2==0)
            {
               ProcessQueue(parentQueueSize);
            }
            else if(parentQueueSize!=1 && parentQueueSize%2!=0)
            {
                int newParentQueueSize=parentQueueSize+1;
                // int numElem=(int)Math.pow((double)2,(double)newParentQueueSize);
                MerkelNode repeatNode=this.merkelQueue.peek();
                // while(this.merkelQueue.size()!=numElem)
                // {
                this.merkelQueue.add(repeatNode);
                // }
                ProcessQueue(newParentQueueSize);
            }
            else
            {
                ProcessQueue(parentQueueSize);
                this.merkelQueue.peek().head=this.merkelQueue.peek();
            }
        }
        return this.merkelQueue.peek();
    }
    public static int size(MerkelNode node)
    {
        if(node.left==null && node.right==null)
        {
            return 1;
        }
        return size(node.left) + size(node.right) +1;
    }
    public boolean verify(MerkelNode node)
    {
        if(node.head==node)
        {
            return true;
        }
        String newHash=HashAlgo.encryptThisString(node.left.hashText+node.head.right.hashText);    
        if(!newHash.equals(node.hashText))
            return true;
        return verify(node.head);
    }

    public boolean doesExsists(String node,MerkelNode root)
    {
        if(node==root.hashText)
            return true;
        if(root.left==null && root.right==null)
            return false;
        return doesExsists(node, root.head) || doesExsists(node, root.left);
    }

    public static void inorder(MerkelNode node)
    {
        System.out.println(node.hashText);
        if(node.left==null && node.right==null)
        {
            return;
        }
        inorder(node.left);
        inorder(node.right);
    }

}
