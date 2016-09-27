
package com.zlebank.zplatform.trade.exception;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年9月1日 上午11:46:02
 * @since 
 */

public class CommonException extends Exception{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3137676281266807343L;
	private String code;
	
    private String message;
    
    public CommonException(String code,String message){
        super();
        this.code = code;
        this.message = message;
    }
    
    
    public CommonException(String code){
        super();
        this.code = code;
        this.message=code;
    }
    public String getCode(){
        return code;
    }
    @Override
    public String getMessage(){
       return message;
    }
}
