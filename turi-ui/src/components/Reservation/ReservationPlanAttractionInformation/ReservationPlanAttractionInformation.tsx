import TextMedium from '../../Shared/Controls/Text/TextMedium'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import { Attraction } from '../../../types/attraction.ts'
import styles from './ReservationPlanAttractionInformation.module.css'

const ReservationPlanAttractionInformation = ({ attraction }: { attraction: Attraction }) => {
    return (
        <div className={styles.information}>
            <TextMedium text={attraction.name} />
            <TextRegular text={attraction.description} />
        </div>
    )
}

export default ReservationPlanAttractionInformation
