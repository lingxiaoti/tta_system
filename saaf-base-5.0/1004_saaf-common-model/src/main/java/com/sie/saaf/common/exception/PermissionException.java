package com.sie.saaf.common.exception;

public class PermissionException extends BusinessException {
    @SuppressWarnings("compatibility:-5078821640641363743")
    private static final long serialVersionUID = 1L;

    public PermissionException(String frdMessage) {
        super(createFriendlyErrMsg(frdMessage));
    }

    public PermissionException(Throwable throwable) {
        super(throwable);
    }

    public PermissionException(Throwable throwable, String frdMessage) {
        super(throwable);
    }

}
