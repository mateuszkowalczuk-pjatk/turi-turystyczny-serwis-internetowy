import TextMediumExtra from '../../Controls/Text/TextMediumExtra'
import styles from './PageTitle.module.css'

const PageTitle = ({ text }: { text: string }) => {
    return (
        <div className={styles.title}>
            <TextMediumExtra text={text} />
        </div>
    )
}

export default PageTitle
