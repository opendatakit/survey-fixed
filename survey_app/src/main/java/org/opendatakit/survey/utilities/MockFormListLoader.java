package org.opendatakit.survey.utilities;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import org.opendatakit.survey.utilities.FormInfo;

import java.util.ArrayList;

public class MockFormListLoader extends AsyncTaskLoader<ArrayList<FormInfo>> {
    private ArrayList<FormInfo> mMockForms;


    public MockFormListLoader(Context context, ArrayList<FormInfo> mockForms) {
        super(context);
        mMockForms = mockForms;
    }

    @Override
    public ArrayList<FormInfo> loadInBackground() {
        return mMockForms;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        deliverResult(mMockForms);
    }
}

