import TextRegular from '../../../Shared/Controls/Text/TextRegular'
import styles from './MainInformationText.module.css'

const MainInformationText = ({ text }: { text: string }) => {
    return (
        <div className={styles.text}>
            <TextRegular text={text} />
        </div>
    )
}

export default MainInformationText
