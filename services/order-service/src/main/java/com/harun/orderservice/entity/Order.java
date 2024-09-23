package com.harun.orderservice.entity;

import com.harun.orderservice.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table("customer_order")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue
    Integer id;
    String reference;
    BigDecimal totalAmount;
    @Enumerated(STRING)
    PaymentMethod paymentMethod;
    String customerId;
    @OneToMany(mappedBy ="order")
    List<OrderLine> orderLines;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    LocalDateTime createdDate;
    @LastModifiedDate
    @Column(updatable = false, nullable = false)
    LocalDateTime lastModifiedDate;

}
