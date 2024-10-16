import { useTranslation } from 'react-i18next'
import TextRegular from '../../../Controls/Text/TextRegular'
import styles from './SignUpPersonalDescription.module.css'

const SignUpPersonalDescription = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.description}>
            <TextRegular
                text={t('signup-personal.description')}
            />
        </div>
    )
}

export default SignUpPersonalDescription;