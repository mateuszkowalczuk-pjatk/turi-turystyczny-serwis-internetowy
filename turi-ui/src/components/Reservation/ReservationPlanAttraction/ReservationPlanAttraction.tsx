import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useReservationPlanAttraction } from '../../../hooks/components/useReservationPlanAttraction.ts'
import ImagePanel from '../../Shared/Image/ImagePanel'
import ReservationPlanButtons from '../ReservationPlanButtons'
import ReservationPlanAttractionInformation from '../ReservationPlanAttractionInformation'
import ReservationPlanAttractionForm from '../ReservationPlanAttractionForm'
import { Props } from './props.ts'
import { PriceType } from '../../../types/attraction.ts'
import { ReservationAttraction } from '../../../types/reservation.ts'
import { reservationService } from '../../../services/reservationService.ts'
import styles from './ReservationPlanAttraction.module.css'
import React from 'react'

const ReservationPlanAttraction = ({
    reservationId,
    attractionId,
    attraction,
    setReservationAttractions,
    dateFrom,
    dateTo
}: Props) => {
    const { t } = useHooks()
    const { reservationMode, setReservationMode, image, formData, handleChange } =
        useReservationPlanAttraction(attractionId)

    const handleSave = async (e: React.FormEvent) => {
        e.preventDefault()

        if (
            formData.dateFrom === null ||
            formData.dateTo === null ||
            formData.hourFrom === null ||
            formData.hourTo === null
        )
            return
        if (attraction.priceType === PriceType.PERSON && formData.people === null) return

        if (attraction.priceType === PriceType.ITEM && formData.items === null) return

        const reservationAttractionResponse = await reservationService.createAttraction(
            reservationId,
            attractionId,
            formData.dateFrom,
            formData.dateTo,
            formData.hourFrom,
            formData.hourTo,
            formData.people,
            formData.items,
            formData.message
        )
        const reservationAttractionData: ReservationAttraction = await reservationAttractionResponse.json()
        setReservationAttractions((prevState: ReservationAttraction[]) => [...prevState, reservationAttractionData])
        setReservationMode(false)
    }

    return (
        <form
            className={styles.attraction}
            onSubmit={handleSave}
        >
            {!reservationMode ? (
                <>
                    <ImagePanel
                        path={image?.path}
                        onlyDisplay
                    />
                    <ReservationPlanAttractionInformation attraction={attraction} />
                    <ReservationPlanButtons
                        createText={t('reservation.reservation-plan-add')}
                        createOnClick={() => setReservationMode(true)}
                    />
                </>
            ) : (
                <>
                    <ReservationPlanAttractionForm
                        attraction={attraction}
                        dateFrom={dateFrom}
                        dateTo={dateTo}
                        formData={formData}
                        handleChange={handleChange}
                    />
                    <ReservationPlanButtons
                        saveText={t('reservation.reservation-plan-save')}
                        cancelText={t('reservation.reservation-plan-cancel')}
                        cancelOnClick={() => setReservationMode(false)}
                    />
                </>
            )}
        </form>
    )
}

export default ReservationPlanAttraction
