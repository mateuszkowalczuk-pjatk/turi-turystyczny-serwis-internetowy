import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { GreenButton } from '../../../Controls/Button'
import styles from './SignUpPersonalButtons.module.css'

const SignUpPersonalButtons = ({ skipOnClick }: { skipOnClick: () => void }) => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const navigateToHome = () => {
        navigate('/')
    }

    return (
        <div className={styles.buttons}>
            <GreenButton
                text={t('signup-personal.skip')}
                onClick={skipOnClick}
            />
            <GreenButton
                text={t('signup-personal.next')}
                onClick={navigateToHome}
            />
        </div>
    )
}

export default SignUpPersonalButtons