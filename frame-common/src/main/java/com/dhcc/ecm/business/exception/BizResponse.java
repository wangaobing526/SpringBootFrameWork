/*
 * @copyright(disclaimer)
 *
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010  All Rights Reserved.
 *
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright Office.
 *
 * @endCopyright
 */
package com.dhcc.ecm.business.exception;

import java.util.List;

/**
 * 
 * 
 * @version $Rev: 620 $ $Date: 2011-11-30 17:01:28 +0800 (星期三, 30 十一月 2011) $
 */
public class BizResponse {
    
    protected String errorCode;
    protected String errorDescription;
    
    protected Object item;

    public BizResponse() {
    }
    
    public BizResponse(Object item) {
    	super();
    	if(item instanceof List){
    		this.item = ((List) item).toArray();
    	}else{
    		this.item = item;
    	}   	
    }
    public BizResponse(String errorCode, String errorDescription) {
        super();
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
   
}
