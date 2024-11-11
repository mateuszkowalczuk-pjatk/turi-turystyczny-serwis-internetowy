import { useTranslation } from 'react-i18next'
import TextMediumExtra from '../../../Controls/Text/TextMediumExtra'
import styles from './SignUpPersonalTitle.module.css'

const SignUpPersonalTitle = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.title}>
            <TextMediumExtra text={t('signup-personal.title')} />
        </div>
    )
}

export default SignUpPersonalTitle
