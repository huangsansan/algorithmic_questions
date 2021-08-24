package chenjicunM08;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 递归
 * （DFS深度优先搜索、前中后序二叉树遍历）
 * 判断一个问题是否可以用递归解决
 *      1.一个问题可以分解为几个子问题的解
 *      2.分解的子问题除了数据规模不同，求解思路完全一样
 *      3.存在递归终止条件
 *
 * 要注意的点： 堆栈溢出、重复计算
 * 递归也可用循环迭代来实现
 */
public class Recursion {


    /**
     * 二叉树的前序遍历
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> integers = new ArrayList<>();
        Stack<TreeNode> treeNodes = new Stack<>();
        if (root != null) treeNodes.push(root);
        while (!treeNodes.isEmpty()) {
            root = treeNodes.pop();
            integers.add(root.val);
            if (root.right != null) treeNodes.push(root.right);
            if (root.left != null) treeNodes.push(root.left);
        }
        return integers;
    }
    /**
     * 二叉树的中序遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> treeNodes = new Stack<>();
        List<Integer> integers = new ArrayList<>();
        while (!treeNodes.isEmpty() || root != null ) {
            if (root != null) {
                treeNodes.push(root);
                root = root.left;
            } else {
                root = treeNodes.pop();
                integers.add(root.val);
                root = root.right;
            }
        }
        return integers;
    }

    /**
     * 二叉树的后序遍历
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> integers = new LinkedList<>();
        Stack<TreeNode> treeNodes = new Stack<>();
        if (root != null) treeNodes.push(root);
        while ( !treeNodes.isEmpty()) {
            root = treeNodes.pop();
            integers.push(root.val);
            if (root.left != null) treeNodes.push(root.left);
            if (root.right != null) treeNodes.push(root.right);
        }
        return integers;
    }
}
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
