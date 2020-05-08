package com.qa.system.utils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName SensitiveWordUtils
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/5/7
 * @Version 1.0
 **/
public class SensitiveWordUtils {
    /**
     * 最小匹配规则，如：敏感词库["中国","中国人"]，语句："我是中国人"，匹配结果：我是[中国]人
     */
    public static final int MinMatchTYpe = 1;
    /**
     * 最大匹配规则，如：敏感词库["中国","中国人"]，语句："我是中国人"，匹配结果：我是[中国人]
     */
    public static final int MaxMatchType = 2;
    /**
     * 敏感词集合
     */
    public static HashMap sensitiveWordMap;


    /**
    * @Author XuFengrui
    * @Description 初始化敏感词库，构建DFA算法模型
    * @Date 10:00 2020/5/7
    * @Param [sensitiveWordSet]
    * @return void
    **/
    public static synchronized void init(Set<String> sensitiveWordSet) {
        initSensitiveWordMap(sensitiveWordSet);
    }

    /**
    * @Author XuFengrui
    * @Description 初始化敏感词库，构建DFA算法模型
    * @Date 10:01 2020/5/7
    * @Param [sensitiveWordSet]
    * @return void
    **/
    private static void initSensitiveWordMap(Set<String> sensitiveWordSet) {

        //初始化敏感词容器，减少扩容操作
        sensitiveWordMap = new HashMap(sensitiveWordSet.size());

        String key;
        Map nowMap;
        Map<String, String> newWorMap;

        //迭代sensitiveWordSet
        Iterator<String> iterator = sensitiveWordSet.iterator();
        while (iterator.hasNext()) {
            //关键字
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                //转换成char型
                char keyChar = key.charAt(i);
                //库中获取关键字
                Object wordMap = nowMap.get(keyChar);
                //如果存在该key，直接赋值，用于下一个循环获取
                if (wordMap != null) {
                    nowMap = (Map) wordMap;
                } else {
                    //不存在，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<>();
                    //不是最后一个
                    newWorMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }
                if (i == key.length() - 1) {
                    //最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }

    /**
    * @Author XuFengrui
    * @Description 判断文字是否包含敏感字符,匹配规则 1：最小匹配规则，2：最大匹配规则
    * @Date 10:04 2020/5/7
    * @Param [txt, matchType]
    * @return boolean 若包含返回true，否则返回false
    **/
    public static boolean contains(String txt, int matchType) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            //判断是否包含敏感字符
            int matchFlag = checkSensitiveWord(txt, i, matchType);
            //大于0存在，返回true
            if (matchFlag > 0) {
                flag = true;
            }
        }
        return flag;

    }

    /**
    * @Author XuFengrui
    * @Description 判断文字是否包含敏感字符
    * @Date 10:05 2020/5/7
    * @Param [txt]
    * @return boolean
    **/
    public static boolean contains(String txt) {
        return contains(txt, MaxMatchType);
    }

    /**
    * @Author XuFengrui
    * @Description 获取文字中的敏感词,匹配规则 1：最小匹配规则，2：最大匹配规则
    * @Date 10:06 2020/5/7
    * @Param [txt, matchType]
    * @return java.util.Set<java.lang.String>
    **/
    public static Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<>();
        for (int i = 0; i < txt.length(); i++) {
            //判断是否包含敏感字符
            int length = checkSensitiveWord(txt, i, matchType);
            //存在,加入list中
            if (length > 0) {
                sensitiveWordList.add(txt.substring(i, i + length));
                //减1的原因，是因为for会自增
                i = i + length - 1;
            }
        }
        return sensitiveWordList;
    }

    /**
    * @Author XuFengrui
    * @Description 获取文字中的敏感词
    * @Date 10:07 2020/5/7
    * @Param [txt]
    * @return java.util.Set<java.lang.String>
    **/
    public static Set<String> getSensitiveWord(String txt) {
        return getSensitiveWord(txt, MaxMatchType);
    }

    /**
    * @Author XuFengrui
    * @Description 替换敏感字字符
    * @Date 10:07 2020/5/7
    * @Param [txt, replaceChar, matchType]
    * @return java.lang.String
    **/
    public static String replaceSensitiveWord(String txt, char replaceChar, int matchType) {
        String resultTxt = txt;
        //获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word;
        String replaceString;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }
        return resultTxt;
    }


    /**
    * @Author XuFengrui
    * @Description 替换敏感字字符,匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符：*，替换结果：我爱***
    * @Date 10:10 2020/5/7
    * @Param [txt, replaceChar]
    * @return java.lang.String
    **/
    public static String replaceSensitiveWord(String txt, char replaceChar) {
        return replaceSensitiveWord(txt, replaceChar, MaxMatchType);
    }

    /**
    * @Author XuFengrui
    * @Description 替换的字符串，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符串：[屏蔽]，替换结果：我爱[屏蔽]
    * @Date 10:10 2020/5/7
    * @Param [txt, replaceStr, matchType]
    * @return java.lang.String
    **/
    public static String replaceSensitiveWord(String txt, String replaceStr, int matchType) {
        String resultTxt = txt;
        //获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word;
        while (iterator.hasNext()) {
            word = iterator.next();
            resultTxt = resultTxt.replaceAll(word, replaceStr);
        }
        return resultTxt;
    }

    /**
    * @Author XuFengrui
    * @Description 替换的字符串
    * @Date 10:12 2020/5/7
    * @Param [txt, replaceStr]
    * @return java.lang.String
    **/
    public static String replaceSensitiveWord(String txt, String replaceStr) {
        return replaceSensitiveWord(txt, replaceStr, MaxMatchType);
    }

    /**
    * @Author XuFengrui
    * @Description 获取替换字符串
    * @Date 10:13 2020/5/7
    * @Param [replaceChar, length]
    * @return java.lang.String
    **/
    private static String getReplaceChars(char replaceChar, int length) {
        String resultReplace = String.valueOf(replaceChar);
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }
        return resultReplace;
    }

    /**
    * @Author XuFengrui
    * @Description 检查文字中是否包含敏感字符
    * @Date 10:13 2020/5/7
    * @Param [txt, beginIndex, matchType]
    * @return int 如果存在，则返回敏感词字符的长度，不存在返回0
    **/
    private static int checkSensitiveWord(String txt, int beginIndex, int matchType) {
        //敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;
        //匹配标识数默认为0
        int matchFlag = 0;
        char word;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            //获取指定key
            nowMap = (Map) nowMap.get(word);
            //存在，则判断是否为最后一个
            if (nowMap != null) {
                //找到相应key，匹配标识+1
                matchFlag++;
                //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))) {
                    //结束标志位为true
                    flag = true;
                    //最小规则，直接返回,最大规则还需继续查找
                    if (MinMatchTYpe == matchType) {
                        break;
                    }
                }
            } else {//不存在，直接返回
                break;
            }
        }
        //长度必须大于等于1，为词
        if (matchFlag < 2 || !flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }

    /**
    * @Author XuFengrui
    * @Description 敏感词替换工具方法（对外方法）
    * @Date 10:15 2020/5/7
    * @Param [string]
    * @return java.lang.String
    **/
    @SuppressWarnings("unchecked")
    public static String sensitiveHelper(String string) throws Exception {
        Set<String> sensitiveWordSet = null;
        sensitiveWordSet = ReadTxt.readFile("D:\\敏感词库（UTF-8）\\敏感词库.txt");
        //初始化敏感词库
        SensitiveWordUtils.init(sensitiveWordSet);
        //判断是否包含敏感词
        if (contains(string)){
            //若包含返回替换后的字符
            String str = SensitiveWordUtils.replaceSensitiveWord(string, "**");
            return str;
        }
        //不包含返回原本字符
        return string;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws Exception {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        //获取前端传递的所有参数名的枚举
        Enumeration pNames = request.getParameterNames();
        //遍历枚举
        while(pNames.hasMoreElements()){
            //获取参数名
            String name=(String)pNames.nextElement();
            //获取参数值
            String value =request.getParameter(name);
            //对参数值进行敏感词处理,并重新设置到request
            String str = SensitiveWordUtils.sensitiveHelper(value);
            request.setAttribute(name,str);
        }
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }
}

