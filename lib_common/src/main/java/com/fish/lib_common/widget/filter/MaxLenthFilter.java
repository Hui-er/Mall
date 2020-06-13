package com.fish.lib_common.widget.filter;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by  kuangbs on 2019-12-13.
 */
public class MaxLenthFilter implements InputFilter {
    private int maxLength;//字节数（1个中文占两个字节）
    private String regEx = "[\\u4e00-\\u9fa5]";
    /**
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
     */
    private static final char SBC_CHAR_START = 65281; // 全角！
    /**
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
     */
    private static final char SBC_CHAR_END = 65374; // 全角～


    public MaxLenthFilter(int maxLength) {
        super();
        this.maxLength = maxLength;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        if (maxLength == 0) return source.toString();
        int destCount = dest.toString().length() + getChineseCount(dest.toString()) + getSBCCharCount(dest.toString());
        int sourceCount = source.toString().length() + getChineseCount(source.toString()) + getSBCCharCount(source.toString());
        if (destCount + sourceCount > maxLength) {
            int surplusCount = maxLength - destCount;
            StringBuilder result = new StringBuilder();
            int index = 0;
            while (surplusCount > 0 && index < source.length()) {
                char c = source.charAt(index);
                if (isChinest(String.valueOf(c)) || (c >= SBC_CHAR_START && c <= SBC_CHAR_END)) {
                    if (surplusCount >= 2) {
                        result.append(c);
                    }
                    surplusCount = surplusCount - 2;
                } else {
                    result.append(c);
                    surplusCount = surplusCount - 1;
                }
                index++;
            }
            return result.toString();
        } else {
            return source;
        }
    }

    private int getChineseCount(String str) {
        int count = 0;
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count++;
            }
        }
        return count;
    }

    private boolean isChinest(String source) {
        return Pattern.matches(regEx, source);
    }

    private int getSBCCharCount(String str) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c >= SBC_CHAR_START && c <= SBC_CHAR_END) {
                count++;
            }
        }
        return count;
    }
}
