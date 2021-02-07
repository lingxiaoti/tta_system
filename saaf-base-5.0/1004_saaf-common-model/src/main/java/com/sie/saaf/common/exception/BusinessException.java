package com.sie.saaf.common.exception;

import org.apache.commons.lang.StringUtils;

/**
 * 自定义业务异常处理类    友好提示
 * @author Andy Chan
 *
 */
public class BusinessException extends RuntimeException {
    @SuppressWarnings("compatibility:-5078821640641363743")
    private static final long serialVersionUID = 1L;

    public BusinessException(String frdMessage) {
        super(createFriendlyErrMsg(frdMessage));
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(Throwable throwable, String frdMessage) {
        super(throwable);
    }

    public static String createFriendlyErrMsg(String msgBody) {
        if (StringUtils.isBlank(msgBody))
            return "您的操作过于过于频繁，请稍后再试";
        return msgBody;
    }
}