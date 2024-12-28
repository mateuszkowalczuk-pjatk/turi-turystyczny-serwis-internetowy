import { useTranslation } from 'react-i18next'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import styles from './NotFoundTitle.module.css'

const NotFoundTitle = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.title}>
            <TextMedium text={t('not-found.title')} />
        </div>
    )
}

export default NotFoundTitle
