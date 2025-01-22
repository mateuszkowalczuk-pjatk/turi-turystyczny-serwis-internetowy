import { useTranslation } from 'react-i18next'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import styles from './OfferInformation.module.css'
import { handleTimeDisplay } from '../../../utils/handleDateTimeDisplay.ts'

const OfferInformation = ({ touristicPlace }: { touristicPlace: TouristicPlace }) => {
    const { t } = useTranslation()

    return (
        <div className={styles.information}>
            <div className={styles.title}>
                <TextRegular text={t('offer.information')} />
            </div>
            <div className={styles.content}>
                <div className={styles.text}>
                    <TextRegular text={touristicPlace.information || ''} />
                </div>
                <div className={styles.services}>
                    <ul>
                        <li className={styles.customListItem}>
                            <span className={styles.customStep}></span>
                            <TextRegular
                                text={
                                    t('offer.check-in-hours') +
                                        handleTimeDisplay(touristicPlace.checkInTimeFrom || '') +
                                        ' - ' +
                                        handleTimeDisplay(touristicPlace.checkInTimeTo || '') || ''
                                }
                            />
                        </li>
                        <li className={styles.customListItem}>
                            <span className={styles.customStep}></span>
                            <TextRegular
                                text={
                                    t('offer.check-out-hours') +
                                        handleTimeDisplay(touristicPlace.checkOutTimeFrom || '') +
                                        ' - ' +
                                        handleTimeDisplay(touristicPlace.checkOutTimeTo || '') || ''
                                }
                            />{' '}
                        </li>
                        <li className={styles.customListItem}>
                            <span className={styles.customStep}></span>
                            <TextRegular
                                text={
                                    touristicPlace.prepayment
                                        ? t('offer.prepayment-required')
                                        : t('offer.prepayment-not-required') || ''
                                }
                            />
                        </li>
                        <li className={styles.customListItem}>
                            <span className={styles.customStep}></span>
                            <TextRegular
                                text={t('offer.cancel-reservation-day-before') + touristicPlace.cancelReservationDays}
                            />
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default OfferInformation
