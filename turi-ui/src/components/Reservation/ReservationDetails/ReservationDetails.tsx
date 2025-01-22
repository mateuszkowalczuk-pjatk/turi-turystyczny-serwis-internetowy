import { useHooks } from '../../../hooks/useHooks.ts'
import Input from '../../Shared/Controls/Input'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import ReservationTime from '../ReservationTime'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import styles from './ReservationDetails.module.css'
import Label from '../../Shared/Controls/Label'

interface Props {
    touristicPlace: TouristicPlace
    dateFrom: string
    dateTo: string
    request: string | null
    setRequest: (value: ((prevState: string | null) => string | null) | string | null) => void
}

const ReservationDetails = ({ touristicPlace, dateFrom, dateTo, request, setRequest }: Props) => {
    const { t } = useHooks()

    return (
        <div className={styles.details}>
            <TextMedium text={t('reservation.reservation-details')} />
            <div className={styles.time}>
                {touristicPlace.checkInTimeFrom && touristicPlace.checkInTimeTo && (
                    <ReservationTime
                        title={t('reservation.reservation-check-in')}
                        date={dateFrom}
                        hourFrom={touristicPlace.checkInTimeFrom}
                        hourTo={touristicPlace.checkInTimeTo}
                    />
                )}
                {dateTo && touristicPlace.checkOutTimeFrom && touristicPlace.checkOutTimeTo && (
                    <ReservationTime
                        title={t('reservation.reservation-check-out')}
                        date={dateTo}
                        hourFrom={touristicPlace.checkOutTimeFrom}
                        hourTo={touristicPlace.checkOutTimeTo}
                    />
                )}
            </div>
            <Label text={t('reservation.reservation-request')} />
            <Input
                type={'text'}
                name={'request'}
                placeholder={t('reservation.reservation-request')}
                value={request}
                onChange={(e) => setRequest && setRequest(e.target.value)}
                required={false}
            />
        </div>
    )
}

export default ReservationDetails
