package com.thanhtung.mockproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "category")
public class Category {

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id_category")
    private Integer id;

    @ColumnInfo(name = "category_name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "slug")
    @SerializedName("slug")
    private String slug;

    @ColumnInfo(name = "parent_id")
    @SerializedName("parent_id")
    private String parentId;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getParentId() {
        return parentId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}