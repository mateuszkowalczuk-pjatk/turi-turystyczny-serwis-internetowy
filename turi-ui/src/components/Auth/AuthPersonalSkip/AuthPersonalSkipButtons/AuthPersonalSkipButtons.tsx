import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import { GreenButton, GreyButton } from '../../../Shared/Controls/Button'
import { notPersonalization } from '../../../../store/slices/personal.ts'
import styles from './AuthPersonalSkipButtons.module.css'

const AuthPersonalSkipButtons = ({ onClick }: { onClick: () => void }) => {
    const { t, navigate, dispatch } = useHooks()

    const navigateToHome = () => {
        dispatch(notPersonalization())
        navigate('/')
    }

    return (
        <div className={styles.buttons}>
            <GreyButton
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
