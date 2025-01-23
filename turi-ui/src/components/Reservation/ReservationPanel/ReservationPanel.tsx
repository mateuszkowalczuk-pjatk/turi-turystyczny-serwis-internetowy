import React, { ReactNode } from 'react'
import ReservationProgressBar from '../ReservationProgressBar'
import TouristicPlaceBanner from '../../Shared/TouristicPlaceBanner/TouristicPlaceBanner'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import { Address } from '../../../types'
import styles from './ReservationPanel.module.css'
import ReservationPrice from '../ReservationPrice'
import ReservationButton from '../ReservationButton'
import Spinner from '../../Shared/Loading/Spinner'

interface Props {
    onSubmit?: (e: React.FormEvent) => Promise<void>
    step: number
    touristicPlace?: TouristicPlace
    address?: Address
    reservationFormSection?: ReactNode
    price?: number
    buttonText?: string
    reservationPlanSelect?: ReactNode
    plan?: boolean
    spinner?: boolean
}

const ReservationPanel = ({
    onSubmit,
    step,
    touristicPlace,
    address,
    reservationFormSection,
    price,
    buttonText,
    reservationPlanSelect,
    plan,
    spinner
}: Props) => {
    return onSubmit ? (
        <form
            className={styles.panel}
            onSubmit={onSubmit}
        >
            {spinner ? (
                <Spinner />
            ) : (
                <>
                    <ReservationProgressBar step={step} />
                    {!plan && touristicPlace && (
                        <TouristicPlaceBanner
                            touristicPlace={touristicPlace}
                            address={address}
                            isReservation
                        />
                    )}
                    {!plan && reservationFormSection}
                    {!plan && price && <ReservationPrice price={price} />}
                    {!plan && buttonText && <ReservationButton text={buttonText} />}
                    {plan && reservationPlanSelect}
                </>
            )}
        </form>
    ) : (
        <div
            className={styles.panel}
        >
            {spinner ? (
                <Spinner />
            ) : (
                <>
                    <ReservationProgressBar step={step} />
                    {!plan && touristicPlace && (
                        <TouristicPlaceBanner
                            touristicPlace={touristicPlace}
                            address={address}
                            isReservation
                        />
                    )}
                    {!plan && reservationFormSection}
                    {!plan && price && <ReservationPrice price={price} />}
                    {!plan && buttonText && <ReservationButton text={buttonText} />}
                    {plan && reservationPlanSelect}
                </>
            )}
        </div>
    )
}

export default ReservationPanel
