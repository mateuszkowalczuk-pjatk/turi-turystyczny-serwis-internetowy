import { useTranslation } from 'react-i18next'
import TextMediumExtra from '../../../Shared/Controls/Text/TextMediumExtra'
import styles from './AuthPersonalTitle.module.css'

const AuthPersonalTitle = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.title}>
            <TextMediumExtra text={t('signup-personal.title')} />
        </div>
    )
}

export default AuthPersonalTitle
