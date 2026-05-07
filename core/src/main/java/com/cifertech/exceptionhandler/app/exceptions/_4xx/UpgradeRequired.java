package com.cifertech.exceptionhandler.app.exceptions._4xx;

import com.cifertech.exceptionhandler.app.exceptions.ApiException;
import com.cifertech.exceptionhandler.app.exceptions.HttpCode;

import java.util.Map;

public class UpgradeRequired extends ApiException {

    public UpgradeRequired(Object details) {
        super(HttpCode.UPGRADE_REQUIRED, details);
    }

    public UpgradeRequired(String message) {
        super(HttpCode.UPGRADE_REQUIRED, Map.of("message", message));
    }

    public UpgradeRequired(){
        super(HttpCode.UPGRADE_REQUIRED, null);
    }
}
