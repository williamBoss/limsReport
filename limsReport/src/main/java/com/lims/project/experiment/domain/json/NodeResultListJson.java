package com.lims.project.experiment.domain.json;

import java.util.List;

/**
 * Created by Administrator on 2020/3/18.
 */
public class NodeResultListJson {

    private String nodeCode;
    private String nodeName;
    private String nodeKey;
    private String experimentCode;
    private String taskCode;
    private String status;
    List<sampleResultsJson> sampleResults;

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getExperimentCode() {
        return experimentCode;
    }

    public void setExperimentCode(String experimentCode) {
        this.experimentCode = experimentCode;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<sampleResultsJson> getSampleResults() {
        return sampleResults;
    }

    public void setSampleResults(List<sampleResultsJson> sampleResults) {
        this.sampleResults = sampleResults;
    }

    @Override
    public String toString() {
        return "NodeResultListJson{" +
                "nodeCode='" + nodeCode + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", nodeKey='" + nodeKey + '\'' +
                ", experimentCode='" + experimentCode + '\'' +
                ", taskCode='" + taskCode + '\'' +
                ", status='" + status + '\'' +
                ", sampleResults=" + sampleResults +
                '}';
    }
}
