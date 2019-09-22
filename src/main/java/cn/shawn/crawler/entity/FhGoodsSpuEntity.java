package cn.shawn.crawler.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 描述:
 *
 * @author Shawn Liang
 * @create 2019-09-15 15:08
 * @package cn.shawn.crawler.entity
 * @contact https://github.com/shawnliang1124
 */
@Entity
@Table(name = "fh_goods_spu", schema = "mall-goods", catalog = "")
@DynamicInsert
@DynamicUpdate
public class FhGoodsSpuEntity {
    private int id;
    private String spuName;
    private String spuModel;
    private int spuCategoryId;
    private String description;
    private String picUrls;
    private int visible;
    private String spuList;
    private BigDecimal spuPrice;
    private int quantity;
    private Timestamp createTime;
    private Timestamp updateTime;
    private int deleted;
    private int version;
    private String url;
    private String address;

    private String producer;
    @Basic
    @Column(name = "producer")
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "spu_name")
    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    @Basic
    @Column(name = "spu_model")
    public String getSpuModel() {
        return spuModel;
    }

    public void setSpuModel(String spuModel) {
        this.spuModel = spuModel;
    }

    @Basic
    @Column(name = "spu_category_id")
    public int getSpuCategoryId() {
        return spuCategoryId;
    }

    public void setSpuCategoryId(int spuCategoryId) {
        this.spuCategoryId = spuCategoryId;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "pic_urls")
    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    @Basic
    @Column(name = "visible")
    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "spu_list")
    public String getSpuList() {
        return spuList;
    }

    public void setSpuList(String spuList) {
        this.spuList = spuList;
    }

    @Basic
    @Column(name = "spu_price")
    public BigDecimal getSpuPrice() {
        return spuPrice;
    }

    public void setSpuPrice(BigDecimal spuPrice) {
        this.spuPrice = spuPrice;
    }

    @Basic
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "deleted")
    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FhGoodsSpuEntity that = (FhGoodsSpuEntity) o;

        if (id != that.id) return false;
        if (spuCategoryId != that.spuCategoryId) return false;
        if (visible != that.visible) return false;
        if (quantity != that.quantity) return false;
        if (deleted != that.deleted) return false;
        if (version != that.version) return false;
        if (spuName != null ? !spuName.equals(that.spuName) : that.spuName != null) return false;
        if (spuModel != null ? !spuModel.equals(that.spuModel) : that.spuModel != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (picUrls != null ? !picUrls.equals(that.picUrls) : that.picUrls != null) return false;
        if (spuList != null ? !spuList.equals(that.spuList) : that.spuList != null) return false;
        if (spuPrice != null ? !spuPrice.equals(that.spuPrice) : that.spuPrice != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (spuName != null ? spuName.hashCode() : 0);
        result = 31 * result + (spuModel != null ? spuModel.hashCode() : 0);
        result = 31 * result + spuCategoryId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (picUrls != null ? picUrls.hashCode() : 0);
        result = 31 * result + visible;
        result = 31 * result + (spuList != null ? spuList.hashCode() : 0);
        result = 31 * result + (spuPrice != null ? spuPrice.hashCode() : 0);
        result = 31 * result + quantity;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + deleted;
        result = 31 * result + version;
        return result;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FhGoodsSpuEntity{" +
                "id=" + id +
                ", spuName='" + spuName + '\'' +
                ", spuModel='" + spuModel + '\'' +
                ", spuCategoryId=" + spuCategoryId +
                ", description='" + description + '\'' +
                ", picUrls='" + picUrls + '\'' +
                ", visible=" + visible +
                ", spuList='" + spuList + '\'' +
                ", spuPrice=" + spuPrice +
                ", quantity=" + quantity +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                ", version=" + version +
                ", url='" + url + '\'' +
                ", address='" + address + '\'' +
                ", producer='" + producer + '\'' +
                '}';
    }
}
