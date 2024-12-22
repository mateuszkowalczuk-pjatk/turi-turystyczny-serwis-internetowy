import { useTranslation } from 'react-i18next'
import TextRegular from '../../../Shared/Controls/Text/TextRegular'
import styles from './AuthPersonalDescription.module.css'

const AuthPersonalDescription = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.description}>
            <TextRegular text={t('signup-personal.description')} />
        </div>
    )
}

export default AuthPersonalDescription
