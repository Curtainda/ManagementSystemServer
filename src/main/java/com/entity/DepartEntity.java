package com.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "depart", schema = "management", catalog = "")
public class DepartEntity {
    private BigInteger id;
    private String departId;
    private String departName;
    private String upDepartId;
    private Integer state;
    private String remarks;
    private Timestamp creatTime;
    private Timestamp lastUpdateTime;
    private Integer isUpload;
    private Timestamp uploadTime;

    @Id
    @Column(name = "ID")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "depart_id")
    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    @Basic
    @Column(name = "depart_name")
    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    @Basic
    @Column(name = "up_depart_id")
    public String getUpDepartId() {
        return upDepartId;
    }

    public void setUpDepartId(String upDepartId) {
        this.upDepartId = upDepartId;
    }

    @Basic
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Basic
    @Column(name = "creat_time")
    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    @Basic
    @Column(name = "last_update_time")
    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Basic
    @Column(name = "is_upload")
    public Integer getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(Integer isUpload) {
        this.isUpload = isUpload;
    }

    @Basic
    @Column(name = "upload_time")
    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartEntity that = (DepartEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (departId != null ? !departId.equals(that.departId) : that.departId != null) return false;
        if (departName != null ? !departName.equals(that.departName) : that.departName != null) return false;
        if (upDepartId != null ? !upDepartId.equals(that.upDepartId) : that.upDepartId != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (creatTime != null ? !creatTime.equals(that.creatTime) : that.creatTime != null) return false;
        if (lastUpdateTime != null ? !lastUpdateTime.equals(that.lastUpdateTime) : that.lastUpdateTime != null)
            return false;
        if (isUpload != null ? !isUpload.equals(that.isUpload) : that.isUpload != null) return false;
        if (uploadTime != null ? !uploadTime.equals(that.uploadTime) : that.uploadTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (departId != null ? departId.hashCode() : 0);
        result = 31 * result + (departName != null ? departName.hashCode() : 0);
        result = 31 * result + (upDepartId != null ? upDepartId.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (creatTime != null ? creatTime.hashCode() : 0);
        result = 31 * result + (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        result = 31 * result + (isUpload != null ? isUpload.hashCode() : 0);
        result = 31 * result + (uploadTime != null ? uploadTime.hashCode() : 0);
        return result;
    }
}
