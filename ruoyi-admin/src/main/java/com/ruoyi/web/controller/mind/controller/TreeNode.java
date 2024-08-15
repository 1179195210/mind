package com.ruoyi.web.controller.mind.controller;

import java.util.*;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, new TreeNode(2, new TreeNode(4, null, null), null), new TreeNode(3, new TreeNode(2, new TreeNode(4), null), new TreeNode(4)));
        Solution solution = new Solution();
        solution.findDuplicateSubtrees(root);
//        solution.reorderSpaces("  hello");
    }

    Map<String,Integer> map = new HashMap<>();
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> result = new ArrayList<>();
        calculate(root, result);
        return result;
    }

    public String calculate(TreeNode root, List<TreeNode> result){
        if(root == null)
            return "#";
        String s = root.val + " " + calculate(root.left,result) + " " + calculate(root.right,result);
        map.put(s,map.getOrDefault(s,0) + 1);
        if(map.get(s) == 2)
            result.add(root);
        return s;

    }
//public String reorderSpaces(String text) {
//    List<String> space = new ArrayList<>();
//    List<String> wordListNew = Arrays.stream(text.split("\\s+")).filter(s -> !s.equals("")).collect(Collectors.toList());
//    String yuspa = "";
//    for (int i = 0; i < text.length(); i++) {
//        if (" ".equals(text.substring(i, i + 1))) {
//            space.add(text.substring(i, i+1));
//            yuspa += text.substring(i, i+1);
//        }
//    }
//    if (wordListNew.size() == 1) {
//        if (space.size() != 0) {
//            return wordListNew.get(0) + yuspa;
//        }
//        return wordListNew.get(0);
//    }
//    int spaceNum = (space.size() / (wordListNew.size() - 1));
//    int yu = (space.size() % (wordListNew.size() - 1));;
//    String sp = "";
//    String yus = "";
//    for (int i = 0; i < spaceNum; i ++) {
//        sp += " ";
//    }
//    for (int i = 0; i < yu; i ++) {
//        yus += " ";
//    }
//    String result = "";
//    for (int i = 0; i < wordListNew.size(); i++) {
//        if (i + 1 == wordListNew.size()) {
//            result  = result + wordListNew.get(i) + yus;
//        } else {
//            result  = result + wordListNew.get(i) + sp;
//        }
//    }
//    return result;
}




