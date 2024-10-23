import { useTranslation } from 'react-i18next'
import { GreenButton } from '../../Controls/Button'
import styles from './NotFoundButton.module.css'
import { useNavigate } from 'react-router-dom'

const NotFoundButton = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()

    const navigateToHome = () => {
        navigate('/')
    }

    return (
        <div className={styles.button}>
            <GreenButton
                text={t('not-found.button')}
                onClick={navigateToHome}
            />
        </div>
    )
}

export default NotFoundButton
