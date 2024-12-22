import TextMedium from '../../../Shared/Controls/Text/TextMedium'
import styles from './MainPropositionTitle.module.css'

const MainPropositionTitle = ({ text }: { text: string }) => {
    return (
        <div className={styles.text}>
            <TextMedium text={text} />
        </div>
    )
}

export default MainPropositionTitle
