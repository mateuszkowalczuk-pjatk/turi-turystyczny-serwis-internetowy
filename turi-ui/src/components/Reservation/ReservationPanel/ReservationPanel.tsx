import React, { ReactNode } from 'react'
import ReservationProgressBar from '../ReservationProgressBar'
import TouristicPlaceBanner from '../../Shared/TouristicPlaceBanner/TouristicPlaceBanner'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import { Address } from '../../../types'
import styles from './ReservationPanel.module.css'
import ReservationPrice from '../ReservationPrice'
import ReservationButton from '../ReservationButton'

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
    plan
}: Props) => {
    return (
        <form
            className={styles.panel}
            onSubmit={onSubmit}
        >
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
        </form>
    )
}

export default ReservationPanel
