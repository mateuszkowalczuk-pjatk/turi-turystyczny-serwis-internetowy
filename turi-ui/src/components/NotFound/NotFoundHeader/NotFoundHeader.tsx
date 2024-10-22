import { useTranslation } from 'react-i18next'
import TextMediumExtra from '../../Controls/Text/TextMediumExtra'
import styles from './NotFoundHeader.module.css'

const NotFoundHeader = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.header}>
            <TextMediumExtra
                text={t('not-found.404')}
            />
        </div>
    )
}

export default NotFoundHeader