import React, { ReactNode } from 'react'
import Spinner from '../../Shared/Loading/Spinner'
import ReservationPrice from '../ReservationPrice'
import ReservationButton from '../ReservationButton'
import TouristicPlaceBanner from '../../Shared/TouristicPlaceBanner/TouristicPlaceBanner'
import ReservationProgressBar from '../ReservationProgressBar'
import { Address } from '../../../types'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import styles from './ReservationPanel.module.css'

interface Props {
    onSubmit?: (e: React.FormEvent) => Promise<void>
    step: number
    touristicPlace?: TouristicPlace
    stayName?: string
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
    stayName,
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
                            stayName={stayName}
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
        <div className={styles.panel}>
            {spinner ? (
                <Spinner />
            ) : (
                <>
                    <ReservationProgressBar step={step} />
                    {!plan && touristicPlace && (
                        <TouristicPlaceBanner
                            touristicPlace={touristicPlace}
                            address={address}
                            stayName={stayName}
                            isReservation
                        />
                    )}
                    {!plan && reservationFormSection}
                    {!plan && price && <ReservationPrice price={price} />}
                    {!plan && buttonText && (
                        <ReservationButton
                            text={buttonText}
                            onlyDisplay={onSubmit !== null}
                        />
                    )}
                    {plan && reservationPlanSelect}
                </>
            )}
        </div>
    )
}

export default ReservationPanel
