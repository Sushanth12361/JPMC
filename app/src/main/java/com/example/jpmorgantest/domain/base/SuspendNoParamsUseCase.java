package com.example.jpmorgantest.domain.base;

import static com.example.jpmorgantest.util.constants.ConstantsKt.LOG_DOMAIN;
import android.util.Log;
import com.example.jpmorgantest.util.state.Result;

public abstract class SuspendNoParamsUseCase<Results> {

    public Result<Results> invoke() {
        try {
            return execute();
        } catch (Exception e) {
            Log.e(LOG_DOMAIN, e.getMessage());
            return new Result.Error.Error(e);
        }
    }

    protected abstract Result<Results> execute() throws Exception;
}