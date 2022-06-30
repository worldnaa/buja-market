package com.bujamarket.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass                                        //공통 매핑 정보가 필요할 때 사용
@EntityListeners(value = {AuditingEntityListener.class}) //Auditing 을 적용하기 위해 사용
public class BaseTimeEntity {

    @Column(updatable = false)
    @CreatedDate                       //엔티티가 생성되어 저장될 때, 시간을 자동으로 저장한다
    private LocalDateTime regTime;     //등록일

    @LastModifiedDate                  //엔티티의 값을 변경할 때, 시간을 자동으로 저장한다
    private LocalDateTime updateTime;  //수정일
}

/*
 * 등록일, 수정일 만 갖는 엔티티는 BaseTimeEntity 를 상속받으면 된다
*/