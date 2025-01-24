import { useHooks } from '../../../hooks/shared/useHooks.ts'
import React, { useState } from 'react'
import { PaymentMethod } from '../../../types'
import Loader from '../../../components/Shared/Loading/Loader'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageReturn from '../../../components/Shared/PageReturn'
import ReservationPanel from '../../../components/Reservation/ReservationPanel'
import ReservationFormSection from '../../../components/Reservation/ReservationFormSection'
import ReservationDetails from '../../../components/Reservation/ReservationDetails'
import PremiumPayment from '../../../components/Premium/PremiumPayment'
import { reservationService } from '../../../services/reservationService.ts'
import { ReservationMode } from '../../../types/reservation.ts'
import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'

const ReservationPaymentPage = () => {
    const { t, location } = useHooks()
    const { isAuthenticated } = useStates()
    const {
        reservation = null,
        reservationAttractions = null,
        touristicPlace = null,
        address = null,
        price = null,
        request = null,
        dateFrom = null,
        dateTo = null
    } = location.state || {}
    const [paymentMethod, setPaymentMethod] = useState<PaymentMethod | null>(null)
    const [privacyPolicy, setPrivacyPolicy] = useState<boolean>(false)

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()

        if (paymentMethod === null) {
            return
        }

        const response = await reservationService.pay(
            reservation.reservationId,
            paymentMethod,
            ReservationMode.INITIAL,
            null,
            reservationAttractions
        )
        if (response.status === 200) window.location.href = await response.text()
    }

    useRedirectEvery([!isAuthenticated], '/')

    return (
        <Loader>
            <PageContent
                title={<PageReturn text={t('reservation.reservation-plan-return')} />}
                firstPanel={
                    touristicPlace &&
                    address &&
                    price && (
                        <ReservationPanel
                            onSubmit={handleSubmit}
                            step={3}
                            touristicPlace={touristicPlace}
                            address={address}
                            reservationFormSection={
                                <ReservationFormSection
                                    leftPanel={
                                        touristicPlace && (
                                            <ReservationDetails
                                                touristicPlace={touristicPlace}
                                                dateFrom={dateFrom}
                                                dateTo={dateTo}
                                                request={request}
                                                personal
                                            />
                                        )
                                    }
                                    rightPanel={
                                        <PremiumPayment
                                            paymentMethod={paymentMethod}
                                            setPaymentMethod={setPaymentMethod}
                                            privacyPolicy={privacyPolicy}
                                            setPrivacyPolicy={setPrivacyPolicy}
                                        />
                                    }
                                />
                            }
                            price={price}
                            buttonText={t('reservation.next')}
                        />
                    )
                }
            />
        </Loader>
    )
}

export default ReservationPaymentPage
