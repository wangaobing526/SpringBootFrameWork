package com.dhcc.ecm.business.exception;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.ibm.common.docexchange.ErrorMessageHelper;

public class EcmException extends Exception {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EcmException.class);

    private String code;
    private String errorDescrption;

    /**
     * @author ozz
     */
    @Override
    public String getMessage() {
        String mess = "code: " + getCode() + ", errorDescrption: " + getErrorDescrption();
        if(StringUtils.isEmpty(super.getMessage()) || super.getMessage().equals(getErrorDescrption()))
            return mess;
        else
            return mess + "\r\n" + super.getMessage();
    }

    /**
     * @author ozz
     */
    public static EcmException getEcmException(Throwable e) {
        if (e instanceof EcmException)
            return (EcmException) e;
        else
            return new EcmException(e);
    }

    /**
     * @author ozz
     */
    public static EcmException getEcmExceptionByCode(String code, Throwable cause) {
        EcmException ecmException = new EcmException(ErrorMessageHelper.getErrMessage(code), cause);
        ecmException.setCode(code);
        return ecmException;
    }

    /**
     * @author ozz
     */
    public static EcmException getEcmExceptionByCode(String code, String param, Throwable cause) {
        EcmException ecmException = new EcmException(code, new Object[] { param }, cause);
        ecmException.setCode(code);
        return ecmException;
    }

    public static EcmException getEcmNoPermissionException(Throwable cause) {
        return getEcmExceptionByCode(ErrorCode.NO_PERMISSION_ERROR, cause);
    }

    /**
     * Modified by ozz 2012-8-16 20:18:16 如果没有错误信息则根据code获取相应的错误信息
     */
    public String getErrorDescrption() {
        if (StringUtils.isEmpty(this.errorDescrption)) {
            try {
                this.errorDescrption = ErrorMessageHelper.getErrMessage(getCode());
            } catch (Throwable e) {
                logger.warn("get errorDescrption error. code: " + getCode(), e);
            }
        }
        return errorDescrption;
    }

    public void setErrorDescrption(String errorDescrption) {
        this.errorDescrption = errorDescrption;
    }

    public EcmException() {

    }

    public EcmException(String message) {
        super(message);
        this.errorDescrption = message;

    }
    

    public EcmException(String code,String message) {
        super(message);
        this.code = code;
        this.errorDescrption = message;

    }

    public EcmException(Throwable cause) {
        super(cause);
        if (cause != null && cause instanceof EcmException) {
            EcmException temp = (EcmException) cause;
            this.code = temp.code;
            this.errorDescrption = temp.errorDescrption;
        }
    }

    public EcmException(String message, Throwable cause) {
        super(message, cause);
        this.errorDescrption = message;
    }

    public EcmException(String code, String message, Throwable cause) {

        super(message, cause);

        this.code = code;
    }

    public EcmException(String code, Object[] params, Throwable cause) {

        super(ErrorMessageHelper.getErrMessage(code, params), cause);
        this.code = code;
        this.errorDescrption = super.getMessage();
    }

    /**
     * Modified by ozz 如果没有code则返回未知异常
     */
    public String getCode() {
        if (StringUtils.isEmpty(this.code))
            this.code = ErrorCode.UNKNOW_ERROR;
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
