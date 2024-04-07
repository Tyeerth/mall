package com.xdu;

import java.util.*;

/**
 * @Author ${USER}
 * @Date ${DATE} ${TIME}
 * @Description
 */
public class Main {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> hashMap = new HashMap<>();
        for(String str : strs){
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String s = String.valueOf(chars);
            if (hashMap.containsKey(s)){
                List<String> strings = hashMap.get(s);
                strings.add(str);
                hashMap.put(s,strings);
            }
//            hashMap.put(s,new ArrayList<>());
            hashMap.pu
        }
        List<List<String>> list = new ArrayList();
        for (List<String> value : hashMap.values()) {
            list.add(value);
        }
        return list;
    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}