package com.diamond.common.entity;

import com.diamond.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 字典表
 */
@Data
public class Dict implements Serializable {
    private static final long serialVersionUID = 1L;

    //编号
    @Excel(name = "用户序号", cellType = Excel.ColumnType.NUMERIC, prompt = "用户编号")
    private Long id;

    //标签名
    @Excel(name = "标签名")
    private String name;

    //数据值
    @Excel(name = "数据值")
    private String value;

    //类型
    @Excel(name = "类型")
    private String type;

    //描述
    @Excel(name = "描述")
    private String description;

    //排序（升序）
    @Excel(name = "排序")
    private BigDecimal sort;

    //父级编号
    @Excel(name = "父级编号")
    private Long parentId;

    //创建者
    @Excel(name = "创建者")
    private Integer createBy;

    //创建时间
    @Excel(name = "创建时间")
    private Date createDate;

    //更新者
    @Excel(name = "更新者")
    private Long updateBy;

    //更新时间
    @Excel(name = "更新时间")
    private Date updateDate;

    //备注信息
    @Excel(name = "备注信息")
    private String remarks;

    //删除标记
    @Excel(name = "删除标记")
    private String delFlag;

}
