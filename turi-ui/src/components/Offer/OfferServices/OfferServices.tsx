import { useTranslation } from 'react-i18next'
import TourismAttractionOfferItem from '../../Tourism/TourismAttractionOfferItem'
import TourismStayOfferItem from '../../Tourism/TourismStayOfferItem'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import Input from '../../Shared/Controls/Input'
import { Attraction } from '../../../types/attraction.ts'
import { Stay } from '../../../types/stay.ts'
import styles from './OfferServices.module.css'

interface Props {
    dateFrom: string
    dateTo: string
    stays: Stay[]
    attractions: Attraction[]
}

const OfferServices = ({ dateFrom, dateTo, stays, attractions }: Props) => {
    const { t } = useTranslation()

    return (
        <div className={styles.services}>
            <div className={styles.title}>
                <TextRegular text={t('offer.available-offers')} />
            </div>
            <div className={styles.date}>
                <Input
                    type={'date'}
                    name={'dateFrom'}
                    placeholder={'Data od'}
                    value={dateFrom}
                    onChange={() => null}
                    required={true}
                />
                <Input
                    type={'date'}
                    name={'dateTo'}
                    placeholder={'Data od'}
                    value={dateTo}
                    onChange={() => null}
                    required={true}
                />
            </div>
            <div className={styles.stays}>
                {stays &&
                    stays.map((stay, index) => (
                        <TourismStayOfferItem
                            stay={stay}
                            index={index}
                            key={index}
                            reservation
                        />
                    ))}
            </div>
            <div className={styles.title}>
                <TextRegular text={t('offer.available-attractions')} />
            </div>
            <div className={styles.attractions}>
                {attractions &&
                    attractions.map((attraction, index) => (
                        <TourismAttractionOfferItem
                            attraction={attraction}
                            index={index}
                            key={index}
                            offer
                        />
                    ))}
            </div>
        </div>
    )
}

export default OfferServices
