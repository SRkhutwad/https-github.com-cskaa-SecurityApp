
package com.project.verification.EmpPkg;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class EmpPojo {

    @SerializedName("data")
    private List<EmpData> mData;
    @SerializedName("status")
    private String mStatus;

    public List<EmpData> getData() {
        return mData;
    }

    public EmpPojo(List<EmpData> mData) {
        this.mData = mData;
    }

    public void setData(List<EmpData> data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}

