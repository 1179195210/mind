package com.ruoyi.web.controller.mind.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class Solution2 {
    Queue<TreeNode> queue = new LinkedList<>();

    public TreeNode trimBST(TreeNode root, int low, int high) {
        queue.add(root);
        List result = new ArrayList<>();
        recursive(Arrays.asList(new Integer[]{1,2,3,4,5}),new ArrayList<>(), 3, result);
        System.out.println(result);
        return null;
    }
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        double[] avgWage =  new double[quality.length];
        for (int i = 0; i < quality.length; i++) {
            double avgWageSingle = Double.valueOf(wage[i])/ Double.valueOf(quality[i]);
            avgWage[i] = avgWageSingle;
        }
        List<List<Integer>> result = new ArrayList<>();
        Integer[] kArr = new Integer[quality.length];
        for (int i = 0; i < quality.length; i++) {
            kArr[i] = i;
        }
        recursive(Arrays.asList(kArr), new ArrayList<>(), k, result);
        List<Double> doubleList = new ArrayList<>();

        for (List<Integer> one : result) {
            double wageMax = 0;
            int all = 0;
            for (Integer a : one) {
                if (wageMax < avgWage[a]) {
                    wageMax = avgWage[a];
                }
                all += quality[a];
            }
            double resultNum = all * wageMax;

            doubleList.add(resultNum);
        }
        Double max = Collections.min(doubleList);
        return  max;

    }

    List<List<Integer>> interList = new ArrayList<>();
    public static void recursive(List<Integer> candidate, List<Integer> prefix, Integer size, List<List<Integer>> resList){
        if(size == 0) {
            if(prefix.size() != 0) {
                System.out.println(prefix);
                resList.add(prefix);
            }

        } else if(prefix.size() == size){
            System.out.println(prefix);
            resList.add(prefix);
            return;
        }

        for(int i=0; i<candidate.size(); i++){
            List<Integer> temp = new LinkedList<Integer>(candidate);
            int item = (int)temp.remove(i);  // 取出被删除的元素，这个元素当作一个组合用掉了

            //去重保留一种  例如12和21为同一种  123 132 321位同一种
            for(int k = i; k > 0; k--) {//注释即为全排列
                temp.remove(k-1);
            }
            prefix.add(item);
            recursive(temp, prefix, size, resList);
            prefix = new ArrayList<>();

        }
    }

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
//        solution2.trimBST(null, 0 ,0);
        solution2.mincostToHireWorkers(new int[]{3,1,10,10,1}, new int[]{4,8,2,2,7}, 3);

    }
}
