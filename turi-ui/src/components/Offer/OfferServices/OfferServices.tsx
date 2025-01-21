import { useTranslation } from 'react-i18next'
import { useState } from 'react'
import TourismAttractionOfferItem from '../../Tourism/TourismAttractionOfferItem'
import TourismStayOfferItem from '../../Tourism/TourismStayOfferItem'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import Input from '../../Shared/Controls/Input'
import { Attraction } from '../../../types/attraction.ts'
import { Stay } from '../../../types/stay.ts'
import { reservationService } from '../../../services/reservationService.ts'
import styles from './OfferServices.module.css'

interface Props {
    initDateFrom: string | null
    initDateTo: string | null
    initStays: Stay[]
    initAttractions: Attraction[]
    touristicPlaceId: number
}

const OfferServices = ({ initDateFrom, initDateTo, initStays, initAttractions, touristicPlaceId }: Props) => {
    const { t } = useTranslation()
    const [dateFrom, setDateFrom] = useState<string | null>(initDateFrom)
    const [dateTo, setDateTo] = useState<string | null>(initDateTo)
    const [stays, setStays] = useState<Stay[]>(initStays)
    const [attractions, setAttractions] = useState<Attraction[]>(initAttractions)

    const handleDateFromChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setDateFrom(event.target.value)
        handleFetch().catch((error) => error)
    }

    const handleDateToChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        handleFetch().catch((error) => error)
        setDateTo(event.target.value)
    }

    const handleFetch = async () => {
        if (dateFrom && dateTo) {
            const staysResponse = await reservationService.getAllTouristicPlaceStaysAvailableInDate(
                touristicPlaceId,
                new Date(dateFrom),
                new Date(dateTo)
            )
            const staysData: Stay[] = await staysResponse.json()
            setStays(staysData)
            const attractionsResponse = await reservationService.getAllTouristicPlaceAttractionsAvailableInDate(
                touristicPlaceId,
                new Date(dateFrom),
                new Date(dateTo)
            )
            const attractionsData: Attraction[] = await attractionsResponse.json()
            setAttractions(attractionsData)
        }
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
