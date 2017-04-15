package com.tk.sample;

/**
 * Created by TK on 2016/7/25.
 */
public class DataUtils {
    public static String[] initData() {
        String[] s = new String[31];
        for (int i = 0; i < 31; i++) {
            s[i] = "模拟条目" + i;
        }
        return s;
    }
}
