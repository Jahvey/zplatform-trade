package com.zlebank.zplatform.trade.bean.gateway;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class QueryAccBean implements Serializable {
	
		private static final long serialVersionUID = 1L;
		
		@NotEmpty(message="param.empty.memberId")
	    private String memberId="";
	    @NotEmpty(message="param.empty.accoutType")
	    @Length(max=3,message="param.error.accoutType")
	    private String accoutType="";
		public String getMemberId() {
			return memberId;
		}
		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}
		public String getAccoutType() {
			return accoutType;
		}
		public void setAccoutType(String accoutType) {
			this.accoutType = accoutType;
		}
	    
	    
		
		
	    
}
