package com.turi.payment.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentReservationAttractionRepositoryDao extends JpaRepository<PaymentReservationAttractionEntity, Long>
{
    @Query(value = """
    SELECT * FROM paymentreservationattraction p
    WHERE p.paymentid = :paymentId AND p.reservationattractionid = :reservationAttractionId
    """, nativeQuery = true)
    Optional<PaymentReservationAttractionEntity> findByPaymentIdAndReservationAttractionId(@Param("paymentId") final Long paymentId,
                                                                                           @Param("reservationAttractionId") final Long reservationAttractionId);
}
