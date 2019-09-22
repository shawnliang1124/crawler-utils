package cn.shawn.crawler.entity;

import javax.persistence.*;

/**
 * *  @author jiajing.liang
 * *  @date 2019/9/11 19:06
 * *  @description
 */
@Entity
@Table(name = "lagou", schema = "mall-goods", catalog = "")
public class LagouEntity {
    private int id;
    private String jobSalary;
    private String jobCity;
    private String jobExperience;
    private String job;
    private String education;
    private String jobDesc;
    private String details;
    private String handleRate;
    private String companyName;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "job_salary")
    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    @Basic
    @Column(name = "job_city")
    public String getJobCity() {
        return jobCity;
    }

    public void setJobCity(String jobCity) {
        this.jobCity = jobCity;
    }

    @Basic
    @Column(name = "job_experience")
    public String getJobExperience() {
        return jobExperience;
    }

    public void setJobExperience(String jobExperience) {
        this.jobExperience = jobExperience;
    }

    @Basic
    @Column(name = "job")
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Basic
    @Column(name = "education")
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Basic
    @Column(name = "job_desc")
    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    @Basic
    @Column(name = "details")
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Basic
    @Column(name = "handle_rate")
    public String getHandleRate() {
        return handleRate;
    }

    public void setHandleRate(String handleRate) {
        this.handleRate = handleRate;
    }

    @Basic
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LagouEntity that = (LagouEntity) o;

        if (id != that.id) return false;
        if (jobSalary != null ? !jobSalary.equals(that.jobSalary) : that.jobSalary != null) return false;
        if (jobCity != null ? !jobCity.equals(that.jobCity) : that.jobCity != null) return false;
        if (jobExperience != null ? !jobExperience.equals(that.jobExperience) : that.jobExperience != null)
            return false;
        if (job != null ? !job.equals(that.job) : that.job != null) return false;
        if (education != null ? !education.equals(that.education) : that.education != null) return false;
        if (jobDesc != null ? !jobDesc.equals(that.jobDesc) : that.jobDesc != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        if (handleRate != null ? !handleRate.equals(that.handleRate) : that.handleRate != null) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (jobSalary != null ? jobSalary.hashCode() : 0);
        result = 31 * result + (jobCity != null ? jobCity.hashCode() : 0);
        result = 31 * result + (jobExperience != null ? jobExperience.hashCode() : 0);
        result = 31 * result + (job != null ? job.hashCode() : 0);
        result = 31 * result + (education != null ? education.hashCode() : 0);
        result = 31 * result + (jobDesc != null ? jobDesc.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (handleRate != null ? handleRate.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        return result;
    }
}
