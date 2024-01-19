package com.paymentservice.data;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
@Table(name = "payments")
public class PaymentEntity {
@Id
private String paymentId;
private String orderId;
private LocalDate timestamp;
private String paymentStatus;

}
