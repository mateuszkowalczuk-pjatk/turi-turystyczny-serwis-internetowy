import { useTranslation } from 'react-i18next'
import TextMediumExtra from '../../../../Shared/Controls/Text/TextMediumExtra'
import styles from './AuthPersonalSkipTitle.module.css'

const AuthPersonalSkipTitle = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.title}>
            <TextMediumExtra text={t('signup-personal.skip-title')} />
        </div>
    )
}

export default AuthPersonalSkipTitle
