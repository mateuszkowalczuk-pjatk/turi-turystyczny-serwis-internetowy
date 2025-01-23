import { useLocation } from 'react-router-dom'
import React, { useEffect, useState } from 'react'
import { Address } from '../../../types'
import { addressService } from '../../../services/addressService.ts'
import Loader from '../../../components/Shared/Loading/Loader'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageReturn from '../../../components/Shared/PageReturn'
import ReservationPanel from '../../../components/Reservation/ReservationPanel'
import ReservationFormSection from '../../../components/Reservation/ReservationFormSection'
import ReservationDetails from '../../../components/Reservation/ReservationDetails'
import ReservationPlan from '../../../components/Reservation/ReservationPlan'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { reservationService } from '../../../services/reservationService.ts'

const ReservationOfferPage = () => {
    const { t, navigate } = useHooks()
    const { reservation = null, touristicPlace = null, stay = null, isOwner = null } = useLocation().state || {}
    const [ address, setAddress ] = useState<Address>()
    const [step, setStep ] = useState<number>(0)
    const [price, setPrice] = useState<number>()
    const [ isCancel, setIsCancel ] = useState<boolean>()

    useEffect(() => {
        const fetchAddress = async () => {
            const response = await addressService.getById(touristicPlace.addressId)
            const data = await response.json()
            setAddress(data)

            const priceResponse = await reservationService.getPrice(reservation.reservation.reservationId)
            const priceData: number = await priceResponse.json()
            setPrice(priceData)

            if (reservation.reservation.status === 'RESERVATION') setStep(1)
            else if (reservation.reservation.status === 'REALIZATION') setStep(2)
            else if (reservation.reservation.status === 'REALIZED') setStep(3)

            const today = new Date();
            const dateFrom = new Date(reservation.reservation.dateFrom);
            const cancelDate = new Date(today.setDate(today.getDate() + touristicPlace.cancelReservationDays));
            const condition = touristicPlace.cancelReservationDays !== undefined && cancelDate < dateFrom
            setIsCancel(condition)
        }
        fetchAddress().catch((error) => error)
    }, [])

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (isCancel)
            await reservationService.cancel(reservation.reservation.reservationId);

        navigate(-1)
    };


    return (
        <Loader>
            <PageContent
                title={<PageReturn text={t('reservation.reservation-return')} />}
                firstPanel={
                    touristicPlace &&
                    address && (
                        <ReservationPanel
                            onSubmit={isCancel && !isOwner? handleSubmit : undefined}
                            step={step}
                            touristicPlace={touristicPlace}
                            stayName={stay.name}
                            address={address}
                            reservationFormSection={
                                <ReservationFormSection
                                    leftPanel={
                                        touristicPlace && (
                                            <ReservationDetails
                                                touristicPlace={touristicPlace}
                                                dateFrom={reservation.reservation.dateFrom}
                                                dateTo={reservation.reservation.dateTo}
                                                request={reservation.reservation.request}
                                                personal
                                            />
                                        )
                                    }
                                    rightPanel={
                                        reservation &&
                                        touristicPlace && (
                                            <ReservationPlan
                                                reservationId={reservation.reservation.reservationId}
                                                touristicPlace={touristicPlace}
                                                dateFrom={reservation.reservation.dateFrom}
                                                dateTo={reservation.reservation.dateTo}
                                                reservationAttractions={reservation.attractions}
                                            />
                                        )
                                    }
                                />
                            }
                            price={price}
                            buttonText={t('reservation.reservation-plan-cancel')}
                        />
                    )
                }
            />
        </Loader>
    )
}

export default ReservationOfferPage
