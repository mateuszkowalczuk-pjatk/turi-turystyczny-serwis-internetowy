import { useTranslation } from 'react-i18next'
import TextExtraLight from '../../../../Shared/Controls/Text/TextExtraLight'
import TextRegular from '../../../../Shared/Controls/Text/TextRegular'
import styles from './AuthPersonalSkipDescription.module.css'

const AuthPersonalSkipDescription = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.description}>
            <TextExtraLight text={t('signup-personal.skip-description')} />
            <TextRegular text={t('signup-personal.skip-decision')} />
        </div>
    )
}

export default AuthPersonalSkipDescription
