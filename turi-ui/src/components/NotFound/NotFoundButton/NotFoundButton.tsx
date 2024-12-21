import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { GreenButton } from '../../Controls/Button'
import styles from './NotFoundButton.module.css'

const NotFoundButton = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()

    return (
        <div className={styles.button}>
            <GreenButton
                text={t('not-found.button')}
                onClick={() => navigate('/')}
            />
        </div>
    )
}

export default NotFoundButton
