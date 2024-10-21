import { useTranslation } from 'react-i18next'
import TextExtraLight from '../../../../Controls/Text/TextExtraLight'
import TextRegular from '../../../../Controls/Text/TextRegular'
import styles from './SignUpPersonalSkipDescription.module.css'

const SignUpPersonalSkipDescription = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.description}>
            <TextExtraLight
                text={t('signup-personal.skip-description')}
            />
            <TextRegular
                text={t('signup-personal.skip-decision')}
            />
        </div>
    )
}

export default SignUpPersonalSkipDescription