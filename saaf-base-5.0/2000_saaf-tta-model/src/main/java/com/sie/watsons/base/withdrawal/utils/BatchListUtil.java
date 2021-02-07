package com.sie.watsons.base.withdrawal.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hmb
 * @date 2019/10/10 16:39
 * 把集合拆分成多个集合
 */
public class BatchListUtil<E> {
    /**
     * 把list分成多个批次
     * @param list 集合
     * @param batchSize 批次大小
     * @return Map<Integer,List<E>>
     */
    public Map<Integer, List<E>> batchList(List<E> list, int batchSize){
        Map<Integer,List<E>> itemMap = new HashMap<>();
        itemMap.put(1, new ArrayList<E>());
        for(E e : list){
            List<E> batchList= itemMap.get(itemMap.size());
            if(batchList.size() == batchSize){//当list满足批次数量，新建一个list存放后面的数据
                batchList = new ArrayList<E>();
                itemMap.put(itemMap.size()+1, batchList);
            }
            batchList.add(e);
        }
        return itemMap;
    }

    /*
     * 分割集合为多个集合
     */
    public static List<List<String>> groupList(List<String> list) {
        List<List<String>> listGroup = new ArrayList<List<String>>();
        int listSize = list.size();
        //子集合的长度
        int toIndex = 2;
        for (int i = 0; i < list.size(); i += 2) {
            if (i + 2 > listSize) {
                toIndex = listSize - i;
            }
            List<String> newList = list.subList(i, i + toIndex);
            listGroup.add(newList);
        }
        return listGroup;
    }


    /**
     * 把list分成多个批次
     * @param list
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String,List<String>> groupList(List<String> list,int batchSize){

        int listSize=list.size();
        int toIndex=batchSize;
        Map<String,List<String>> map = new HashMap();     //用map存起来新的分组后数据
        int keyToken = 0;
        for(int i = 0;i<list.size();i+=batchSize){
            if(i+batchSize>listSize){        //作用为toIndex最后没有500条数据则剩余几条newList中就装几条
                toIndex=listSize-i;
            }
            List<String> newList = list.subList(i,i+toIndex);
            map.put("batchName"+keyToken, newList);
            keyToken++;
        }

        return map;
    }


    //测试
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        if(list!=null && list.size()>0){
            Map<Integer,List<Object>> itemMap = new BatchListUtil<Object>().batchList(list, 500);
            //分批次更新
            for(Integer i : itemMap.keySet()){
                //objectMapper.insertAll(itemMap.get(i));
            }
        }
        System.out.println("本次插入共用时:"+ (System.currentTimeMillis()-start) );
    }

}
