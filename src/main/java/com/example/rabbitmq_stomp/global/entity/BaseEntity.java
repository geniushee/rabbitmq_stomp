package com.example.rabbitmq_stomp.global.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Persistable;

@Getter
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public boolean isNew(){
        return id == null;
    }

    public String getModelName(){
        String name = this.getClass().getSimpleName();
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
}
