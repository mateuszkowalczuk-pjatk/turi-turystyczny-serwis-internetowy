import TextMedium from '../../Shared/Controls/Text/TextMedium'
import ReservationPlanSelectItems from '../ReservationPlanSelectItems'
import { Attraction } from '../../../types/attraction.ts'
import { TouristicPlace } from '../../../types/touristicPlace.ts'
import { ReservationAttraction } from '../../../types/reservation.ts'
import styles from './ReservationPlanSelectPart.module.css'

interface Props {
    text: string
    reservationAttractions?: ReservationAttraction[]
    attractions?: Attraction[]
    reservationId?: number
    setReservationAttractions?: (
        value: ((prevState: ReservationAttraction[]) => ReservationAttraction[]) | ReservationAttraction[]
    ) => void
    touristicPlace?: TouristicPlace
    dateFrom: Date
    dateTo: Date
}

const ReservationPlanSelectPart = ({
    text,
    reservationAttractions,
    attractions,
    reservationId,
    setReservationAttractions,
    touristicPlace,
    dateFrom,
    dateTo
}: Props) => {
    return (
        <div className={styles.part}>
            <TextMedium text={text} />
            <ReservationPlanSelectItems
                reservationAttractions={reservationAttractions}
                attractions={attractions}
                reservationId={reservationId}
                setReservationAttractions={setReservationAttractions}
                touristicPlace={touristicPlace}
                dateFrom={dateFrom}
                dateTo={dateTo}
            />
        </div>
    )
}

export default ReservationPlanSelectPart
