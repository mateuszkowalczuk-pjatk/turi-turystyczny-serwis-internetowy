import { ChangeEvent } from 'react'
import { Attraction } from '../../../types/attraction.ts'
import { ReservationPlanAttractionFormData } from '../ReservationPlanAttraction/reservationPlanAttractionFormData.ts'

export interface Props {
    attraction: Attraction
    dateFrom: Date
    dateTo: Date
    formData: ReservationPlanAttractionFormData
    handleChange: (e: ChangeEvent<HTMLInputElement>) => void
}
