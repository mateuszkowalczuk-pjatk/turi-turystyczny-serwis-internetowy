import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlanReservationAttraction.module.css'
import TextExtraLight from '../../Shared/Controls/Text/TextExtraLight'
import { handleDateDisplay, handleTimeDisplay } from '../../../utils/handleDateTimeDisplay.ts'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import { GreenButton } from '../../Shared/Controls/Button'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import React, { useEffect, useState } from 'react'
import { attractionService } from '../../../services/attractionService.ts'
import { Attraction } from '../../../types/attraction.ts'
import { reservationService } from '../../../services/reservationService.ts'

interface Props {
    reservationAttraction: ReservationAttraction
    setReservationAttractions?: (
        value: ((prevState: ReservationAttraction[]) => ReservationAttraction[]) | ReservationAttraction[]
    ) => void
    plan?: boolean
}

const ReservationPlanReservationAttraction: React.FC<Props> = ({
    reservationAttraction,
    setReservationAttractions,
    plan
}: Props) => {
    const { t } = useHooks()
    const [name, setName] = useState<string>('')

    useEffect(() => {
        const fetchName = async () => {
            const attractionResponse = await attractionService.getById(reservationAttraction.attractionId)
            const attractionData: Attraction = await attractionResponse.json()
            setName(attractionData.name)
        }
        fetchName().catch((error) => console.error(error))
    }, [reservationAttraction.attractionId])

    const handleDelete = async (e: React.FormEvent) => {
        e.preventDefault()

        const response = await reservationService.deleteAttraction(reservationAttraction.reservationAttractionId)
        if (response.status === 200 && setReservationAttractions) {
            setReservationAttractions((prevState) =>
                prevState.filter(
                    (attraction) => attraction.reservationAttractionId !== reservationAttraction.reservationAttractionId
                )
            )
        }
    }

    return (
        <form
            className={styles.reservation}
            onSubmit={handleDelete}
        >
            <div className={styles.time}>
                <TextExtraLight text={handleDateDisplay(reservationAttraction.dateFrom.toString())} />
                <TextRegular
                    text={
                        handleTimeDisplay(reservationAttraction.hourFrom) +
                        ' - ' +
                        handleTimeDisplay(reservationAttraction.hourTo)
                    }
                />
            </div>
            <div className={styles.name}>
                <TextMedium text={name} />
            </div>
            {plan && (
                <div className={styles.button}>
                    <GreenButton
                        text={t('reservation.reservation-plan-delete')}
                        type={'submit'}
                    />
                </div>
            )}
        </form>
    )
}

export default ReservationPlanReservationAttraction
