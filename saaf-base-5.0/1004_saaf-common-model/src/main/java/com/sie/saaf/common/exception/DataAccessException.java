package com.sie.saaf.common.exception;

public class DataAccessException extends RuntimeException {
    @SuppressWarnings("compatibility:-6202772110610669815")
    private static final long serialVersionUID = 1L;

    /**
     * @param frdMessage
     */
    public DataAccessException(String frdMessage) {
        super(createFriendlyErrMsg(frdMessage));
    }

    public DataAccessException(Throwable throwable) {
        super(throwable);
    }

    public DataAccessException(Throwable throwable, String frdMessage) {
        super(throwable);
    }

    private static String createFriendlyErrMsg(String msgBody) {
        String prefixStr = "抱歉，";
        String suffixStr = " 请稍后再试或与管理员联系！";
        StringBuffer friendlyErrMsg = new StringBuffer("");
        friendlyErrMsg.append(prefixStr);
        friendlyErrMsg.append(msgBody);
        friendlyErrMsg.append(suffixStr);
        return friendlyErrMsg.toString();
    }
}