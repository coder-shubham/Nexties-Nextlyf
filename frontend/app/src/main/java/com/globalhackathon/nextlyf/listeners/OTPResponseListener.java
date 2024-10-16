package com.globalhackathon.nextlyf.listeners;

import com.globalhackathon.nextlyf.model.OTPResponse;

public interface OTPResponseListener {

    void onResponse(OTPResponse otpResponse);

    void onFailure(Throwable t);

}
