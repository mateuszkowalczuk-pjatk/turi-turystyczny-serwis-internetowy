import TextMedium from '../../Controls/Text/TextMedium'
import styles from './HeaderInformation.module.css'

const HeaderInformation = ({ text }: { text: string }) => {
    return (
        <div className={styles.header}>
            <TextMedium
                text={ text }
            />
        </div>
    )
}

export default HeaderInformation;