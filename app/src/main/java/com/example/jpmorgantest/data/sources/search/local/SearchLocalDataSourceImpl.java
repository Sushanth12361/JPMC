package com.example.jpmorgantest.data.sources.search.local;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.jpmorgantest.util.constants.ConstantsKt;
import com.example.jpmorgantest.util.state.Result;

import javax.inject.Inject;

import kotlin.Unit;
import kotlin.jvm.JvmField;

public class SearchLocalDataSourceImpl implements SearchLocalDataSource {
    private final SharedPreferences sharedPreferences;

    @Inject
    public SearchLocalDataSourceImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public Result<Unit> saveQuery(@NonNull String value) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (value.isEmpty()) {
                editor.remove(RECENTLY_SEARCHED);
            } else {
                editor.putString(RECENTLY_SEARCHED, value);
            }
            editor.apply();
            return new Result.Success<>(null);
        } catch (Exception e) {
            return new Result.Error(e);
        }
    }

    @NonNull
    @Override
    public Result<String> recentlySearched() {
        try {
            String result = sharedPreferences.getString(
                    RECENTLY_SEARCHED,
                    ConstantsKt.EMPTY_STRING
            );
            if (result.isBlank()) {
                return new Result.Error(new Exception());
            } else {
                return new Result.Success<>(result);
            }
        } catch (Exception e) {
            return new Result.Error(e);
        }
    }

    @JvmField
    static String RECENTLY_SEARCHED = "recently_searched";
}
