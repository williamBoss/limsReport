package com.lims.project.experiment.domain.json;

import com.lims.project.experiment.domain.ExperimentResult;

import java.util.List;

/**
 * Created by Administrator on 2020/3/18.
 */
public class sampleResultsJson {

    private String id;
    private String sampleCode;
    private String typeName;
    private String sampleType;
    private String resultVal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String  getResultVal() {
        return resultVal;
    }

    public void setResultVal(String resultVal) {
        this.resultVal = resultVal;
    }

    @Override
    public String toString() {
        return "sampleResultsJson{" +
                "id='" + id + '\'' +
                ", sampleCode='" + sampleCode + '\'' +
                ", typeName='" + typeName + '\'' +
                ", sampleType='" + sampleType + '\'' +
                ", resultVal=" + resultVal +
                '}';
    }
}
