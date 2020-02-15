import java.io.*;
import java.util.LinkedList;
import java.util.Queue;//for level traversal
import java.util.Random;
import java.util.Scanner;

class BBSTNode1 {
    int key;
    int height = 0;
    int size = 0; // the # pf nodes in the subtree rooted with this node
    BBSTNode1 father;
    BBSTNode1 left;
    BBSTNode1 right;

    BBSTNode1(int key){
        this.key = key;
    }


    int sizeLeft = 0;
    int sizeRight = 0;
}

public class Ex5 {
    static int[] a;
    static int[] n;
    static BBSTNode1 roott;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int m = input.nextInt();
        int k = input.nextInt();

        a = new int[m];
        n = new int[m - k + 1];
        for(int i = 0;i < m;i++){
            a[i] = input.nextInt();
        }
        for(int i = 0;i < m - k + 1;i++){
            n[i] = input.nextInt();
        }
        //read input

        roott = new BBSTNode1(a[0]);
        for(int i = 1;i < k;i++){
            insert(roott,a[i]);
        }
        System.out.println(findKthBiggest(roott,n[0]));
        delete(a[0]);
        //first k nodes

        for(int i = 1;i < m - k + 1;i++){
            if(roott == null){
                roott = new BBSTNode1(a[i + k - 1]);
            }else{
                insert(roott,a[i + k - 1]);
            }
            System.out.println(findKthBiggest(roott,n[i]));
            delete(a[i]);
        }
        //next m-k nodes
    }

//    public static void main(String[] args) throws IOException {
//        Reader input =new Reader();
//        PrintWriter out=new PrintWriter(System.out);
//        //FileReader reader = new FileReader("D:\\1.in");
//        //BufferedReader br = new BufferedReader(reader);
//        /*String line;
//        while ((line = br.readLine()) != null) {
//            // 一次读入一行数据
//            System.out.println(line);
//        }*/
//
//        int m = input.nextInt();
//        int k = input.nextInt();
//        //int m = 100000;
//        //int k = 5000;
//        java.util.Random r = new java.util.Random(99);
//        a = new int[m];
//        n = new int[m - k + 1];
//        for(int i = 0;i < m;i++){
//            Random rand = new Random();
//            //a[i] = r.nextInt(100000) + 1;
//            a[i] = input.nextInt();
//        }
//        for(int i = 0;i < m - k + 1;i++){
//            Random rand = new Random();
//            //n[i] = r.nextInt(k-2) + 1;
//            n[i] = input.nextInt();
//        }
//        //read input
//
//        roott = new BBSTNode1(a[0]);
//        for(int i = 1;i < k;i++){
//            insert(roott,a[i]);
//        }
//        out.println(findKthBiggest(roott,n[0]));
//        delete(a[0]);
//        //first k nodes
//
//        for(int i = 1; i < m - k + 1; i++){
//            if(i==i){
//                //System.out.println(i+"!");
//            }
//            if(roott == null){
//                roott = new BBSTNode1(a[i + k - 1]);
//            }else{
//                insert(roott,a[i + k - 1]);
//            }
//            out.println(findKthBiggest(roott,n[i]));
//            delete(a[i]);
//        }
//        //next m-k nodes
//        out.close();
//    }
//  助教的debug，随机数生成，读文件

    public static void insert(BBSTNode1 root,int key){
        if(key < root.key){
            //加入左子树
            if(root.left != null){
                insert(root.left,key);
            }else{
                BBSTNode1 tmp = new BBSTNode1(key);
                tmp.father = root;
                root.left = tmp;
                //root.sizeLeft++;

                maintain(tmp);
            }
            //若左儿子有值则递归，没值则加入
            //加入后maintain维护平衡
        }else{
            //加入右子树
            if(root.right != null){
                insert(root.right,key);
            }else{
                BBSTNode1 tmp = new BBSTNode1(key);
                tmp.father = root;
                root.right = tmp;
                //root.sizeRight++;

                maintain(tmp);
            }
            //若右儿子有值则递归，没值则加入
            //加入后maintain维护平衡
        }
    }

    public static void delete(int key){
        BBSTNode1 toDelete = predecessorQuery(roott,key,null);
        //predecessor query找到要删的字符

        if(toDelete.right == null && toDelete.left == null){
            //要删的点为leaf
            BBSTNode1 father = toDelete.father;

            if(father == null){
                roott = null;
                return;
            }
            //要删的点为root

            if(father.left == toDelete){
                father.left = null;
            }else if(father.right == toDelete){
                father.right = null;
            }
            //找到要删的点是它的father的左/右儿子

            maintain(father);
            //updateHeight_Size(father);
            //删除之后维护height与size
        }
        else if(toDelete.right != null){
            //要删的点有右子树

            BBSTNode1 suc = toDelete.right;
            while(suc.left != null){
                suc = suc.left;
            }
            //find successor

            BBSTNode1 father = suc.father;

            if(suc.right == null){
                //successor为叶子节点，则替换key，删除successor

                toDelete.key = suc.key;

                if(father.left == suc){
                    father.left = null;
                }else if(father.right == suc){
                    father.right = null;
                }
                //找到要删的点是它的father的左/右儿子

                maintain(father);
                //updateHeight_Size(father);
                //删除之后维护height与size
            }
            else{
                //则successor一定有右子树
                //替换key，用子树取代successor，注意更改father
                toDelete.key = suc.key;

                if(father.left == suc){
                    father.left = suc.right;
                    suc.right.father = father;
                }else if(father.right == suc){
                    father.right = suc.right;
                    suc.right.father = father;
                }
                //找到要删的点是它的father的左/右儿子

                maintain(suc.right);
                //updateHeight_Size(suc.right);
                //删除之后维护height与size
            }

        }
        else{
            //要删的点一定有左子树
            BBSTNode1 father = toDelete.father;

            if(father == null){
                roott = toDelete.left;
                toDelete.left.father = null;
                return;
            }
            //要删的点为root

            if(father.left == toDelete){
                father.left = toDelete.left;
                toDelete.left.father = father;
            }else if(father.right == toDelete){
                father.right = toDelete.left;
                toDelete.left.father = father;
            }
            //找到要删的点是它的father的左/右儿子

            maintain(toDelete.left);
            //updateHeight_Size(toDelete.left);
            //删除之后维护height与size
        }
    }

    public static BBSTNode1 predecessorQuery(BBSTNode1 root,int key,BBSTNode1 predecessor){
        if(root == null){
            return predecessor;
        }else if(root.key == key){
            predecessor = root;
            return predecessor;
        }else if(root.key > key){
            return predecessorQuery(root.left,key,predecessor);
        }else{
            predecessor = root;
            return predecessorQuery(root.right,key,predecessor);
        }
    }
    //return predecessor <= key

    public static void maintain(BBSTNode1 inserted){
        updateHeight_Size(inserted);

        while(inserted != null){
            int balance = checkBalance(inserted);
            //0 balance   >0 左高    <0 右高

            if(balance > 1 || balance < -1){
                if(balance > 1){
                    //LX
                    BBSTNode1 a = inserted;
                    BBSTNode1 b = inserted.left;

                    if(checkBalance(b) < 0){
                        //LR
                        BBSTNode1 c = b.right;
                        rotateRR(b);
                        rotateLL(a);

                        if(c.father == null){
                            roott = c;
                            break;
                        }
                        inserted = c.father;
                    }
                    else{
                        //LL
                        rotateLL(a);

                        if(b.father == null){
                            roott = b;
                            break;
                        }
                        inserted = b.father;
                    }
                }
                else{
                    //RX
                    BBSTNode1 a = inserted;
                    BBSTNode1 b = inserted.right;

                    if(checkBalance(b) <= 0){
                        //RR
                        rotateRR(a);

                        if(b.father == null){
                            roott = b;
                            break;
                        }
                        inserted = b.father;
                    }
                    else{
                        //RL
                        BBSTNode1 c = b.left;
                        rotateLL(b);
                        rotateRR(a);

                        if(c.father == null){
                            roott = c;
                            break;
                        }
                        inserted = c.father;
                    }
                }
            }
            else{
                if(inserted.father == null){
                    roott = inserted;
                    break;
                }
                inserted = inserted.father;
            }
        }
    }
    //维护平衡

    public static void rotateLL(BBSTNode1 root){ //
        BBSTNode1 a = root;
        BBSTNode1 b = root.left;
        BBSTNode1 father = a.father;

        a.left = b.right;
        if(b.right != null){
            b.right.father = a;
        }

        b.right = a;
        b.father = father;
        if(father != null){
            if(father.right == a){
                father.right = b;
            }else{
                father.left = b;
            }
        }
        a.father = b;

        updateHeight_Size(a);
    }
    //旋转LL型树

    public static void rotateRR(BBSTNode1 root){  //root为较高的节点
        BBSTNode1 a = root;
        BBSTNode1 b = root.right;
        BBSTNode1 father = a.father;

        a.right = b.left;
        if(b.left != null){
            b.left.father = a;
        }

        b.left = a;
        b.father = father;
        if(father != null){
            if(father.left == a){
                father.left = b;
            }else{
                father.right = b;
            }
        }
        a.father = b;

        updateHeight_Size(a);
    }
    //旋转RR型树

    public static int checkBalance(BBSTNode1 root){
        int leftHeight;
        int rightHeight;

        if(root.left == null){
            leftHeight = 0;
        }else{
            leftHeight = root.left.height + 1;
        }

        if(root.right == null){
            rightHeight = 0;
        }else{
            rightHeight = root.right.height + 1;
        }

        return leftHeight - rightHeight;

        //difference = Math.abs(rightHeight - leftHeight);
    }
    //返回左子树高度减右子树高度的差

    public static void updateHeight_Size(BBSTNode1 root){
        while(root != null){
            int leftHeight;
            int rightHeight;

            if(root.left == null){
                leftHeight = 0;
            }else{
                leftHeight = root.left.height + 1;
            }
            if(root.right == null){
                rightHeight = 0;
            }else{
                rightHeight = root.right.height + 1;
            }
            root.height = Math.max(leftHeight,rightHeight);
            //height

            int leftSize;
            int rightSize;

            if(root.left == null){
                leftSize = 0;
            }else{
                leftSize = root.left.size;
            }
            if(root.right == null){
                rightSize = 0;
            }else{
                rightSize = root.right.size;
            }
            root.size = leftSize + rightSize + 1;
            //size

            root = root.father;
        }
    }
    //加入删除后改变高度与size

    public static int findKthBiggest(BBSTNode1 root, int k){
        int leftSize;

        if(root.left == null){
            leftSize = 1;
        }else{
            leftSize = root.left.size + 1;
        }

        if(k == leftSize){
            return root.key;
        }else if(k > leftSize){
            return findKthBiggest(root.right,k - leftSize);
        }else{
            return findKthBiggest(root.left,k);
        }
    }




    public static void delete_2(BBSTNode1 root,int key){
        if(key > root.key){
            delete_2(root.right,key);
        }else if(key < root.key){
            delete_2(root.left,key);
        }else{
            BBSTNode1 father = root.father;

            if(father == null){
                roott = null;
                return;
            }

            if(root.left == null && root.right == null){
                if(father.left != null && father.left == root){
                    father.left = null;
                }else if(father.right != null && father.right == root){
                    father.right = null;
                }

                updateHeight_Size(father);
            }else if(root.left == null){
                if(father.left != null && father.left == root){
                    father.left = root.right;
                }else if(father.right != null && father.right == root){
                    father.right = root.right;
                }

                updateHeight_Size(father);
            }else if(root.right == null){
                if(father.left != null && father.left == root){
                    father.left = root.left;
                }else if(father.right != null && father.right == root){
                    father.right = root.left;
                }

                updateHeight_Size(father);
            }else{
                BBSTNode1 tmp = root.right;
                while(tmp.left != null){
                    tmp = tmp.left;
                }
                root.key = tmp.key;
                delete_2(root.right,tmp.key);
            }
        }
    }

    public static void insert_2(BBSTNode1 root,int key){
        if(root == null){
            root = new BBSTNode1(key);
            //root.size++;

            maintain(root);
            updateHeight_Size(root);
        }else{
            //root.size++;
            if(key > root.key){
                insert_2(root.right,key);
            }else if(key < root.key){
                insert_2(root.left,key);
            }
        }
    }

    public static BBSTNode1 successorQuery(BBSTNode1 root,int key,BBSTNode1 successor){
        if(root == null){
            return successor;
        }
        else if(root.key == key){
            successor = root;
            return successor;
        }
        else if(root.key > key){
            return successorQuery(root.right,key,successor);
        }else{
            successor = root;
            return successorQuery(root.left,key,successor);
        }
    }
    //return successor > key

    public static void print_Level(int n) {
        BBSTNode1[] levelTraverse = new BBSTNode1[50];
        Queue<BBSTNode1> queue = new LinkedList<>();

        int cnt = 1;
        levelTraverse[1] = roott;
        queue.offer(roott);

        while (!queue.isEmpty()) {
            BBSTNode1 tmp = queue.poll();
            if (tmp.left != null) {
                levelTraverse[cnt * 2] = tmp.left;
                queue.offer(tmp.left);
            }
            if (tmp.right != null) {
                levelTraverse[cnt * 2 + 1] = tmp.right;
                queue.offer(tmp.right);
            }
            cnt++;
        }

        for (int i = 1; i < n + 1; i++) {
            if(levelTraverse[i] == null){
                continue;
            }
            System.out.print(levelTraverse[i].key + " ");

        }
        System.out.println();
    }//level traversal

    public static void print(BBSTNode1 root){
        if(root.left != null){
            print(root.left);
        }
        System.out.print(root.key + " ");
        if(root.right != null){
            print(root.right);
        }
    }//inorder traversal
}





