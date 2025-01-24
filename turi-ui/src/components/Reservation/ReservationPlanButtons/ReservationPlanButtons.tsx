import { GreenButton, GreyButton } from '../../Shared/Controls/Button'
import styles from './ReservationPlanButtons.module.css'

interface Props {
    createText?: string
    createOnClick?: () => void
    saveText?: string
    cancelText?: string
    cancelOnClick?: () => void
}

const ReservationPlanButtons = ({ createText, createOnClick, saveText, cancelText, cancelOnClick }: Props) => {
    return (
        <div className={styles.buttons}>
            {createText && (
                <GreenButton
                    text={createText}
                    onClick={createOnClick}
                />
            )}
            {saveText && (
                <GreenButton
                    text={saveText}
                    type={'submit'}
                />
            )}
            {cancelText && (
                <GreyButton
                    text={cancelText}
                    onClick={cancelOnClick}
                />
            )}
        </div>
    )
}

export default ReservationPlanButtons
