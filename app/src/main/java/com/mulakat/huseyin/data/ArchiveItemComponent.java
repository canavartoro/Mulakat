package com.mulakat.huseyin.data;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;

import com.mulakat.huseyin.interfaces.ontaskend;
import com.mulakat.huseyin.task.DownloadImageTask;
import com.mulakat.huseyin.task.TranslateAsyncTask;
import com.mulakat.huseyin.util.statics;
import com.mulakat.huseyin.util.utility;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by huseyin on 1.11.2017.
 */

public class ArchiveItemComponent implements ontaskend {

    private TranslateAsyncTask translateAsyncTask;
    private DownloadImageTask downloadImageTask;
    private ontaskend wordlistener;

    public ArchiveItemComponent() {}

    public ArchiveItemComponent(String line) {
        archivetitle = Html.fromHtml(line.substring(line.indexOf("archive-item-component__title") + 50, line.indexOf("archive-item-component__desc") - 15)).toString();
        archivedesc = line.substring(line.indexOf("archive-item-component__desc") + 49);
        archivedesc = Html.fromHtml(archivedesc.substring(0, archivedesc.indexOf("</p>"))).toString();
        archivelink = line.substring(line.indexOf("archive-item-component__link") + 36, line.indexOf("<div") - 21);
        archiveimage = line.substring(line.indexOf("image-group-component") + 52, line.indexOf("srcset") - 3);
        archivetime = line.substring(line.indexOf("time") + 24, line.indexOf("/time") - 1);
        archiveauthor = line.substring(line.indexOf("visually-hidden") + 36, line.indexOf("byline-component__content") -20);
        archiveauthor = archiveauthor.replaceAll("<!-- /react-text", "");
        String[] tmp = archiveauthor.split("-->");
        if (tmp != null && tmp.length > 0) {
            archiveauthor = Html.fromHtml(tmp[tmp.length -1]).toString();
        }
        if (archiveimage != null) {
            downloadImageTask = new DownloadImageTask(this, archiveimage);
            downloadImageTask.execute();
        }
    }

    private String archiveimage = "";
    private String archivetime = "";
    private String archiveauthor = "";
    private String archivetitle = "";
    private String archivedesc = "";
    private String archivetext = "";
    private String archivelink = "";
    private Bitmap image;
    private ArrayList<ArchiveItemWord> wordlist;

    public String getArchiveImage() {
        return archiveimage;
    }

    public void setArchiveImage(String s) {
        archiveimage = s;
    }

    public String getArchiveTime() {
        return archivetime;
    }

    public void setArchiveTime(String s) {
        archivetime = s;
    }

    public String getArchiveAuthor() {
        return archiveauthor;
    }

    public void setArchiveAuthor(String s) {
        archiveauthor = s;
    }

    public String getArchiveTitle() {
        return archivetitle;
    }

    public void setArchiveTitle(String s) {
        archivetitle = s;
    }

    public String getArchiveDesc() {
        return archivedesc;
    }

    public void setArchiveDesc(String s) {
        archivedesc = s;
    }

    public String getArchiveLink() {
        return archivelink;
    }

    public void setArchiveLink(String s) {
        archivelink = s;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getArchiveText() {
        return archivetext;
    }

    public void setWordListener(ontaskend l) {
        wordlistener = l;
    }

    public String getWords() {
        StringBuffer stringBuffer = new StringBuffer();
        if (wordlist != null && wordlist.size() > 0) {
            for (ArchiveItemWord w : wordlist) {
                stringBuffer.append(w.getWord()).append(" (").append(w.getCount()).append(")").append(" | ").append(w.getWordTr()).append(" |");
            }
        }
        return stringBuffer.toString();
    }

    public void setArchiveText(String text) {
        archivetext = text;
        if (text != null) {
            HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();
            archivetext = archivetext.replace(".", "");
            archivetext = archivetext.replace(",", "");
            String[] words = archivetext.toLowerCase().split(" ");
            for (String word : words) {
                if (utility.isNumeric(word) == false) {
                    if(wordCountMap.containsKey(word)) {
                        wordCountMap.put(word, wordCountMap.get(word)+1);
                    }
                    else {
                        wordCountMap.put(word, 1);
                    }
                }
            }
            //quick sort kullanmak gerekiyor, sqlite ile de çözülebilir
            Set<Map.Entry<String, Integer>> entrySet = wordCountMap.entrySet();
            statics.getMainActivity().getDbHelper().deleteAllWords();
            for (Map.Entry<String, Integer> entry : entrySet) {
                //System.out.println("kelime:" + entry.getKey() + ", say:" + entry.getValue());
                statics.getMainActivity().getDbHelper().insertWord(entry.getKey(), entry.getValue());
            }
            wordlist = statics.getMainActivity().getDbHelper().getAllWords();
            translateAsyncTask = new TranslateAsyncTask(this);
            translateAsyncTask.execute(wordlist);
        }
    }


    @Override
    public void onEnd(int resp, String item, Object data) {
        if (resp == 0 && data != null) {
            image = (Bitmap)data;
            if ( statics.getMainActivity() != null) {
                statics.getMainActivity().reloadList();
            }
        }
        else if (resp == 2) {
            if (wordlistener != null) {
                wordlistener.onEnd(2, "", this);
            }
        }
    }
}
