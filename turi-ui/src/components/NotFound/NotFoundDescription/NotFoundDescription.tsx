import { useTranslation } from 'react-i18next'
import TextRegular from '../../Controls/Text/TextRegular'
import styles from './NotFoundDescription.module.css'

const NotFoundDescription = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.description}>
            <TextRegular text={t('not-found.description')} />
        </div>
    )
}

export default NotFoundDescription
