import { GreenButton, GreyButton } from '../../Shared/Controls/Button'
import styles from './ReservationPlanButtons.module.css'

interface Props {
    firstText: string
    firstOnClick: () => void
    secondText?: string
    secondOnClick?: () => void
}

const ReservationPlanButtons = ({ firstText, firstOnClick, secondText, secondOnClick }: Props) => {
    return (
        <div className={styles.buttons}>
            <GreenButton
                text={firstText}
                onClick={firstOnClick}
            />
            {secondText && (
                <GreyButton
                    text={secondText}
                    onClick={secondOnClick}
                />
            )}
        </div>
    )
}

export default ReservationPlanButtons
