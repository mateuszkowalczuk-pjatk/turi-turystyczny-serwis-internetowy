import { useTranslation } from 'react-i18next'
import TextMediumExtra from '../../../../Controls/Text/TextMediumExtra'
import styles from './SignUpPersonalSkipTitle.module.css'

const SignUpPersonalSkipTitle = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.title}>
            <TextMediumExtra
                text={t('signup-personal.skip-title')}
            />
        </div>
    )
}

export default SignUpPersonalSkipTitle