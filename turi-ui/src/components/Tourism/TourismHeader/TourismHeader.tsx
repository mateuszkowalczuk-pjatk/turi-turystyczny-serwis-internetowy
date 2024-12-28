import { GreenButton } from '../../Shared/Controls/Button'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import styles from './TourismHeader.module.css'

interface Props {
    text: string
    firstButtonText?: string
    firstButtonOnClick?: () => void
    secondButtonText?: string
    secondButtonOnClick?: () => void
}

const TourismHeader = ({ text, firstButtonText, firstButtonOnClick, secondButtonText, secondButtonOnClick }: Props) => {
    return (
        <div className={styles.header}>
            <TextMedium text={text} />
            <div className={styles.buttons}>
                {firstButtonText && (
                    <GreenButton
                        text={firstButtonText}
                        onClick={firstButtonOnClick}
                    />
                )}
                {secondButtonText && (
                    <GreenButton
                        text={secondButtonText}
                        onClick={secondButtonOnClick}
                    />
                )}
            </div>
        </div>
    )
}

export default TourismHeader
