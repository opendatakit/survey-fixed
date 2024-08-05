package org.opendatakit.survey;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import org.opendatakit.survey.utilities.FormInfo;

import java.util.ArrayList;


public class MockFormListLoader extends AsyncTaskLoader<ArrayList<FormInfo>> {
    private ArrayList<FormInfo> mMockForms;

    public MockFormListLoader(Context context) {
        super(context);
        this.mMockForms = MockFormData.generateMockForms(); // Assuming MockData.generateForms() is available
    }

    @Override
    public ArrayList<FormInfo> loadInBackground() {
        return mMockForms;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mMockForms != null) {
            deliverResult(mMockForms);
        }
        forceLoad();
    }

}

