package cn.mejhwu.service.impl;

import cn.mejhwu.service.SensitivieService;
import org.apache.commons.lang3.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/24
 * Time:   17:51
 * Description:
 */

@Service
public class SensitiveServiceImpl implements SensitivieService, InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(SensitiveServiceImpl.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedInputStream = new BufferedReader(reader);
            String lineTxt;
            while ((lineTxt = bufferedInputStream.readLine()) != null) {
                addWord(lineTxt);
            }
        } catch (Exception e) {
            logger.error("读取敏感词文件失败: " + e.getMessage());
        }
    }

    //增加关键词
    private void addWord(String lineTxt) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < lineTxt.length(); ++i) {
            char c = lineTxt.charAt(i);

            TrieNode node = tempNode.getSubNode(c);
            if (node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;
            if (i == lineTxt.length() - 1) {
                tempNode.setKeyWordEnd(true);
            }
        }
    }

    private class TrieNode {

        //是不是关键词结尾
        boolean end = false;

        Map<Character, TrieNode> subNodes = new HashMap<Character, TrieNode>();
        void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }
        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }
        boolean isKeyWordEnd() {
            return end;
        }
        void setKeyWordEnd(boolean end) {
            this.end = end;
        }
    }

    private TrieNode rootNode = new TrieNode();


    private boolean isSymbol(char c) {
        int ic = (int) c;
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    @Override
    public String filter(String text) {
        if (StringUtils.isEmpty(text)) {
            return text;
        }


        StringBuilder sb = new StringBuilder();
        String replacement = "***";
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;

        while (position < text.length()) {
            char c = text.charAt(position);

            if (isSymbol(c)) {

                if (tempNode == rootNode) {
                    sb.append(c);
                    ++begin;
                }

                ++position;
                continue;
            }

            tempNode = tempNode.getSubNode(c);

            if (tempNode == null) {
                sb.append(text.charAt(position));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            } else if (tempNode.isKeyWordEnd()) {
                sb.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            } else {
                ++position;
            }

        }
        sb.append(text.substring(begin));
        return sb.toString();
    }

}
