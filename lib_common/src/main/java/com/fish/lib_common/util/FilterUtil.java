package com.fish.lib_common.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.fish.lib_common.extenision.CommonExtenisionKt;

import org.jetbrains.annotations.Contract;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 小任
 * @date 2017/4/29
 * version 1.0
 * 描述:
 */

public class FilterUtil {


    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 浮点数格式化
     *
     * @param isPercentage 是否为分数
     * @param needSign     是否需要正号
     * @param isMoney      是否使用金钱格式(每3位用","分隔)
     * @param digit        需要保留的位数
     * @param num          需要格式化的值(int、float、double、String、byte均可)
     */
    @NonNull
    public static String format(boolean isPercentage, boolean needSign, boolean isMoney, int digit, Object num) {
        digit = digit < 0 ? 0 : digit;
        if (num == null) num = 0;

        double converted;//需求大多四舍五入  float保留位数会舍去后面的
        try {
            converted = Double.parseDouble(num.toString());
        } catch (Throwable e) {
            Log.e("FormatErr", "Input number is not kind number");
            converted = 0d;
        }
        StringBuilder sb = new StringBuilder("%");

        if (needSign && (converted > 0)) {
            sb.append("+");
        }

        if (isMoney) {
            sb.append(",");
        }

        sb.append(".").append(digit).append("f");

        if (isPercentage) {
            sb.append("%%");
        }

        return String.format(Locale.getDefault(), sb.toString(), converted);
    }

    public static String numFormat(Object num, int digit) {
        return numFormat(false, digit, num);
    }

    public static String numFormat(boolean needSign, int digit, Object num) {
        return format(false, needSign, false, digit, num);
    }

    /**
     * 保留四位小数，直接去掉尾数
     */
    public static double getFourDouble(double d) {
        BigDecimal b = new BigDecimal(d);
        d = b.setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
        return d;
    }

    /**
     * 保留四位位小数且不用科学计数法，若没有小数不补0
     *
     * @param d 数据源
     * @return 字符串
     */
    public static String formateDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.####");
        String value = df.format(d);
        return value;
    }

    /**
     * 创建double类型的输入过滤器
     * <p>
     * 可通过设置如下属性保证EditText无法粘贴和选取
     * edittext.setLongClickable(false);
     * edittext.setTextIsSelectable(false);
     *
     * @param digit 小数最多为digit位
     * @return 返回过滤器
     */
    @NonNull
    @Contract(pure = true)
    public static InputFilter createDoubleInputFilter(final int digit) {
        return (source, start, end, dest, dstart, dend) -> {
            try {
                String destStr = dest.toString();
                String sourceStr = source.toString();

                //禁止粘贴
                /*if (sourceStr.length() > 1) {
                    return "";
                }*/

                //判断输入的字符是否为数字或者小数点
                if (!sourceStr.matches("[\\d.]*")) {
                    return "";
                }

                //目标将要生成的字符串
                String reg;
                if (digit > 0) {
                    reg = "^(([1-9][^.]*)|0)(\\.[\\d]{0," + digit + "})?$";
                } else {
                    reg = "^(([1-9][^.]*)|0)$";
                }
                String finalString = destStr.substring(0, dstart) + source + destStr.substring(dend);
                if (!finalString.matches(reg)) {
                    //如果组合后生成字符串不匹配规则,则也禁止输入
                    return "";
                }

                return source;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        };
    }

    /**
     * 创建最小单位输入过滤器
     */
    @NonNull
    @Contract(pure = true)
    public static InputFilter createUnitFilter(final double unit) {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                try {
                    String destStr = dest.toString();
                    String sourceStr = source.toString();

                    //判断输入的字符是否为数字或者小数点
                    if (!sourceStr.matches("[\\d.]*")) {
                        return "";
                    }

//                    //目标将要生成的字符串
//                    String reg;
//                    if (digit > 0) {
//                        reg = "^(([1-9][^.]*)|0)(\\.[\\d]{0," + digit + "})?$";
//                    } else {
//                        reg = "^(([1-9][^.]*)|0)$";
//                    }
//                    String finalString = destStr.substring(0, dstart) + source + destStr.substring(dend);
//                    if (!finalString.matches(reg)) {
//                        //如果组合后生成字符串不匹配规则,则也禁止输入
//                        return "";
//                    }

                    return source;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            }
        };
    }

    /**
     * 给edittext设置过滤器 过滤emojilayout_line_menu
     */
    @NonNull
    @Contract(pure = true)
    public static InputFilter setFilter() {
        return (source, start, end, dest, dstart, dend) -> {
            Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(source);
            if (matcher.find()) {
                CommonExtenisionKt.toast(null, "禁止表情符号");
                return "";
            } else return source;
        };
    }


    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            if (" ".equals(source)) {
                return "";
            } else {
                return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

}
