import React from 'react'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'
import { validateText } from '../../../utils/validateText.ts'
import { useReservation } from '../../../hooks/pages/useReservation.ts'
import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import Loader from '../../../components/Shared/Loading/Loader'
import PageReturn from '../../../components/Shared/PageReturn'
import PageContent from '../../../components/Shared/Contents/PageContent'
import ReservationPlan from '../../../components/Reservation/ReservationPlan'
import ReservationPanel from '../../../components/Reservation/ReservationPanel'
import ReservationDetails from '../../../components/Reservation/ReservationDetails'
import ReservationFormSection from '../../../components/Reservation/ReservationFormSection'
import { reservationService } from '../../../services/reservationService.ts'

const ReservationPage = () => {
    const { t, navigate, location } = useHooks()
    const { isAuthenticated } = useStates()
    const { stay = null, dateFrom = null, dateTo = null } = location.state || {}
    const { reservation, reservationAttractions, touristicPlace, address, price, request, setRequest } = useReservation(
        stay,
        dateFrom,
        dateTo
    )

    useRedirectEvery([!isAuthenticated], '/')

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()

        if (reservation && request)
            await reservationService.updateDetails(reservation.reservationId, validateText(request))

        navigate('/reservation/personal', {
            state: {
                reservation: reservation,
                reservationAttractions: reservationAttractions,
                touristicPlace: touristicPlace,
                address: address,
                price: price,
                request: request,
                dateFrom: dateFrom,
                dateTo: dateTo
            }
        })
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
                            stayName={stay.name}
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
                                        touristicPlace && (
                                            <ReservationPlan
                                                reservationId={reservation.reservationId}
                                                touristicPlace={touristicPlace}
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
