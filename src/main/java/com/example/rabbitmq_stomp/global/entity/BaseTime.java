package com.example.rabbitmq_stomp.global.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseTime extends BaseEntity{
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime modifiedTime;

    public void setModified(){
        setModifiedTime(LocalDateTime.now());
    }
}
