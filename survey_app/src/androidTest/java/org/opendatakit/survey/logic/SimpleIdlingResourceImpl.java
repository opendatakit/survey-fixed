package org.opendatakit.survey.logic;

import androidx.test.espresso.IdlingResource;

public class SimpleIdlingResourceImpl implements IdlingResource {

    private volatile ResourceCallback resourceCallback;
    private boolean isIdle = false;

    @Override
    public String getName() {
        return SimpleIdlingResourceImpl.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        this.isIdle = isIdleNow;
        if (isIdleNow && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
    }
}
