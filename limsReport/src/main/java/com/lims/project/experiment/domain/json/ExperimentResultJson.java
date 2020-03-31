package com.lims.project.experiment.domain.json;

import java.util.List;

/**
 * Created by Administrator on 2020/3/18.
 */
public class ExperimentResultJson {

    private  String experimentCode;

    private List<NodeResultListJson> nodeResultList;

    public String getExperimentCode() {
        return experimentCode;
    }

    public void setExperimentCode(String experimentCode) {
        this.experimentCode = experimentCode;
    }

    public List<NodeResultListJson> getNodeResultList() {
        return nodeResultList;
    }

    public void setNodeResultList(List<NodeResultListJson> nodeResultList) {
        this.nodeResultList = nodeResultList;
    }

    @Override
    public String toString() {
        return "ExperimentResultJson{" +
                "experimentCode='" + experimentCode + '\'' +
                ", nodeResultList=" + nodeResultList +
                '}';
    }
}
