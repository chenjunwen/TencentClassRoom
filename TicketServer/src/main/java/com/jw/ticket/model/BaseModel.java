package com.jw.ticket.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author: chenJenWen
 * @date: 2018/12/4 15:25
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@Data
/**
 * 标识的类表示其不能映射到数据库表，因为其不是一个完整的实体类，但是它所拥有的属性能够隐射在其子类对用的数据库表中
 */
@MappedSuperclass
/**
 *  是用于监听实体类添加或者删除操作的。
 */
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-", "");

    @Column(nullable = false)
    @CreatedDate
    private Date createDate;

    /**
     * 格式化时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    @Column(nullable = false)
    private Date updateDate;

    @Column(nullable = false)
    @Version
    private Integer version;

}
