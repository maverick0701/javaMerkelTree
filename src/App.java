import java.util.ArrayList;
import java.util.Scanner;
public class App {
    public static void main(String[] args) throws Exception {
        ArrayList<MerkelNode>data=new ArrayList<>();
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter The No of Data Block You Want To Store");
        int n=sc.nextInt();MerkelNode lastNode=new MerkelNode();
        for(int i=0;i<n;i++)
        {
            String str=sc.next();
            MerkelNode nd=new MerkelNode(str);
            data.add(nd);
            lastNode=nd;
        }
        if(data.size()%2!=0)
        {
            
            data.add(lastNode);
            
        }
        
        System.out.print("Start Processing"+data.size());
        MerkelNode tree=new MerkelNode();MerkelNode head=tree.BuildTree(data);
        System.out.println("Size of Merel Tree is "+MerkelNode.size(head));
        MerkelNode.inorder(head);
        if(head.doesExsists(data.get(0).hashText, head))
        {
            System.out.println("Yes the node Exsists");
        }
        else
        {
            System.out.println("The node doesnot exsist");
        }
    }
}
