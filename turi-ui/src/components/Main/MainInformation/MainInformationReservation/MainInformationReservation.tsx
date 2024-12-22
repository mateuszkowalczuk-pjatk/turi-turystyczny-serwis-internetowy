import TextRegular from '../../../Shared/Controls/Text/TextRegular'
import styles from './MainInformationReservation.module.css'

const MainInformationReservation = () => {
    return (
        <div className={styles.reservation}>
            <TextRegular text={'Nie posiadasz aktualnie rezerwacji'} />
        </div>
    )
}

export default MainInformationReservation
