import React, { useEffect, useState } from 'react'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import Input from '../../Shared/Controls/Input'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import TourismStayOfferItem from '../../Tourism/TourismStayOfferItem'
import TourismAttractionOfferItem from '../../Tourism/TourismAttractionOfferItem'
import { Stay } from '../../../types/stay.ts'
import { Attraction } from '../../../types/attraction.ts'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import { reservationService } from '../../../services/reservationService.ts'
import styles from './OfferServices.module.css'

interface Props {
    initDateFrom: string | null
    initDateTo: string | null
    initStays: Stay[]
    initAttractions: Attraction[]
    touristicPlace: TouristicPlace
}

const OfferServices = ({ initDateFrom, initDateTo, initStays, initAttractions, touristicPlace }: Props) => {
    const { t } = useHooks()
    const [dateFrom, setDateFrom] = useState<string | null>(initDateFrom)
    const [dateTo, setDateTo] = useState<string | null>(initDateTo)
    const [stays, setStays] = useState<Stay[]>(initStays)
    const [attractions, setAttractions] = useState<Attraction[]>(initAttractions)

    useEffect(() => {
        const handleFetch = async () => {
            if (dateFrom && dateTo && touristicPlace.touristicPlaceId) {
                const staysResponse = await reservationService.getAllTouristicPlaceStaysAvailableInDate(
                    touristicPlace.touristicPlaceId,
                    dateFrom,
                    dateTo
                )
                if (staysResponse.status === 200) {
                    const staysData: Stay[] = await staysResponse.json()
                    setStays(staysData)
                }
                const attractionsResponse = await reservationService.getAllTouristicPlaceAttractionsAvailableInDate(
                    touristicPlace.touristicPlaceId,
                    dateFrom,
                    dateTo
                )
                if (attractionsResponse.status === 200) {
                    const attractionsData: Attraction[] = await attractionsResponse.json()
                    setAttractions(attractionsData)
                }
            }
        }
        if (dateFrom && dateTo) handleFetch().catch((error) => error)
    }, [dateFrom, dateTo, touristicPlace.touristicPlaceId])

    const handleDateFromChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setDateFrom(event.target.value)
    }

    const handleDateToChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setDateTo(event.target.value)
    }

    return (
        <div className={styles.services}>
            <div className={styles.title}>
                <TextRegular text={t('offer.available-offers')} />
            </div>
            <div className={styles.date}>
                <Input
                    type={'date'}
                    name={'dateFrom'}
                    placeholder={t('offer.date-from')}
                    value={dateFrom || ''}
                    onChange={handleDateFromChange}
                    required={true}
                />
                <Input
                    type={'date'}
                    name={'dateTo'}
                    placeholder={t('offer.date-to')}
                    value={dateTo || ''}
                    onChange={handleDateToChange}
                    required={true}
                />
            </div>
            <div className={styles.stays}>
                {stays &&
                    stays.map(
                        (stay, index) =>
                            dateFrom &&
                            dateTo && (
                                <TourismStayOfferItem
                                    stay={stay}
                                    index={index}
                                    key={index}
                                    reservation
                                    dateFrom={dateFrom}
                                    dateTo={dateTo}
                                />
                            )
                    )}
            </div>
            <div className={styles.title}>
                <TextRegular text={t('offer.available-attractions')} />
            </div>
            <div className={styles.attractions}>
                {attractions &&
                    attractions.map(
                        (attraction, index) =>
                            dateFrom &&
                            dateTo && (
                                <TourismAttractionOfferItem
                                    attraction={attraction}
                                    index={index}
                                    key={index}
                                    offer
                                />
                            )
                    )}
            </div>
        </div>
    )
}

export default OfferServices
