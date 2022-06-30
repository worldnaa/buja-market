package com.bujamarket.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter @Setter
@MappedSuperclass                                        //공통 매핑 정보가 필요할 때 사용
@EntityListeners(value = {AuditingEntityListener.class}) //Auditing 을 적용하기 위해 사용
public abstract class BaseEntity extends BaseTimeEntity{

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;   //등록자

    @LastModifiedBy
    private String modifiedBy;  //수정자
}

/*
* 등록일, 수정일, 등록자, 수정자 를 모두 갖는 엔티티는 BaseEntity 를 상속받으면 된다
*/