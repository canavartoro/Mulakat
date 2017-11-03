package com.mulakat.huseyin.data;

import com.mulakat.huseyin.interfaces.ontaskend;
import com.mulakat.huseyin.task.TranslateAsyncTask;

/**
 * Created by huseyin on 2.11.2017.
 */

public class ArchiveItemWord {

    public ArchiveItemWord() {}

    public ArchiveItemWord(String w, Integer i) {
        word = w;
        count = i;
    }

    private String word = "";
    private String wordtr = "";
    private Integer count = 0;

    public void setWord(String s) {
        word = s;
    }

    public String getWord() {
        return word;
    }

    public void setWordTr(String s) {
        wordtr = s;
    }

    public String getWordTr() {
        return wordtr;
    }

    public void setCount(Integer i) {
        count = i;
    }

    public Integer getCount() {
        return count;
    }

}
