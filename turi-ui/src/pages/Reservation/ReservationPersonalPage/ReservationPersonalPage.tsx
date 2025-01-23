import { useLocation } from 'react-router-dom'
import Loader from '../../../components/Shared/Loading/Loader'
import PageContent from '../../../components/Shared/Contents/PageContent'
import PageReturn from '../../../components/Shared/PageReturn'
import ReservationPanel from '../../../components/Reservation/ReservationPanel'
import ReservationFormSection from '../../../components/Reservation/ReservationFormSection'
import ReservationDetails from '../../../components/Reservation/ReservationDetails'
import ReservationPersonal from '../../../components/Reservation/ReservationPersonal'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import React from 'react'
import { useReservationPersonal } from '../../../hooks/pages/useReservationPersonal.ts'
import { Account } from '../../../types'
import { accountService } from '../../../services/accountService.ts'

const ReservationPersonalPage = () => {
    const { t, navigate } = useHooks()
    const {
        reservation = null,
        reservationAttractions = null,
        touristicPlace = null,
        address = null,
        price = null,
        request = null,
        dateFrom = null,
        dateTo = null
    } = useLocation().state || {}
    const { account, firstName, setFirstName, lastName, setLastName, phoneNumber, setPhoneNumber, email } =
        useReservationPersonal()

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()

        if (firstName && lastName && phoneNumber && account) {
            const accountToUpdate: Account = {
                addressId: account.addressId,
                firstName: firstName,
                lastName: lastName,
                phoneNumber: phoneNumber,
                birthDate: account.birthDate,
                gender: account.gender
            }
            await accountService.update(accountToUpdate)
        }

        navigate('/reservation/payment', {
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
                title={<PageReturn text={t('reservation.reservation-plan-return')} />}
                firstPanel={
                    touristicPlace &&
                    address &&
                    price && (
                        <ReservationPanel
                            onSubmit={handleSubmit}
                            step={2}
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
                                        <ReservationPersonal
                                            firstName={firstName}
                                            setFirstName={setFirstName}
                                            lastName={lastName}
                                            setLastName={setLastName}
                                            phoneNumber={phoneNumber}
                                            setPhoneNumber={setPhoneNumber}
                                            email={email}
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

export default ReservationPersonalPage
