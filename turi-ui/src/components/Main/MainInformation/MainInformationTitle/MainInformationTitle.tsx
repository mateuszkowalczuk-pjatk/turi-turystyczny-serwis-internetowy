import TextMedium from '../../../Shared/Controls/Text/TextMedium'
import styles from './MainInformationTitle.module.css'

const MainInformationTitle = ({ text }: { text: string }) => {
    return (
        <div className={styles.title}>
            <TextMedium text={text} />
        </div>
    )
}

export default MainInformationTitle
