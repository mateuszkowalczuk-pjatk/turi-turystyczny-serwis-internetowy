import { useTranslation } from 'react-i18next'
import { GreenButton } from '../../../Controls/Button'
import styles from './SignUpPersonalButtons.module.css'


const SignUpPersonalButtons = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.buttons}>
            <GreenButton
                text={t('')}
                // onClick={}
            />
            <GreenButton
              text={t('')}
              // onClick={}
            />
        </div>
    )
}

export default SignUpPersonalButtons;