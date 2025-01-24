import React, { useState } from 'react'
import { useForm } from '../shared/useForm.ts'
import { useImage } from '../shared/useImage.ts'
import { Image, ImageMode } from '../../types/image.ts'
import { ReservationPlanAttractionFormData } from '../../components/Reservation/ReservationPlanAttraction/reservationPlanAttractionFormData.ts'

export const useReservationPlanAttraction = (
    attractionId: number
): {
    reservationMode: boolean
    setReservationMode: (value: ((prevState: boolean) => boolean) | boolean) => void
    image: Image | null
    formData: ReservationPlanAttractionFormData
    handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void
} => {
    const [reservationMode, setReservationMode] = useState<boolean>(false)
    const { images } = useImage(attractionId, ImageMode.ATTRACTION)
    const image = images.length > 0 ? images[0] : null
    const { formData, handleChange } = useForm<ReservationPlanAttractionFormData>({
        initialValues: {
            dateFrom: null,
            dateTo: null,
            hourFrom: null,
            hourTo: null,
            people: null,
            items: null,
            message: null
        }
    })

    return { reservationMode, setReservationMode, image, formData, handleChange }
}
