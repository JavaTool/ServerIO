package org.tool.server.charge;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class PayItemInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 扣游戏币数量 */
	private int amt;
	/** 订单号，业务需要确保全局的唯一性；相同的订单号不会重复扣款。 */
	private String billno;
	
	private int id;
	
	private String name = "";
	
	private String desc = "";
	/** 购买数量 */
	private int num;

	public int getAmt() {
		return amt;
	}

	public void setAmt(int amt) {
		this.amt = amt;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getName());
		builder.append("[amt=").append(amt).append("]");
		builder.append("[billno=").append(billno).append("]");
		builder.append("[id=").append(id).append("]");
		builder.append("[name=").append(name).append("]");
		builder.append("[desc=").append(desc).append("]");
		builder.append("[num=").append(num).append("]");
		return builder.toString();
	}

	public static PayItemInfo parseFrom(String text) {
		return JSONObject.parseObject(text, PayItemInfo.class);
	}

}
