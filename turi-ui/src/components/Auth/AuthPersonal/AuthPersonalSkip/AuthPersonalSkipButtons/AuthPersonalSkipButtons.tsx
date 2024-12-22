import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux'
import { GreenButton } from '../../../../Shared/Controls/Button'
import { notPersonalization } from '../../../../../store/slices/personal.ts'
import styles from './AuthPersonalSkipButtons.module.css'

const AuthPersonalSkipButtons = ({ onClick }: { onClick: () => void }) => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const dispatch = useDispatch()

    const navigateToHome = () => {
        dispatch(notPersonalization())
        navigate('/')
    }

    return (
        <div className={styles.buttons}>
            <GreenButton
                text={t('signup-personal.back')}
                onClick={onClick}
            />
            <GreenButton
                text={t('signup-personal.continue')}
                onClick={navigateToHome}
            />
        </div>
    )
}

export default AuthPersonalSkipButtons
