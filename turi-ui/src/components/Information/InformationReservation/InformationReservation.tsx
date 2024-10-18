import TextRegular from "../../Controls/Text/TextRegular";
import styles from './InformationReservation.module.css'

const InformationReservation = () => {
    return (
        <div className={styles.reservation}>
            <TextRegular
                text={'Nie posiadasz aktualnie rezerwacji'}
            />
        </div>
    )
}

export default InformationReservation;