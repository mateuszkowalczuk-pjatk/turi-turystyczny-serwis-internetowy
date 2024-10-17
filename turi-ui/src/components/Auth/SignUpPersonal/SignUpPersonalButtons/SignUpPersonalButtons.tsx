import { useTranslation } from 'react-i18next'
import { GreenButton } from '../../../Controls/Button'
import styles from './SignUpPersonalButtons.module.css'


const SignUpPersonalButtons = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.buttons}>
            <GreenButton
                text={t('signup-personal.skip')}
                // onClick={}
            />
            <GreenButton
              text={t('signup-personal.next')}
              // onClick={}
            />
        </div>
    )
}

export default SignUpPersonalButtons;