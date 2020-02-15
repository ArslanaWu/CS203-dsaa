//判断第一个是哪种，确定第一个的种类之后，之后的如果同种就加，异种就拿出来
//如果删到空，就再判断下一个是什么种类
//找predecessor和successor，先判断他们是否为空，再与value求差。
//差不同选小的，差相同选predecessor

import java.util.Scanner;

class BBSTNode2{
    long value;
    long kind;
    long height = 0;
    long size = 0; // the # pf nodes in the subtree rooted with this node

    BBSTNode2 father;
    BBSTNode2 left;
    BBSTNode2 right;

    BBSTNode2(long value, long kind){
        this.value = value;
        this.kind = kind;
    }
}

public class Ex6 {
    static BBSTNode2 roott;
    static long cost = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        //int n = 80000;
        //int a = 0;

        for(int i = 0; i < n; i++){

            java.util.Random r = new java.util.Random();
            long kind = input.nextLong();
            long value = input.nextLong();
            //long value = r.nextInt(214748364) + 1;
            //long kind = Math.random()> 0.5 ? 1:0;
            //a = i;

            //System.out.println(i);
            //System.out.println(kind +" " + value);



            if(roott == null){
                roott = new BBSTNode2(value,kind);
            }else{
                if(roott.kind == kind){
                    insert(roott,value,kind);
                }else{
                    calculateCost_delete(value);
                }
            }
        }
        //System.out.println(a);
        System.out.println(cost);
    }

    public static void calculateCost_delete(long value){
        BBSTNode2 pre = predecessorQuery(roott,value,null);
        BBSTNode2 suc = successorQuery(roott,value,null);

        if(pre == null && suc != null){
            cost = cost + Math.abs(suc.value - value);
            delete(suc.value);
        }else if(pre != null && suc == null){
            cost = cost + Math.abs(value - pre.value);
            delete(pre.value);
        }else if(pre != null){
            long diffPre = Math.abs(value - pre.value);
            long diffSuc = Math.abs(suc.value - value);

            if(diffPre <= diffSuc){
                cost = cost + diffPre;
                delete(pre.value);
            }else{
                cost = cost + diffSuc;
                delete(suc.value);
            }
        }
    }

    public static void insert(BBSTNode2 root, long value, long kind){
        if(value < root.value){
            //加入左子树
            if(root.left != null){
                insert(root.left,value,kind);
            }else{
                BBSTNode2 tmp = new BBSTNode2(value,kind);
                tmp.father = root;
                root.left = tmp;

                maintain(tmp);
            }
            //若左儿子有值则递归，没值则加入
            //加入后maintain维护平衡
        }else{
            //加入右子树
            if(root.right != null){
                insert(root.right,value,kind);
            }else{
                BBSTNode2 tmp = new BBSTNode2(value,kind);
                tmp.father = root;
                root.right = tmp;

                maintain(tmp);
            }
            //若右儿子有值则递归，没值则加入
            //加入后maintain维护平衡
        }
    }

    public static void delete(long value){
        BBSTNode2 toDelete = predecessorQuery(roott,value,null);
        //predecessor query找到要删的字符

        if(toDelete.right == null && toDelete.left == null){
            //要删的点为leaf
            BBSTNode2 father = toDelete.father;

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

            //updateHeight_Size(father);
            maintain(father);
            //删除之后维护height与size
        }
        else if(toDelete.right != null){
            //要删的点有右子树

            BBSTNode2 suc = toDelete.right;
            while(suc.left != null){
                suc = suc.left;
            }
            //find successor

            BBSTNode2 father = suc.father;

            if(suc.right == null){
                //successor为叶子节点，则替换key，删除successor

                toDelete.value = suc.value;

                if(father.left == suc){
                    father.left = null;
                }else if(father.right == suc){
                    father.right = null;
                }
                //找到要删的点是它的father的左/右儿子

                //updateHeight_Size(father);
                maintain(father);
                //删除之后维护height与size
            }else{
                //则successor一定有右子树
                //替换key，用子树取代successor，注意更改father
                toDelete.value = suc.value;

                if(father.left == suc){
                    father.left = suc.right;
                    suc.right.father = father;
                }else if(father.right == suc){
                    father.right = suc.right;
                    suc.right.father = father;
                }
                //找到要删的点是它的father的左/右儿子

                //updateHeight_Size(suc.right);
                maintain(suc.right);
                //删除之后维护height与size
            }

        }
        else{
            //要删的点一定有左子树
            BBSTNode2 father = toDelete.father;

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

            //updateHeight_Size(toDelete.left);
            maintain(toDelete.left);
            //删除之后维护height与size
        }
    }

    public static BBSTNode2 predecessorQuery(BBSTNode2 root, long value, BBSTNode2 predecessor){
        if(root == null){
            return predecessor;
        }else if(root.value == value){
            predecessor = root;
            return predecessor;
        }else if(root.value > value){
            return predecessorQuery(root.left, value,predecessor);
        }else{
            predecessor = root;
            return predecessorQuery(root.right, value,predecessor);
        }
    }
    //return predecessor <= key

    public static void maintain(BBSTNode2 inserted){
        updateHeight_Size(inserted);

        while(inserted != null){
            long balance = checkBalance(inserted);
            //0 balance   >0 左高    <0 右高

            if(balance > 1 || balance < -1){
                if(balance > 1){
                    //LX
                    BBSTNode2 a = inserted;
                    BBSTNode2 b = inserted.left;

                    if(checkBalance(b) < 0){
                        //LR
                        BBSTNode2 c = b.right;
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
                    BBSTNode2 a = inserted;
                    BBSTNode2 b = inserted.right;

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
                        BBSTNode2 c = b.left;
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

    public static void rotateLL(BBSTNode2 root){ //
        BBSTNode2 a = root;
        BBSTNode2 b = root.left;
        BBSTNode2 father = a.father;

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

    public static void rotateRR(BBSTNode2 root){  //root为较高的节点
        BBSTNode2 a = root;
        BBSTNode2 b = root.right;
        BBSTNode2 father = a.father;

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

    public static long checkBalance(BBSTNode2 root){
        long leftHeight;
        long rightHeight;

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

    public static void updateHeight_Size(BBSTNode2 root){
        while(root != null){
            long leftHeight;
            long rightHeight;

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

            long leftSize;
            long rightSize;

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

    public static BBSTNode2 successorQuery(BBSTNode2 root, long value, BBSTNode2 successor){
        if(root == null){
            return successor;
        }else if(root.value == value){
            successor = root;
            return successor;
        }else if(root.value < value){
            return successorQuery(root.right, value,successor);
        }else{
            successor = root;
            return successorQuery(root.left, value,successor);
        }
    }
    //return successor > key
}