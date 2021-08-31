package com.ieee.ieee_yesist.model;

public class YesistHome {
    Integer quesName;
    String quesReason;

    public YesistHome(Integer quesName, String quesReason) {
        this.quesName = quesName;
        this.quesReason = quesReason;
    }

    public Integer getQuesName() {
        return quesName;
    }

    public void setQuesName(Integer quesName) {
        this.quesName = quesName;
    }

    public String getQuesReason() {
        return quesReason;
    }

    public void setQuesReason(String quesReason) {
        this.quesReason = quesReason;
    }
}
