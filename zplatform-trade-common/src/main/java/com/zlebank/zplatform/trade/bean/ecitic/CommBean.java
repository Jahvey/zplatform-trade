/*
 * 创建日期 2006-5-25
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.zlebank.zplatform.trade.bean.ecitic;

/**
 * @author asus
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
/*
 * 创建日期 2006-3-9
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
/*
 * 创建日期 2006-2-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */


public class CommBean{    
	int cursor = 0;
	protected String[][] data = null;
	protected String[] head = null;

	public CommBean() {
	}

	public CommBean(String[][] data) {
		this.data = data;
		this.head = data[0];
	}

	public CommBean(String[] head) {
		this.data = new String[1][head.length];
		data[0] = head;
		this.head = data[0];
	}

	public boolean next() {
		cursor++;
		return cursor < data.length;
	}

	public boolean moveto(int index) {
		if (index <= 0) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean previous() {
		if (cursor > 0) {
			cursor--;
			return true;
		}
		else {
			return false;
		}
	}

	public void beforefirst() {
		this.cursor = 0;
	}

	public String[] getHead() {
		return head;
	}

	public String getValue(String colName) {
		if (cursor == 0) {
			cursor = 1;
		}

		for (int i = 0; i < head.length; i++) {
			if (head[i].equals(colName)) {
				if(data[cursor][i]==null || data[cursor][i].equals(""))//by liujq 2006-09-01
					return "";
				else
					return data[cursor][i].trim();
			}
		}
		return "";
	}

	public String[][] getData() {

		return data;
	}

	public int getRowNum() {
		if (data == null) {
			return 0;
		}
		else {
			return data.length - 1;
		}
	}

	public void addColumn(String colName) {
		String newData[][] = new String[data.length][data[0].length + 1];
		for(int i = 0;i<data.length;i++)
		{
			for(int j = 0;j<data[0].length;j++)
			{
				newData[i][j] = data[i][j];
			}
			if(i!=0)
			{
				newData[i][newData[0].length-1] = "";
			}
		}
		newData[0][newData[0].length-1] = colName;
		this.data = newData;
		this.head = newData[0];
		this.cursor = 0;
	}

	private int getColIndex(String colName) {
		for (int i = 0; i < head.length; i++) {
			if (head[i].equals(colName)) {
				return i;
			}
		}
		return -1;
	}

	public void updateValue(String colName, String value) {
		int colIndex = getColIndex(colName);
		if (colIndex > -1 && cursor > 0) {
			data[cursor][colIndex] = value;
		}
	}

	public void appendRow(String[] row) {
		Object[] temp = new Object[data.length];
		System.arraycopy(data, 0, temp, 0, data.length);
		data = new String[temp.length + 1][head.length];
		System.arraycopy(temp, 0, data, 0, temp.length);
		data[temp.length] = row;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				buff.append("[" + data[i][j] + "]");
			}
			buff.append("\n");
		}
		return buff.toString();
	}
	public String [] getCurrentRow(){
		
		return data[cursor];
		
	}
	
}



