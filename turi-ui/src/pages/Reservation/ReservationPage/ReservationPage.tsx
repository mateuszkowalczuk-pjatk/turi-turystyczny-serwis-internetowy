import React from 'react'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useLocation } from 'react-router-dom'
import { validateText } from '../../../utils/validateText.ts'
import { useReservation } from '../../../hooks/pages/useReservation.ts'
import Loader from '../../../components/Shared/Loading/Loader'
import PageReturn from '../../../components/Shared/PageReturn'
import PageContent from '../../../components/Shared/Contents/PageContent'
import ReservationPlan from '../../../components/Reservation/ReservationPlan'
import ReservationPanel from '../../../components/Reservation/ReservationPanel'
import ReservationDetails from '../../../components/Reservation/ReservationDetails'
import ReservationFormSection from '../../../components/Reservation/ReservationFormSection'
import { reservationService } from '../../../services/reservationService.ts'

const ReservationPage = () => {
    const { t, navigate } = useHooks()
    const { stay = null, dateFrom = null, dateTo = null } = useLocation().state || {}
    const { reservation, reservationAttractions, touristicPlace, address, price, request, setRequest } = useReservation(
        stay,
        dateFrom,
        dateTo
    )

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()

        if (reservation) await reservationService.updateDetails(reservation.reservationId, validateText(request))

        navigate('/reservation/personal')
    }

    return (
        <Loader>
            <PageContent
                title={<PageReturn text={t('reservation.reservation-return')} />}
                firstPanel={
                    touristicPlace &&
                    address &&
                    price && (
                        <ReservationPanel
                            onSubmit={handleSubmit}
                            step={1}
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
                                                setRequest={setRequest}
                                            />
                                        )
                                    }
                                    rightPanel={
                                        reservation &&
                                        touristicPlace.touristicPlaceId && (
                                            <ReservationPlan
                                                reservationId={reservation.reservationId}
                                                touristicPlaceId={touristicPlace.touristicPlaceId}
                                                dateFrom={dateFrom}
                                                dateTo={dateTo}
                                                reservationAttractions={reservationAttractions}
                                            />
                                        )
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

export default ReservationPage
