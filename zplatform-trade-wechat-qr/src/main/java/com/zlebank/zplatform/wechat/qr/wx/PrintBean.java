package com.zlebank.zplatform.wechat.qr.wx;

import java.util.ArrayList;
import java.util.List;

public class PrintBean {
	private int size;
	private String[] title;
	private List<String[]> content;
	private String[] footTitle;
	private String[] footContent;
	public PrintBean(int size, int footSize) {
		this.size = size;
		title = new String[size];
		content = new ArrayList<String[]>();
		footTitle = new String[footSize];
		footContent = new String[footSize];
	}
	public void addAllTitle(String titleStr) {
	    String[] fields = titleStr.split(",");
	    int idx=0;
	    for (String field : fields) {
	        title[idx++] = field;
	    }
	}
	public void addAllContent(String contentStr) {
	    String[] details = contentStr.split("`");
	    String[] line = new String[size];
	    int idx = 0;
	    for (int i=1;i<details.length;i++) {
	        if ((i-1)%size == 0 && i!=1) {
	            content.add(line);
	            line = new String[size];idx=0;
	        }
	        if (details[i].lastIndexOf(",") == details[i].length()-1) {
	            line[idx++] = details[i].substring(0, details[i].length() < 2 ? 0 : details[i].length()-1);
	        } else {
	            line[idx++] = details[i];
	        }
	    }
	    content.add(line);
	}
	public void addFootTitle(String footStr) {
	       String[] fields = footStr.split(",");
	        int idx=0;
	        for (String field : fields) {
	            footTitle[idx++] = field;
	        }
	}
	public void addFootContent(String footContentStr) {
	    String[] fields = footContentStr.split(",`");
        int idx=0;
        for (String field : fields) {
            footContent[idx++] = field;
        }
        footContent[0] = footContent[0].replace("`", "");
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<String[]> getContent() {
		return content;
	}
	public void setContent(List<String[]> content) {
		this.content = content;
	}
	public String[] getTitle() {
		return title;
	}
	public void setTitle(String[] title) {
		this.title = title;
	}
	public String[] getFootTitle() {
		return footTitle;
	}
	public void setFootTitle(String[] footTitle) {
		this.footTitle = footTitle;
	}
	public String[] getFootContent() {
		return footContent;
	}
	public void setFootContent(String[] footContent) {
		this.footContent = footContent;
	}
	public void print() {
	    String FORMAT = "%1$-50s";
	    System.out.println("----------------------");
	    for (String str : title) {
	        System.out.print(String.format(FORMAT, str) );
	    }
	    System.out.println();
	       for (String[] str : content) {
	            for (String tmp : str) {
	                System.out.print(String.format(FORMAT, tmp));
	            }
	            System.out.println();
	        }
	    for (String str : footTitle) {
	        System.out.print(String.format(FORMAT, str));
	    }
	    System.out.println();
	    for (String str : footContent) {
	        System.out.print(String.format(FORMAT, str));
	    }
	}
}
