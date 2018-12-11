package com.jw.ticket.model.entity;

import com.jw.ticket.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author chenjunwen
 * @Date: 2018/12/4 11:31
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@Data
@Entity
/**
 * 无参数构造函数
 */
@NoArgsConstructor
/**
 * 全参数构造函数
 */
@AllArgsConstructor
/**
 * 创建索引
 */
@Table(indexes = {
        @Index(columnList = "num")
})
public class Ticket extends BaseModel{
    /**
     * 票的数量
     */
    private Integer num;

    /**
     * 票的名称
     */
    @NonNull
    @Column(columnDefinition = "varchar(255) comment '票名称'")
    private String ticketName;


    private String address;


}
